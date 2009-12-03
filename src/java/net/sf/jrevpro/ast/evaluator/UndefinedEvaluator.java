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

import java.util.ArrayList;
import java.util.List;

import net.sf.jrevpro.reflect.instruction.Instruction;

public class UndefinedEvaluator extends AbstractInstructionEvaluator {

  public UndefinedEvaluator(EvaluatorContext context) {
    super(context);
  }

  @Override
  void evaluate(Instruction ins) {
    throw new UnsupportedOperationException("Opcode " + ins.opcode
        + " not supported ");
  }

  @Override
  Iterable<Integer> getProcessingOpcodes() {
    List<Integer> result = new ArrayList<Integer>();
    result.add(OPCODE_XXXUNUSEDXXX); // opcode
    for (int i = OPCODE_UNUSED_START; i <= OPCODE_UNUSED_END; ++i) {
      result.add(Integer.valueOf(i));
    }
    return result;
  }

}
