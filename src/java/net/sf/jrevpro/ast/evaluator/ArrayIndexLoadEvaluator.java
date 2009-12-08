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
 * 
 */
package net.sf.jrevpro.ast.evaluator;


import java.util.Arrays;

import net.sf.jrevpro.ast.expression.ArrayMemberReferenceExpression;
import net.sf.jrevpro.ast.expression.Expression;
import net.sf.jrevpro.reflect.instruction.Instruction;

public class ArrayIndexLoadEvaluator extends AbstractInstructionEvaluator {

  public ArrayIndexLoadEvaluator(EvaluatorContext context) {
    super(context);
  }

  @Override
  void evaluate(Instruction ins) {
    Expression subscript = evalMachine.pop();
    Expression arrayObject = evalMachine.pop();

    char type = JVM_TYPE_UNDEFINED;
    switch (ins.opcode) {
    case OPCODE_IALOAD:
      type = JVM_TYPE_INT;
      break;
    case OPCODE_LALOAD:
      type = JVM_TYPE_LONG;
      break;
    case OPCODE_FALOAD:
      type = JVM_TYPE_FLOAT;
      break;
    case OPCODE_DALOAD:
      type = JVM_TYPE_DOUBLE;
      break;
    case OPCODE_AALOAD:
      type = JVM_TYPE_REFERENCE;
      break;
    case OPCODE_BALOAD:
      type = JVM_TYPE_BOOLEAN;
      break;
    case OPCODE_CALOAD:
      type = JVM_TYPE_CHAR;
      break;
    case OPCODE_SALOAD:
      type = JVM_TYPE_SHORT;
      break;
    }
    ArrayMemberReferenceExpression expr = new ArrayMemberReferenceExpression(
        subscript, arrayObject, type);

    evalMachine.push(expr);

  }

  @Override
  Iterable<Integer> getProcessingOpcodes() {
    return Arrays.asList(OPCODE_IALOAD, OPCODE_LALOAD, OPCODE_FALOAD,
        OPCODE_DALOAD, OPCODE_AALOAD, OPCODE_BALOAD, OPCODE_CALOAD,
        OPCODE_SALOAD);
  }

}
