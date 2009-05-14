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
package net.sf.jrevpro.ast.evaluator;

import java.util.List;

import net.sf.jrevpro.ast.expression.Assignment;
import net.sf.jrevpro.ast.expression.Expression;
import net.sf.jrevpro.ast.expression.Variable;
import net.sf.jrevpro.ast.intermediate.CompleteLine;
import net.sf.jrevpro.reflect.instruction.Instruction;

public class LongFloatDoubleStoreEvaluator extends AbstractInstructionEvaluator {

	public LongFloatDoubleStoreEvaluator(EvaluatorContext context) {
		super(context);
	}

	@Override
	void evaluate(Instruction ins) {
		int indexToSymbolTable = getIndexToSymbolTable(ins);
		Expression rhs = evalStack.pop();
		Variable lhs = new Variable(varTable, rhs.getType(),
				indexToSymbolTable, ins.currentPc);

		statements.append(new CompleteLine(ins, new Assignment(lhs, rhs)));

		// Hint to the symbol table about the type.
		varTable.recordLocalDatatypeReference(indexToSymbolTable,
				rhs.getType(), ins.currentPc);

	}

	private int getIndexToSymbolTable(Instruction ins) {
		int opcode = ins.opcode;

		switch (opcode) {
		case OPCODE_LSTORE:
		case OPCODE_FSTORE:
		case OPCODE_DSTORE:
			return ins.getArgUnsignedWide();

		case OPCODE_LSTORE_0:
		case OPCODE_LSTORE_1:
		case OPCODE_LSTORE_2:
		case OPCODE_LSTORE_3:
			return opcode - OPCODE_LSTORE_0;

		case OPCODE_FSTORE_0:
		case OPCODE_FSTORE_1:
		case OPCODE_FSTORE_2:
		case OPCODE_FSTORE_3:
			return opcode - OPCODE_FSTORE_0;

		case OPCODE_DSTORE_0:
		case OPCODE_DSTORE_1:
		case OPCODE_DSTORE_2:
		case OPCODE_DSTORE_3:
			return opcode - OPCODE_DSTORE_0;

		default:
			throw new RuntimeException("Invalid Opcode specified. " + opcode);
		}
	}

	@Override
	List<Integer> getProcessingOpcodes() {
		return numbersAsList(OPCODE_LSTORE, OPCODE_LSTORE_0,
				OPCODE_LSTORE_1,
				OPCODE_LSTORE_2,
				OPCODE_LSTORE_3, // Store longs
				OPCODE_FSTORE, OPCODE_FSTORE_0, OPCODE_FSTORE_1,
				OPCODE_FSTORE_2,
				OPCODE_FSTORE_3, // Store Floats
				OPCODE_DSTORE, // Store doubles
				OPCODE_DSTORE_0, OPCODE_DSTORE_1, OPCODE_DSTORE_2,
				OPCODE_DSTORE_3);
	}

}
