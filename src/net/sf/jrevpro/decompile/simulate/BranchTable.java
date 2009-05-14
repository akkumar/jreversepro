/**
 * @(#)BranchTable.java
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
 **/

package net.sf.jrevpro.decompile.simulate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Logger;

import net.sf.jrevpro.CustomLoggerFactory;
import net.sf.jrevpro.jvm.Opcodes;
import net.sf.jrevpro.reflect.Method;
import net.sf.jrevpro.reflect.MethodException;
import net.sf.jrevpro.reflect.instruction.Instruction;

/**
 * BranchTable manages the objects of GotoEntry and BranchEntry.
 * 
 * @author Karthik Kumar
 */
public class BranchTable implements BranchConstants, Opcodes {

	/**
	 * @param method
	 *            Method reference.
	 */
	public BranchTable(Method method) {
		mJSRTarget = new Vector<Integer>();
		mMonitor = new HashMap<Integer, String>();
		branches = new Vector<BranchEntry>();
		switches = new Vector<Instruction>();
		gotos = new HashMap<Integer, Integer>();
		this.method = method;
	}

	/**
	 * Setter method for the branch tables.
	 * 
	 * @param aBranches
	 *            Branches to be added.
	 */
	public void setTables(List<BranchEntry> aBranches) {
		branches.addAll(aBranches);
	}

	/**
	 * Getter method for goto tables.
	 * 
	 * @return Map of goto table entries. key - goto pc. value - target of that
	 *         goto table.
	 */
	public Map<Integer, Integer> getGotoTable() {
		return gotos;
	}

	/**
	 * Adds a Goto entry to the internal data structure.
	 * 
	 * @param startPc
	 *            StartPc of the goto statement.
	 * @param targetPc
	 *            TargetPc of the goto statement.
	 */
	public void addGotoEntry(int startPc, int targetPc) {
		gotos.put(new Integer(startPc), new Integer(targetPc));
	}

	/**
	 * Finalizer method.
	 */
	protected void finalize() {
		branches = null;
		gotos = null;
	}

	/**
	 * Adds a new branch entry to the list of branches.
	 * 
	 * @param ent
	 *            branch entry to be added.
	 */
	public void add(BranchEntry ent) {
		branches.add(ent);
	}

	/**
	 * Checks if the Pc passed as argument is the target for any JSR
	 * instructions.
	 * 
	 * @param currPc
	 *            Pc for which it is to be checked if it is the target for any
	 *            JSR instruction.
	 * @return true, if there exists a JSR instruction with its target currPc.
	 *         false, otherwise.
	 */
	public boolean isJSRTarget(int currPc) {
		return mJSRTarget.contains(new Integer(currPc));
	}

	/**
	 * This adds the pc given as input as a JSR target.
	 * 
	 * @param targetPc
	 *            TargetPc for a JSR instruction that is to be added to the
	 *            internal data structure ( list ).
	 */
	public void addJSRPc(int targetPc) {
		Integer intPc = new Integer(targetPc);
		if (!mJSRTarget.contains(intPc)) {
			mJSRTarget.add(intPc);
		}
	}

	/**
	 * When a RET instruction is encountered we add a branch with the last
	 * element of the JSR target lists. JSR instructions signify 'synchronized'
	 * and catch..all blocks.
	 * 
	 * @param retPc
	 *            PC of the instruction which is a RET.
	 */
	public void addRetPc(int retPc) {
		int startPc = mJSRTarget.lastElement().intValue();
		branches.add(new BranchEntry(method, startPc, startPc, retPc, TYPE_JSR,
				"", "", ""));
	}

	/**
	 * This sorts the list containing branches such that no branch overlaps with
	 * the one previously existing. See JBranchComparator for more details.
	 */
	public void sort() {
		Collections.sort(branches, new BranchComparator());
	}

	/**
	 * Adds a monitor Pc.
	 * 
	 * @param aMonitorPc
	 *            Pc that is monitorenter.
	 * @param aMonObject
	 *            Object that is 'monitored'. In the sense object for which lock
	 *            is obtained before entering a 'synchronized' object.
	 */
	public void addMonitorPc(int aMonitorPc, String aMonObject) {
		mMonitor.put(new Integer(aMonitorPc), aMonObject);
	}

