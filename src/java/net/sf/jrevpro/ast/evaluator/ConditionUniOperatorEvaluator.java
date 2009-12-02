/**
 *  @(#) ConditionUniOperatorEvaluator.java
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
 *
 **/
package net.sf.jrevpro.ast.evaluator;


import net.sf.jrevpro.ast.expression.ConditionExpression;
import net.sf.jrevpro.ast.expression.Constant;
import net.sf.jrevpro.ast.expression.Expression;
import net.sf.jrevpro.ast.expression.ConditionExpression.RelationalOperator;
import net.sf.jrevpro.jls.JLSConstants;
import net.sf.jrevpro.reflect.instruction.Instruction;

/**
 * @author akkumar
 * 
 */
public class ConditionUniOperatorEvaluator extends AbstractInstructionEvaluator {

  /**
   * @param context
   */
  public ConditionUniOperatorEvaluator(EvaluatorContext context) {
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
    Expression lhs = evalStack.pop();

    RelationalOperator op = RelationalOperator.EQ;

    switch (ins.opcode) {
    case OPCODE_IFEQ:
      op = RelationalOperator.EQ;
      break;
    case OPCODE_IFNE:
      op = RelationalOperator.NE;
      break;
    case OPCODE_IFLT:
      op = RelationalOperator.LT;
      break;
    case OPCODE_IFLE:
      op = RelationalOperator.LE;
      break;
    case OPCODE_IFGE:
      op = RelationalOperator.GE;
      break;
    case OPCODE_IFGT:
      op = RelationalOperator.GT;
      break;

    }
    if (evalStack.prevOpcode < OPCODE_LCMP
        || evalStack.prevOpcode > OPCODE_DCMPG) {
      // To be compared with 0
      evalStack.conditionExpression = null;
      Expression rhs = new Constant(JLSConstants.VALUE_0, lhs.getType());

      evalStack.conditionExpression = new ConditionExpression(lhs, rhs, op);
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @seenet.sf.jrevpro.decompile.evaluator.AbstractInstructionEvaluator#
   * getProcessingOpcodes()
   */
  @Override
  Iterable<Integer> getProcessingOpcodes() {
    return numbersAsList(OPCODE_IFEQ, OPCODE_IFNE, OPCODE_IFLT, OPCODE_IFLE,
        OPCODE_IFGT, OPCODE_IFGE);
  }

}
