/**
 *  @(#) DupEvaluator.java
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
package org.jreversepro.ast.evaluator;


import java.util.Arrays;

import org.jreversepro.ast.expression.Expression;
import org.jreversepro.reflect.instruction.Instruction;


/**
 * @author akkumar
 * 
 */
public class DupEvaluator extends AbstractInstructionEvaluator {

  /**
   * @param context
   */
  public DupEvaluator(EvaluatorContext context) {
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
    try {
      switch (ins.opcode) {
      case OPCODE_DUP: { // dup
        evalMachine.push(evalMachine.peek());
        break;
      }
      case OPCODE_DUP_X1: { // dup_x1
        Expression op1 = evalMachine.pop();
        Expression op2 = evalMachine.pop();
        evalMachine.push(op1);
        evalMachine.push(op2);
        evalMachine.push((Expression) op1.clone());
        break;
      }
      case OPCODE_DUP_X2: { // dup_x2
        Expression op1 = evalMachine.pop();
        Expression op2 = evalMachine.pop();
        if (op2.isCategory1()) {
          // Cat.1
          Expression op3 = evalMachine.pop();
          evalMachine.push((Expression) op1.clone());
          evalMachine.push(op3);
        } else {
          // Cat.2
          evalMachine.push((Expression) op1.clone());
        }
        evalMachine.push(op2);
        evalMachine.push(op1);
        break;
      }
      case OPCODE_DUP2: { // dup2
        Expression op1 = evalMachine.pop();
        if (op1.isCategory1()) {
          // Cat.1
          Expression op2 = evalMachine.pop();
          evalMachine.push(op2);
          evalMachine.push(op1);
          evalMachine.push((Expression) op2.clone());
        } else {
          // Cat.2
          evalMachine.push(op1);
        }
        evalMachine.push(op1);
        break;
      }
      case OPCODE_DUP2_X1: { // dup2_x1
        Expression op1 = evalMachine.pop();
        Expression op2 = evalMachine.pop();
        if (op1.isCategory1()) {
          // Cat.1
          Expression op3 = evalMachine.pop();
          evalMachine.push(op2);
          evalMachine.push(op1);
          evalMachine.push(op3);
          evalMachine.push((Expression) op2.clone());
        } else {
          // Cat.2
          evalMachine.push(op1);
          evalMachine.push(op2);
        }
        evalMachine.push((Expression) op1.clone());
        break;
      }
      case OPCODE_DUP2_X2: { // dup2_x2
        Expression op1 = evalMachine.pop();
        Expression op2 = evalMachine.pop();
        if (op1.isCategory1()) {
          // value1-Cat1
          Expression op3 = evalMachine.pop();
          if (op2.isCategory1()) {
            // value2-Cat1
            Expression op4 = evalMachine.pop();
            evalMachine.push(op2);
            evalMachine.push(op1);
            evalMachine.push(op4);
            // Form 1.
          } else {
            // value2-Cat2
            evalMachine.push(op2);
            evalMachine.push(op1);
            // Form. 3
          }
          evalMachine.push(op3);
        } else {
          // value1-Cat2
          if (op2.isCategory1()) {
            // value2-Cat1
            Expression op3 = evalMachine.pop();
            evalMachine.push(op1);
            evalMachine.push(op3);
            // Form. 2
          } else {
            // value2-Cat2
            evalMachine.push(op1);
            // Form 4.
          }
        }
        evalMachine.push(op2);
        evalMachine.push(op1);
        break;
      }
      case OPCODE_SWAP: { // swap
        Expression op1 = evalMachine.pop();
        Expression op2 = evalMachine.pop();
        evalMachine.push(op1);
        evalMachine.push(op2);
        break;
      }
      }
    } catch (CloneNotSupportedException cex) {
      throw new UnsupportedOperationException("Clone not supported", cex);
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
    return Arrays.asList(OPCODE_DUP, OPCODE_DUP_X1, OPCODE_DUP_X2, OPCODE_DUP2,
        OPCODE_DUP2_X1, OPCODE_DUP2_X2, OPCODE_SWAP);
  }
}
