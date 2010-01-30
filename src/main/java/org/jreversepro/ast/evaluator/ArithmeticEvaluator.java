/**
 *  @(#) ArithmeticEvaluator.java
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
package org.jreversepro.ast.evaluator;


import java.util.Arrays;

import org.jreversepro.ast.expression.BinaryOpExpression;
import org.jreversepro.ast.expression.BinaryOpExpression.BinaryOperator;
import org.jreversepro.reflect.instruction.Instruction;


/**
 * @author akkumar
 * 
 */
public class ArithmeticEvaluator extends AbstractInstructionEvaluator {

  /**
   * @param context
   */
  public ArithmeticEvaluator(EvaluatorContext context) {
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
    // +
    case OPCODE_IADD:
      BinaryOpExpression.evaluateBinary(evalMachine, BinaryOperator.PLUS,
          JVM_TYPE_INT);
      break;
    case OPCODE_LADD:
      BinaryOpExpression.evaluateBinary(evalMachine, BinaryOperator.PLUS,
          JVM_TYPE_LONG);
      break;
    case OPCODE_FADD:
      BinaryOpExpression.evaluateBinary(evalMachine, BinaryOperator.PLUS,
          JVM_TYPE_FLOAT);
      break;
    case OPCODE_DADD:
      BinaryOpExpression.evaluateBinary(evalMachine, BinaryOperator.PLUS,
          JVM_TYPE_DOUBLE);
      break;
    // -
    case OPCODE_ISUB:
      BinaryOpExpression.evaluateBinary(evalMachine, BinaryOperator.MINUS,
          JVM_TYPE_INT);
      break;
    case OPCODE_LSUB:
      BinaryOpExpression.evaluateBinary(evalMachine, BinaryOperator.MINUS,
          JVM_TYPE_LONG);
      break;
    case OPCODE_FSUB:
      BinaryOpExpression.evaluateBinary(evalMachine, BinaryOperator.MINUS,
          JVM_TYPE_FLOAT);
      break;
    case OPCODE_DSUB:
      BinaryOpExpression.evaluateBinary(evalMachine, BinaryOperator.MINUS,
          JVM_TYPE_DOUBLE);
      break;
    // *
    case OPCODE_IMUL:
      BinaryOpExpression.evaluateBinary(evalMachine, BinaryOperator.MULTIPLY,
          JVM_TYPE_INT);
      break;
    case OPCODE_LMUL:
      BinaryOpExpression.evaluateBinary(evalMachine, BinaryOperator.MULTIPLY,
          JVM_TYPE_LONG);
      break;
    case OPCODE_FMUL:
      BinaryOpExpression.evaluateBinary(evalMachine, BinaryOperator.MULTIPLY,
          JVM_TYPE_FLOAT);
      break;
    case OPCODE_DMUL:
      BinaryOpExpression.evaluateBinary(evalMachine, BinaryOperator.MULTIPLY,
          JVM_TYPE_DOUBLE);
      break;
    // /
    case OPCODE_IDIV:
      BinaryOpExpression.evaluateBinary(evalMachine, BinaryOperator.DIVIDE,
          JVM_TYPE_INT);
      break;
    case OPCODE_LDIV:
      BinaryOpExpression.evaluateBinary(evalMachine, BinaryOperator.DIVIDE,
          JVM_TYPE_LONG);
      break;
    case OPCODE_FDIV:
      BinaryOpExpression.evaluateBinary(evalMachine, BinaryOperator.DIVIDE,
          JVM_TYPE_FLOAT);
      break;
    case OPCODE_DDIV:
      BinaryOpExpression.evaluateBinary(evalMachine, BinaryOperator.DIVIDE,
          JVM_TYPE_DOUBLE);
      break;
    // %
    case OPCODE_IREM:
      BinaryOpExpression.evaluateBinary(evalMachine, BinaryOperator.MODULO,
          JVM_TYPE_INT);
      break;
    case OPCODE_LREM:
      BinaryOpExpression.evaluateBinary(evalMachine, BinaryOperator.MODULO,
          JVM_TYPE_LONG);
      break;
    case OPCODE_FREM:
      BinaryOpExpression.evaluateBinary(evalMachine, BinaryOperator.MODULO,
          JVM_TYPE_FLOAT);
      break;
    case OPCODE_DREM:
      BinaryOpExpression.evaluateBinary(evalMachine, BinaryOperator.MODULO,
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
  Iterable<Integer> getProcessingOpcodes() {
    return Arrays.asList(OPCODE_IADD, OPCODE_LADD, OPCODE_FADD, OPCODE_DADD,
        OPCODE_ISUB, OPCODE_LSUB, OPCODE_FSUB, OPCODE_DSUB, OPCODE_IMUL,
        OPCODE_LMUL, OPCODE_FMUL, OPCODE_DMUL, OPCODE_IDIV, OPCODE_LDIV,
        OPCODE_FDIV, OPCODE_DDIV, OPCODE_IREM, OPCODE_LREM, OPCODE_FREM,
        OPCODE_DREM);
  }

}
