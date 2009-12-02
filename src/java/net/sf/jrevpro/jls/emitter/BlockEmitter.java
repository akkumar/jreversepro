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
 *  * 
 */
package net.sf.jrevpro.jls.emitter;

import net.sf.jrevpro.ast.block.Block;

/**
 * 
 * Introduced Template design pattern
 * 
 * @author karthikeyanc
 */
public abstract class BlockEmitter {

  // The block may have
  public void emitJLSCode(EmitterTarget target, Block _block) {
    emitBlockBeginCode(target, _block);
    emitCurrentCode(target, _block);
    emitBlockEndCode(target, _block);
  }

  protected abstract void emitBlockBeginCode(EmitterTarget target, Block _block);

  protected void emitCurrentCode(EmitterTarget target, Block _block) {
    /* For a block - the current code is the same as emitting Children code */
    for (Block childBlock : _block.getChildren()) {
      childBlock.getEmitter().emitJLSCode(target, childBlock);
    }
  }

  protected abstract void emitBlockEndCode(EmitterTarget target, Block _block);

}