	/**
	 * Returns the monitor type for the monitor that begins with Pc.
	 * 
	 * @param monitorBeginPc
	 *            Pc that begins with the monitor.
	 * @return monitor object associated with this branch.
	 */
	public String doesMonitorBegin(int monitorBeginPc) {
		return mMonitor.get(new Integer(monitorBeginPc));
	}

	/**
	 * Identifies the else..if and else branches. Identifies catch.. branches.
	 * 
	 * @throws RevEngineException
	 *             Thrown in case of any error.
	 */
	public void identifyMoreBranches() {

		for (BranchEntry jbe : branches) {
			int gotoStartPc = jbe.getEndBlockPc() - 3;
			int gotoNextPc = gotoStartPc + 3;
			Integer obj = gotos.get(new Integer(gotoStartPc));
			switch (jbe.getType()) {
			case TYPE_IF:
			case TYPE_ELSE_IF:
				if (obj != null) {
					// Before adding else, check for else if.
					int gotoTargetPc = obj.intValue();
					if (gotoTargetPc - gotoStartPc == 3) {
						break;
					}
					BranchEntry elsif = contains(startsWith(gotoNextPc),
							TYPE_IF);

					if (elsif == null) {
						BranchEntry caseEntry = contains(
								startsWith(gotoNextPc), TYPE_CASE);
						if (caseEntry == null) {
							BranchEntry elseEntry = new BranchEntry(method,
									gotoNextPc, gotoNextPc, gotoTargetPc,
									TYPE_ELSE, jbe.opr1, jbe.opr2, jbe.operator);
							branches.add(elseEntry);
						}
					} else {
						elsif.setType(TYPE_ELSE_IF);
					}
				}
				break;
			case TYPE_DO_WHILE:
				if (gotos.containsValue(new Integer(jbe.getStartPc()))) {
					jbe.setType(TYPE_WHILE);
				}
				break;
			}
		}
	}

	/**
	 * Adds the switch entries and the case entries under the same to the branch
	 * table.
	 * 
	 * @param switchEntry
	 *            switch table containing entries about switch statements.
	 */
	public void addSwitch(SwitchTable switchEntry) {
		int defaultByte = switchEntry.getDefaultByte();
		int maxTarget = defaultByte;
		List<CaseEntry> enumCases = switchEntry.getCases();

		logger.fine("No: Case Entries " + enumCases.size());
		for (CaseEntry singleCase : enumCases) {
			int caseTarget = singleCase.getTarget();
			int endCase = singleCase.getEndTarget();

			List<String> caseValues = singleCase.getValues();
			StringBuilder sb = new StringBuilder();
			for (String singleCaseValue : caseValues) {
				sb.append(singleCaseValue + ",");
			}
			BranchEntry ent = new BranchEntry(method, caseTarget, caseTarget,
					endCase, TYPE_CASE, sb.toString(), "", "");
			branches.add(ent);
		}
		branches.add(switchEntry.getBranchEntry());
	}

	/**
	 * List of JException entries.
	 * 
	 * @param excTryTable
	 *            Individual entries being JException.
	 */
	public void addTryBlocks(List<MethodException> excTryTable) {
		for (MethodException exc : excTryTable) {
			int insIndex = exc.getStartPc();
			if (insIndex == -1) {
				continue;
			}

			int endPc = exc.getEndPc();
			String syncLock = doesMonitorBegin(insIndex - 1);
			if (syncLock != null) {
				branches.add(new BranchEntry(method, insIndex, insIndex, endPc,
						TYPE_SYNC, syncLock, "", ""));
			} else {
				branches.add(new BranchEntry(method, insIndex, insIndex, endPc,
						(exc.isAny()) ? TYPE_TRY_ANY : TYPE_TRY, "", "", ""));
			}
		}
	}

	/**
	 * For the given pc return the target of the instruction. The instruction is
	 * a goto statement.
	 * 
	 * @param startPc
	 *            Start Pc.
	 * @return the TargetPc for the goto instruction at the startPc
	 */
	public int findGotoTarget(int startPc) {
		Integer gotoStartPc = new Integer(startPc);
		Integer obj = gotos.get(gotoStartPc);
		if (obj == null) {
			return -1;
		} else {
			return obj.intValue();
		}
	}

