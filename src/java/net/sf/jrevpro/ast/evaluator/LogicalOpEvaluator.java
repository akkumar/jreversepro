/**
 *  @(#) LogicalOpEvaluator.java
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


import java.util.Arrays;

import net.sf.jrevpro.ast.expression.BinaryOpExpression;
import net.sf.jrevpro.ast.expression.BinaryOpExpression.BinaryOperator;
import net.sf.jrevpro.reflect.instruction.Instruction;

/**
 * @author akkumar
 * 
 */
public class LogicalOpEvaluator extends AbstractInstructionEvaluator {

  /**
   * @param context
   */
  public LogicalOpEvaluator(EvaluatorContext context) {
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
    case OPCODE_ISHL:
      BinaryOpExpression.evaluateBinary(evalStack, BinaryOperator.SHIFTLEFT,
          JVM_TYPE_INT);
      break;
    case OPCODE_LSHL:
      BinaryOpExpression.evaluateBinary(evalStack, BinaryOperator.SHIFTLEFT,
          JVM_TYPE_LONG);
      break;
    case OPCODE_ISHR:
      BinaryOpExpression.evaluateBinary(evalStack, BinaryOperator.SHIFTRIGHT,
          JVM_TYPE_INT);
      break;
    case OPCODE_IUSHR:
      BinaryOpExpression.evaluateBinary(evalStack, BinaryOperator.SHIFTRIGHT,
          JVM_TYPE_INT);
      break;
    case OPCODE_LSHR:
      BinaryOpExpression.evaluateBinary(evalStack, BinaryOperator.SHIFTRIGHT,
          JVM_TYPE_LONG);
      break;
    case OPCODE_LUSHR:
      BinaryOpExpression.evaluateBinary(evalStack, BinaryOperator.SHIFTRIGHT,
          JVM_TYPE_LONG);
      break;
    case OPCODE_IAND:
      BinaryOpExpression.evaluateBinary(evalStack, BinaryOperator.BITWISE_AND,
          JVM_TYPE_INT);
      break;
    case OPCODE_LAND:
      BinaryOpExpression.evaluateBinary(evalStack, BinaryOperator.BITWISE_AND,
          JVM_TYPE_LONG);
      break;
    case OPCODE_IOR:
      BinaryOpExpression.evaluateBinary(evalStack, BinaryOperator.BITWISE_OR,
          JVM_TYPE_INT);
      break;
    case OPCODE_LOR:
      BinaryOpExpression.evaluateBinary(evalStack, BinaryOperator.BITWISE_OR,
          JVM_TYPE_LONG);
      break;
    case OPCODE_IXOR:
      BinaryOpExpression.evaluateBinary(evalStack, BinaryOperator.BITWISE_XOR,
          JVM_TYPE_INT);
      break;
    case OPCODE_LXOR:
      BinaryOpExpression.evaluateBinary(evalStack, BinaryOperator.BITWISE_XOR,
          JVM_TYPE_LONG);
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
    return Arrays.asList(OPCODE_ISHL, OPCODE_LSHL, OPCODE_ISHR, OPCODE_IUSHR,
        OPCODE_LSHR, OPCODE_LUSHR, OPCODE_IAND, OPCODE_LAND, OPCODE_IOR,
        OPCODE_LOR, OPCODE_IXOR, OPCODE_LXOR);
  }
}
