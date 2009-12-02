/**
 *  @(#) ReturnEvaluator.java
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

import net.sf.jrevpro.ast.expression.Expression;
import net.sf.jrevpro.ast.expression.ReturnExpression;
import net.sf.jrevpro.ast.intermediate.CompleteLine;
import net.sf.jrevpro.reflect.instruction.Instruction;

/**
 * @author akkumar
 * 
 */
public class ReturnEvaluator extends AbstractInstructionEvaluator {

  /**
   * @param context
   */
  public ReturnEvaluator(EvaluatorContext context) {
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
    ReturnExpression expr = null;
    if (ins.opcode == OPCODE_RETURN) {
      expr = new ReturnExpression();
    } else {
      Expression op1 = evalStack.pop();

      switch (ins.opcode) {
      case OPCODE_IRETURN:
        expr = new ReturnExpression(op1, methodReturnType);
        break;
      case OPCODE_LRETURN:
      case OPCODE_FRETURN:
      case OPCODE_DRETURN:
      case OPCODE_ARETURN:
        expr = new ReturnExpression(op1, JVM_TYPE_VOID);
        break;
      default:
        throw new IllegalArgumentException("Unsupported return code "
            + ins.opcode);
      }

    }
    statements.append(new CompleteLine(ins, expr));

  }

  /*
   * (non-Javadoc)
   * 
   * @seenet.sf.jrevpro.decompile.evaluator.AbstractInstructionEvaluator#
   * getProcessingOpcodes()
   */
  @Override
  Iterable<Integer> getProcessingOpcodes() {
    return numbersAsList(OPCODE_IRETURN, OPCODE_LRETURN, OPCODE_FRETURN,
        OPCODE_DRETURN, OPCODE_ARETURN, OPCODE_RETURN);
  }

}
