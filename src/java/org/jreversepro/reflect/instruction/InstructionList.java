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
 * limitations under the License. * 
 */
package org.jreversepro.reflect.instruction;

import java.util.ArrayList;
import java.util.List;

/**
 * List of instructions in a given method.
 * 
 */
public class InstructionList {

  private final List<Instruction> instructions;

  public InstructionList() {
    instructions = new ArrayList<Instruction>();
  }

  public void add(Instruction ins) {
    instructions.add(ins);
 
  }

  /**
   * Returns the JInstruction having the specified byte offset
   * 
   * @param ind
   *          Index of the instruction.
   * @return Returns the JInstruction in the method.
   */
  public Instruction getInstruction(int ind) {
    if (instructions == null) {
      return null;
    }

    for (Instruction sIns : instructions) {
      if (sIns.currentPc == ind) {
        return sIns;
      }
    }
    return null;
  }

  /**
   * Returns the JInstruction following the instruction having the specified
   * byte offset
   * 
   * @param ind
   *          Index of the instruction.
   * @return Returns the JInstruction in the method.
   */
  public Instruction getNextInstruction(int ind) {
    if (instructions == null) {
      return null;
    }

    // now search for the ins
    Instruction tIns = null;
    for (int i = 0; i < instructions.size(); i++) {
      Instruction sIns = instructions.get(i);
      if (sIns.currentPc == ind) {
        i++;
        // now make sure it's not the last ins
        if (i < instructions.size()) {
          tIns = instructions.get(i);
        }
        break;
      }
    }
    return tIns;
  }

  public List<Instruction> getAllInstructions() {
    return instructions;
  }


}
