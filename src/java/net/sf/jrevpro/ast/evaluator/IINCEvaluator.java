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
 **/
package net.sf.jrevpro.ast.evaluator;


import net.sf.jrevpro.ast.expression.BinaryOpExpression;
import net.sf.jrevpro.ast.expression.Constant;
import net.sf.jrevpro.ast.expression.Variable;
import net.sf.jrevpro.ast.expression.BinaryOpExpression.BinaryOperator;
import net.sf.jrevpro.ast.intermediate.CompleteLine;
import net.sf.jrevpro.reflect.instruction.Instruction;

/**
 * @author akkumar
 * 
 */
public class IINCEvaluator extends AbstractInstructionEvaluator {

  /**
   * @param context
   */
  public IINCEvaluator(EvaluatorContext context) {
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

    Variable var = new Variable(varTable, JVM_TYPE_INT, ins
        .getArgUnsignedWide(), ins.currentPc);
    int constant = ins.getArgWide(1);

    if (constant < 0) {
      Constant ct = new Constant(-constant);
      statements.append(new CompleteLine(ins, new BinaryOpExpression(var,
          BinaryOperator.SMART_MINUS, ct, JVM_TYPE_INT)));
    } else {
      Constant ct = new Constant(constant);
      statements.append(new CompleteLine(ins, new BinaryOpExpression(var,
          BinaryOperator.SMART_PLUS, ct, JVM_TYPE_INT)));
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
    return numbersAsList(OPCODE_IINC);
  }

}
