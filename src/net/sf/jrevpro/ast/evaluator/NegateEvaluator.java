/**
 *  @(#) NegateEvaluator.java
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

import net.sf.jrevpro.ast.expression.UnaryOpExpression;
import net.sf.jrevpro.ast.expression.UnaryOpExpression.UnaryOperator;
import net.sf.jrevpro.reflect.instruction.Instruction;

/**
 * @author akkumar
 * 
 */
public class NegateEvaluator extends AbstractInstructionEvaluator {

	/**
	 * @param context
	 */
	public NegateEvaluator(EvaluatorContext context) {
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
		case OPCODE_INEG:
			UnaryOpExpression.evaluateUnary(evalStack, UnaryOperator.NEGATE,
					JVM_TYPE_INT);
			break;
		case OPCODE_LNEG:
			UnaryOpExpression.evaluateUnary(evalStack, UnaryOperator.NEGATE,
					JVM_TYPE_LONG);
			break;
		case OPCODE_FNEG:
			UnaryOpExpression.evaluateUnary(evalStack, UnaryOperator.NEGATE,
					JVM_TYPE_FLOAT);
			break;
		case OPCODE_DNEG:
			UnaryOpExpression.evaluateUnary(evalStack, UnaryOperator.NEGATE,
					JVM_TYPE_DOUBLE);
			break;
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
		return numbersAsList(OPCODE_INEG, OPCODE_LNEG, OPCODE_FNEG, OPCODE_DNEG);
	}

}
