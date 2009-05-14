/**
 *  @(#) NonStaticFieldAccessorEvaluator.java
 *
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
package net.sf.jrevpro.ast.evaluator;

import java.util.List;

import net.sf.jrevpro.ast.expression.Assignment;
import net.sf.jrevpro.ast.expression.Expression;
import net.sf.jrevpro.ast.expression.FieldAccessExpression;
import net.sf.jrevpro.ast.intermediate.CompleteLine;
import net.sf.jrevpro.reflect.instruction.Instruction;

/**
 * @author akkumar
 * 
 */
public class NonStaticFieldReferenceEvaluator extends
		AbstractInstructionEvaluator {

	/**
	 * @param context
	 */
	public NonStaticFieldReferenceEvaluator(EvaluatorContext context) {
		super(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.jrevpro.decompile.evaluator.AbstractInstructionEvaluator#evaluate
	 * (net.sf.jrevpro.reflect.instruction.Instruction)
	 */
	@Override
	void evaluate(Instruction ins) {
		switch (ins.opcode) {
		case OPCODE_GETFIELD: {
			Expression op1 = evalStack.pop();

			int offset = ins.getArgUnsignedShort();

			int fieldPtr = pool.getPtr2(offset);

			String fieldName = pool.getFieldName(fieldPtr);
			String fieldType = pool.getFieldType(fieldPtr);

			FieldAccessExpression expr = new FieldAccessExpression(op1,
					fieldName, fieldType);
			evalStack.push(expr);
			break;
		}
		case OPCODE_PUTFIELD: {

			Expression rhs = evalStack.pop();
			Expression accessTarget = evalStack.pop();

			int offset = ins.getArgUnsignedShort();

			int fieldPtr = pool.getPtr2(offset);
			String fieldName = pool.getFieldName(fieldPtr);
			String fieldType = pool.getFieldType(fieldPtr);

			FieldAccessExpression expr = new FieldAccessExpression(
					accessTarget, fieldName, fieldType);
			Assignment assign = new Assignment(expr, rhs);
			statements.append(new CompleteLine(ins, assign));
			break;
		}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seenet.sf.jrevpro.decompile.evaluator.AbstractInstructionEvaluator#
	 * getProcessingOpcodes()
	 */
	@Override
	List<Integer> getProcessingOpcodes() {
		return numbersAsList(OPCODE_GETFIELD, OPCODE_PUTFIELD);
	}

}
