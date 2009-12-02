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
package net.sf.jrevpro.ast.intermediate;

import net.sf.jrevpro.decompile.BlockInferrer;
import net.sf.jrevpro.reflect.instruction.Instruction;

/**
 * Represents the abstraction of a statement
 * 
 * @author akkumar
 * 
 */
public abstract class AbstractLineOfCode {

  /**
   * 
   * @param _expr
   *          Expression contained within a given statement.
   */
  protected AbstractLineOfCode(Instruction _ins) {
    if (_ins == null) {
      throw new IllegalArgumentException(
          "Instruction passed to a LineOfCode can never be null");
    }
    ins = _ins;
  }

  public int feedToInferrer(BlockInferrer ctx) {
    if (!(this instanceof ConditionalLine)) {
      ctx.markConditionAsStatement();
    }
    regenerateBlock(ctx);
    return ins.nextPc;
  }

  protected abstract void regenerateBlock(BlockInferrer ctx);

  protected Instruction ins;

}
