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
package org.jreversepro.ast.evaluator;

import java.util.Arrays;

import org.jreversepro.ast.expression.Variable;
import org.jreversepro.reflect.instruction.Instruction;


public class ReferenceLoadEvaluator extends AbstractInstructionEvaluator {

  public ReferenceLoadEvaluator(EvaluatorContext context) {
    super(context);
  }

  @Override
  void evaluate(Instruction ins) {
    switch (ins.opcode) {
    case OPCODE_ALOAD:
      operateLoadInstruction(ins, ins.getArgUnsignedWide());
      break;
    case OPCODE_ALOAD_0:
    case OPCODE_ALOAD_1:
    case OPCODE_ALOAD_2:
    case OPCODE_ALOAD_3:
      operateLoadInstruction(ins, ins.opcode - OPCODE_ALOAD_0);
      break;

    }
  }

  private void operateLoadInstruction(Instruction ins,
      int variableIndexToSymbolTable) {
    Variable var = new Variable(varTable, JVM_TYPE_REFERENCE,
        variableIndexToSymbolTable, ins.currentPc);

    evalMachine.push(var);
  }

  @Override
  Iterable<Integer> getProcessingOpcodes() {
    return Arrays.asList(OPCODE_ALOAD, OPCODE_ALOAD_0, OPCODE_ALOAD_1,
        OPCODE_ALOAD_2, OPCODE_ALOAD_3);
  }
}
