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
package org.jreversepro.jls.emitter.java14;

import org.jreversepro.ast.block.Block;
import org.jreversepro.ast.block.MethodBlock;
import org.jreversepro.jls.emitter.EmitterTarget;
import org.jreversepro.jls.emitter.SourceEmitter;


public class DefaultSourceEmitter implements SourceEmitter {

  public String emitCode(Block block) {
    if (!(block instanceof MethodBlock)) {
      throw new IllegalArgumentException(
          "I take only MethodBlocks. Can't take " + block.getClass().getName());
    }
    EmitterTarget target = new EmitterTarget();
    block.getEmitter().emitJLSCode(target, block);
    return target.getEmittedCode();
  }

}
