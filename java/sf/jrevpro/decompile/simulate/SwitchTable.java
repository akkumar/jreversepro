/**
 * JReversePro - Java Decompiler / Disassembler.
 * Copyright (C) 2008 Karthik Kumar.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *  
 *  	http://www.apache.org/licenses/LICENSE-2.0 
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * 
 */
package net.sf.jrevpro.decompile.simulate;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import net.sf.jrevpro.CustomLoggerFactory;
import net.sf.jrevpro.ast.expression.Expression;
import net.sf.jrevpro.jls.JLSConstants;
import net.sf.jrevpro.jvm.Opcodes;
import net.sf.jrevpro.jvm.TypeInferrer;
import net.sf.jrevpro.reflect.Method;
import net.sf.jrevpro.reflect.instruction.Instruction;

public class SwitchTable {

	/**
	 * @param method
	 *            Reference to current method.
	 * @param ins
	 *            Instruction that corresponds to a tableswitch or a
	 *            lookupswitch instruction.
	 * @param gotos
	 *            Map of goto statements.
	 * 
	 * @throws IOException
	 *             thrown in case of any error.
	 * @throws RevEngineException
	 *             if the instruction passed is not a switch opcode.
	 */
	public SwitchTable(Method method, Instruction ins,
			Map<Integer, Integer> gotos) throws IOException {

		this.method = method;
		insIndex = ins.currentPc;
		cases = new ArrayList<CaseEntry>();

		if (ins.opcode == Opcodes.OPCODE_TABLESWITCH) {
			createTableSwitch(ins.args, ins.currentPc, gotos);
		} else if (ins.opcode == Opcodes.OPCODE_LOOKUPSWITCH) {
			createLookupSwitch(ins.args, ins.currentPc, gotos);
		} else {
			throw new IllegalArgumentException("Opcode " + ins.opcode
					+ " Not a switch statement");
		}
		datatype = null;
	}

	/**
	 * @param method
	 *            Reference to current method.
	 * @param ins
	 *            Instruction that corresponds to a tableswitch or a
	 *            lookupswitch instruction.
	 * @param op1
	 *            Operand that is to be used inside the switch statement.
	 * @param gotos
	 *            Map of goto statements.
	 * @throws IOException
	 *             thrown in case of any error.
	 * @throws RevEngineException
	 *             if the instruction passed is not a switch opcode.
	 */
	public SwitchTable(Method method, Instruction ins, Expression op1,
			Map<Integer, Integer> gotos) throws IOException {
		this.datatype = op1.getType();
		this.varName = op1.getJLSCode();

		// Copy - paste from prev. constructor.
		this.method = method;
		insIndex = ins.currentPc;
		cases = new ArrayList<CaseEntry>();

		if (ins.opcode == Opcodes.OPCODE_TABLESWITCH) {
			createTableSwitch(ins.args, ins.currentPc, gotos);
		} else if (ins.opcode == Opcodes.OPCODE_LOOKUPSWITCH) {
			createLookupSwitch(ins.args, ins.currentPc, gotos);
		} else {
			throw new IllegalArgumentException("Opcode " + ins.opcode
					+ " Not a switch statement");
		}
		logger.fine("switch datatype " + datatype);
	}

	/**
	 * @return Returns the default byte of this switch block.
	 */
	public int getDefaultByte() {
		return defaultByte;
	}

	/**
	 * For 'tableswitch' opcode this fills the data structure - JSwitchTable.
	 * 
	 * @param entries
	 *            Bytecode entries that contain the case values and their target
	 *            opcodes.
	 * @param offset
	 *            offset is the index of the current tableswitch instruction
	 *            into the method bytecode array.
	 * @param gotos
	 *            Map of goto statements.
	 * @throws IOException
	 *             Thrown in case of an i/o error when reading from the bytes.
	 */
	private void createTableSwitch(byte[] entries, int offset,
			Map<Integer, Integer> gotos) throws IOException {
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(
				entries));
		defaultByte = dis.readInt() + offset;
		int lowVal = dis.readInt();
		int highVal = dis.readInt();

