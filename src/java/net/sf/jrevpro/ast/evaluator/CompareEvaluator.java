/**
 *  @(#) CompareEvaluator.java
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

import java.util.Arrays;
import java.util.List;

import net.sf.jrevpro.ast.expression.ConditionExpression;
import net.sf.jrevpro.ast.expression.Constant;
import net.sf.jrevpro.ast.expression.Expression;
import net.sf.jrevpro.ast.expression.ConditionExpression.RelationalOperator;
import net.sf.jrevpro.reflect.instruction.Instruction;

/**
 * @author akkumar
 * 
 */
public class CompareEvaluator extends AbstractInstructionEvaluator {

  /**
   * @param context
   */
  public CompareEvaluator(EvaluatorContext context) {
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
    Expression rhs = evalStack.pop();
    Expression lhs = evalStack.pop();

    evalStack.conditionExpression = null; // Fix memory leaks
    evalStack.conditionExpression = new ConditionExpression(lhs, rhs,
        RelationalOperator.EQ);

    evalStack.push(Constant.VALUE_1);
  }

  /*
   * (non-Javadoc)
   * 
   * @seenet.sf.jrevpro.decompile.evaluator.AbstractInstructionEvaluator#
   * getProcessingOpcodes()
   */
  @Override
  Iterable<Integer> getProcessingOpcodes() {
    return Arrays.asList(OPCODE_LCMP, OPCODE_FCMPL, OPCODE_FCMPG, OPCODE_DCMPL,
        OPCODE_DCMPG);

  }

}