	/**
	 * Returns the list of branches that starts with the mentioned aInsIndex.
	 * 
	 * @param aInsIndex
	 *            Instruction index.
	 * @return List of JBranchEntry - list of branches that starts with the
	 *         mentioned instruction index.
	 * @throws RevEngineException
	 *             thrown in case of an error.
	 */
	public List<BranchEntry> startsWith(int aInsIndex) {

		List<BranchEntry> branchEntries = new ArrayList<BranchEntry>();
		for (BranchEntry jbe : branches) {
			if (jbe.doesStartWith(aInsIndex)) {
				branchEntries.add(jbe);
			}
		}
		return branchEntries;
	}

	/**
	 * Delete the branch that corresponds to a else .. branch starting with the
	 * given Pc
	 * 
	 * @param startElse
	 *            PC for which the else statement is to be deleted.
	 */
	public void deleteElse(int startElse) {
		for (int i = 0; i < branches.size(); i++) {
			BranchEntry jbe = branches.get(i);
			if (jbe.getType() == TYPE_ELSE && jbe.getStartPc() == startElse) {
				branches.remove(i);
			}
		}
	}

	/**
	 * Returns the first branch in the mentioned branchlist that matches the
	 * particular type.
	 * 
	 * @param listBranchEntries
	 *            list of branch entries.
	 * @param type
	 *            Type that is to be searched for.
	 * @return first branch entry that matches the type mentioned in the list
	 *         given.
	 */
	public static BranchEntry contains(List<BranchEntry> listBranchEntries,
			int type) {
		if (listBranchEntries.size() == 0) {
			return null;
		}
		for (BranchEntry ent : listBranchEntries) {
			if (ent.getType() == type) {
				return ent;
			}
		}
		return null;
	}

	/**
	 * @param byteIns
	 *            List of Instructions.
	 * @return null.
	 */
	private Instruction getNextGoto(List<Instruction> byteIns) {
		return null;
	}

	/**
	 * @param byteIns
	 *            BytecodeInstruction List.
	 * @param start
	 *            StartPc.
	 * @param end
	 *            EndPc.
	 * @return Returns a JInstruction reference.
	 */
	public Instruction findGotoIns(List<Instruction> byteIns, int start, int end) {
		int i;
		for (i = 0; i < byteIns.size(); i++) {
			if (byteIns.get(i).currentPc == start) {
				break;
			}
		}
		Instruction ins = byteIns.get(i);
		while (ins != null && ins.opcode != OPCODE_GOTO
				&& ins.opcode != OPCODE_GOTOW) {
			if (ins.currentPc == end) {
				ins = null;
				break;
			} else if (ins.opcode == OPCODE_RETURN) {
				// TODO: Revisit this code
				// ins = byteIns.get(ins.position + 1);
				break;
			}
			// TODO: Revisit this piece of code.
			// ins = byteIns.get(ins.position + 1);
		}
		return ins;
	}

	/**
	 * Stringifies the braches alone.
	 * 
	 * @return Returns a Stringifed version of the branches alone.
	 */
	public String branchesToString() {
		StringBuilder sb = new StringBuilder("");
		int size = branches.size();
		if (size > 0) {
			sb.append("Branches:\n");
			for (int i = 0; i < size; i++) {
				sb.append(branches.get(i) + "\n");
			}
		}
		return sb.toString();
	}

	/**
	 * @return Stringified form of the class
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		sb.append(branchesToString());
		int size = gotos.size();
		if (size > 0) {
			sb.append("Gotos:\n");
			for (Object obj : gotos.entrySet()) {
				sb.append(obj.toString() + "\n");
			}
		}
		size = mJSRTarget.size();
		if (size > 0) {
			sb.append("JSRTargets:\n");
			for (int i = 0; i < size; i++) {
				sb.append(mJSRTarget.get(i) + "\n");
			}
		}
		return sb.toString();
	}

	/**
	 * List of branch representations.
	 */
	List<BranchEntry> branches;

	/**
	 * gotos Key - StartPc ( java.lang.Integer ) Value - TargetPc ( Absolute
	 * target -java.lang.Integer).
	 */
	Map<Integer, Integer> gotos;

	/**
	 * List of switch instructions. Individual members are JInstruction.
	 */
	List<Instruction> switches;

	/**
	 * It is a Vector of 'Integer's. The integers are the target of the jump sub
	 * routine instruction.
	 */
	Vector<Integer> mJSRTarget;

	/**
	 * Map of monitor instructions.
	 */
	Map<Integer, String> mMonitor;

	/** Method reference * */
	Method method;

	private Logger logger = CustomLoggerFactory.createLogger();
}