		Map<Integer, CaseEntry> mapCases = new HashMap<Integer, CaseEntry>();
		for (int i = lowVal; i <= highVal; i++) {
			int curTarget = dis.readInt() + offset;
			String value = TypeInferrer.getValue(String.valueOf(i),
					this.datatype);
			CaseEntry ent = mapCases.get(new Integer(curTarget));
			if (ent == null) {
				mapCases.put(new Integer(curTarget), new CaseEntry(value,
						curTarget));
			} else {
				ent.addValue(value);
			}
		}
		cases = new ArrayList<CaseEntry>(mapCases.values());
		dis.close();
		processData(gotos);
	}

	/**
	 * For 'lookupswitch' opcode this fills the data structure - JSwitchTable.
	 * 
	 * @param entries
	 *            Bytecode entries that contain the case values and their target
	 *            opcodes.
	 * @param offset
	 *            offset is the index of the current lookupswitch instruction
	 *            into the method bytecode array.
	 * @param gotos
	 *            Map of goto statements.
	 * 
	 * @throws IOException
	 *             Thrown in case of an i/o error when reading from the bytes.
	 */
	private void createLookupSwitch(byte[] entries, int offset,
			Map<Integer, Integer> gotos) throws IOException {
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(
				entries));

		defaultByte = dis.readInt() + offset;
		int numVal = dis.readInt();

		Map<Integer, CaseEntry> mapCases = new HashMap<Integer, CaseEntry>();

		for (int i = 0; i < numVal; i++) {
			String value = TypeInferrer.getValue(String.valueOf(dis.readInt()),
					datatype);
			int curTarget = dis.readInt() + offset;

			CaseEntry ent = mapCases.get(new Integer(curTarget));
			if (ent == null) {
				mapCases.put(new Integer(curTarget), new CaseEntry(value,
						curTarget));
			} else {
				ent.addValue(value);
			}
		}
		cases = new ArrayList<CaseEntry>(mapCases.values());
		dis.close();
		processData(gotos);
	}

	/**
	 * @return Returns the list of cases. Individual elements are JCaseEntry.
	 */
	public List<CaseEntry> getCases() {
		return cases;
	}

	/**
	 * @return Returns the disassembled string for this switch statement block.
	 */
	public String disassemble() {
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < cases.size(); i++) {
			sb.append("\n\t\t\t" + cases.get(i));
		}
		sb.append("\n\t\t\tDefault Byte " + defaultByte);
		return sb.toString();
	}

	/**
	 * @param rhsType
	 *            Type of the switch variable of this block.
	 * @param rhsValue
	 *            Value ( name ) of the switch variable for this block.
	 */
	public void setTypeValue(String rhsType, String rhsValue) {
		varName = rhsValue;
		datatype = rhsType;
		// dataType could be either int or char.
	}

	/**
	 * Process the bytecode stream of case values and targets to individual case
	 * blocks.
	 * 
	 * @param gotos
	 *            Map of goto statements.
	 */
	public void processData(Map<Integer, Integer> gotos) {
		maxTarget = defaultByte;
		if (gotos != null) {
			for (CaseEntry ent : cases) {
				Integer obj = gotos.get(new Integer(ent.getTarget() - 3));
				if (obj != null) {
					int tempVal = obj.intValue();
					maxTarget = (maxTarget > tempVal) ? maxTarget : tempVal;
				}
			}
			if (maxTarget > defaultByte) {
				boolean targetPresent = false;
				for (int i = 0; i < cases.size() - 1; i++) {
					CaseEntry ent = cases.get(i);
					if (ent.getTarget() == defaultByte) {
						ent.addValue(JLSConstants.DEFAULT);
						// ent.setTarget(defa);
						targetPresent = true;
					}
				}
				if (!targetPresent) {
					cases.add(new CaseEntry(JLSConstants.DEFAULT, defaultByte));
				}
			}
		}

		// Sort the entries
		Collections.sort(cases, new CaseComparator());

		// Assign endTargets for all of them.
		int i = 0;
		for (; i < cases.size() - 1; i++) {
			CaseEntry ent = cases.get(i);
			CaseEntry entNext = cases.get(i + 1);
			ent.setEndTarget(entNext.getTarget());
		}
		CaseEntry entLast = cases.get(i);
		entLast.setEndTarget(maxTarget);
	}

	/**
	 * @return Returns a branch entry for this switch statement.
	 */
	public BranchEntry getBranchEntry() {
		return new BranchEntry(method, insIndex, maxTarget, maxTarget,
				BranchConstants.TYPE_SWITCH, varName, "", "");
	}

	/**
	 * Inserts a CaseEntry in the list.
	 * 
	 * @param caseEntry
	 *            Case Entry to be appended.
	 */
	public void addCaseEntry(CaseEntry caseEntry) {
		cases.add(caseEntry);
	}

	/**
	 * @return Returns the Stringified version of this class.
	 */
	public String toString() {
		return cases.toString();
	}

	/**
	 * Instruction index of the switch statement.
	 */
	int insIndex;

	/**
	 * List of cases that are available. Individual elements are JCaseEntry.
	 */
	List<CaseEntry> cases;

	/**
	 * DefaultByte for this switch statement.
	 */
	int defaultByte;

	/** Max Target from this switch statement group * */
	int maxTarget;

	/**
	 * Name of the variable that is put under switch statement.
	 */
	String varName;

	/**
	 * Datatype of the variable for which switch is used.
	 */
	String datatype;

	/**
	 * Reference to method to which this switch table belongs.
	 */
	Method method;

	private Logger logger = CustomLoggerFactory.createLogger();

}
