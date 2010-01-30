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
package org.jreversepro.ast.block;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jreversepro.jls.emitter.BlockEmitter;
import org.jreversepro.jls.emitter.BlockEmitterFactory;


public abstract class Block {

  public void addChildBlock(Block block) {
    children.add(block);
  }

  public void addChildBlocks(Collection<Block> _blocks) {
    if (_blocks != null) {
      children.addAll(_blocks);
    }
  }

  public List<Block> getChildren() {
    return children;
  }

  public boolean hasChildren() {
    return children.size() > 0;
  }

  public BlockEmitter getEmitter() {

    BlockEmitter emitter = null;
    try {
      emitter = BlockEmitterFactory.getBlockEmitter(this.getClass());
    } catch (Exception e) {
      // TODO Exception handling
      e.printStackTrace();
    }
    return emitter;
  }

  public void setParent(Block _parent) {
    parent = _parent;
  }

  public Block getParent() {
    return parent;
  }

  public int endOfBlock() {
    int sz = children.size();
    if (sz == 0) {
      throw new IllegalStateException("Something gone wrong");
    }
    return children.get(sz).endOfBlock();
  }

  protected Block(Block _parent) {
    children = new ArrayList<Block>();
    parent = _parent;
  }

  protected List<Block> children;

  protected Block parent;

}
