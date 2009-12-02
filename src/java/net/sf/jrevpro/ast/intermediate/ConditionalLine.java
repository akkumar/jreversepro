/**
 *  @(#) ConditionalLine.java
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
package net.sf.jrevpro.ast.intermediate;

import net.sf.jrevpro.ast.expression.ConditionExpression;
import net.sf.jrevpro.decompile.BlockInferrer;
import net.sf.jrevpro.reflect.instruction.Instruction;

/**
 * @author akkumar
 * 
 */
public class ConditionalLine extends AbstractLineOfCode {

  public ConditionalLine(Instruction _ins, ConditionExpression _ex) {
    super(_ins);
    ex = _ex;
  }

  @Override
  public void regenerateBlock(BlockInferrer ctx) {
    /*
     * int targetPc = ins.getTargetPc(); if (ins.getOffset() < 0) { // TODO: It
     * is a do .. while loop. OR a while loop. // Determine w.r.t current
     * top-of-stack goto statement.
     * 
     * } else { // It is a if branch. ctx.saveConditionalBlock(ins, ex); }
     */
  }

  private ConditionExpression ex;

}
