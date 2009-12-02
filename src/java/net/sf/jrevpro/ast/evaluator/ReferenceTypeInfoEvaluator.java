/**
 *  @(#) ReferenceTypeInfoEvaluator.java
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

import net.sf.jrevpro.ast.expression.ArrayLengthExpression;
import net.sf.jrevpro.ast.expression.Expression;
import net.sf.jrevpro.ast.expression.InstanceOfExpression;
import net.sf.jrevpro.ast.expression.ObjectInstantiationExpression;
import net.sf.jrevpro.ast.expression.ThrowExpression;
import net.sf.jrevpro.ast.expression.UnaryOpExpression;
import net.sf.jrevpro.ast.expression.UnaryOpExpression.UnaryOperator;
import net.sf.jrevpro.ast.intermediate.CompleteLine;
import net.sf.jrevpro.reflect.instruction.Instruction;

/**
 * @author akkumar
 * 
 */
public class ReferenceTypeInfoEvaluator extends AbstractInstructionEvaluator {

  /**
   * @param context
   */
  public ReferenceTypeInfoEvaluator(EvaluatorContext context) {
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
    case OPCODE_NEW: { // new
      int offset = ins.getArgUnsignedShort();
      ObjectInstantiationExpression expr = new ObjectInstantiationExpression(
          pool.getClassName(offset));
      evalStack.push(expr);
      break;
    }
    case OPCODE_ARRAYLENGTH: {
      Expression arrayReference = evalStack.pop();
      ArrayLengthExpression expr = new ArrayLengthExpression(arrayReference);
      evalStack.push(expr);
      break;
    }
    case OPCODE_ATHROW: {
      Expression thrownClass = evalStack.pop();
      ThrowExpression expr = new ThrowExpression(thrownClass);
      statements.append(new CompleteLine(ins, expr));
      break;
    }
    case OPCODE_CHECKCAST: {
      int offset = ins.getArgUnsignedShort();

      // Get Class Name
      Expression expr = evalStack.pop();

      String castType = pool.getClassName(offset);

      evalStack.push(new UnaryOpExpression(expr, UnaryOperator.CAST_REFERENCE,
          castType));
      // No Change to JVM Stack
      break;
    }
    case OPCODE_INSTANCEOF: {
      Expression reference = evalStack.pop();
      int offset = ins.getArgUnsignedShort();

      // Class Type found here
      String classType = pool.getClassName(offset);
      InstanceOfExpression expr = new InstanceOfExpression(reference, classType);

      evalStack.push(expr);
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
    return numbersAsList(OPCODE_NEW, OPCODE_ARRAYLENGTH, OPCODE_ATHROW,
        OPCODE_CHECKCAST, OPCODE_INSTANCEOF);
  }

}
