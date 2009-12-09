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
package org.jreversepro.decompile;

import java.util.Stack;

import org.jreversepro.ast.block.Block;
import org.jreversepro.ast.block.ConditionalBlock;
import org.jreversepro.ast.block.MethodBlock;
import org.jreversepro.ast.expression.ConditionExpression;
import org.jreversepro.reflect.instruction.Instruction;


public class BlockInferrer {

  public BlockInferrer(DecompilationContext _context) {

    decompiler = _context;

    mainBlock = new MethodBlock(decompiler.method.getBytes().length);

    currentBlock = mainBlock;

    gotoStack = new Stack<Instruction>();
  }

  public void pushGotoInstruction(Instruction ins) {
    if (ins.getOffset() > 0) {
      // Only forward-looking references are pushed.
      gotoStack.push(ins);
    }
  }

  public boolean isMultiConditionalExpression() {
    return (currentBlock instanceof ConditionalBlock)
        && !currentBlock.hasChildren();

  }

  public void appendChildBlock(Block _block) {
    _block.setParent(currentBlock);
    currentBlock.addChildBlock(_block);
  }

  public void moveUp() {
    Block parent = currentBlock.getParent();
    if (parent == null) {
      throw new IllegalStateException(
          "Cannot move up since current block is the topmost level block");
    }
    currentBlock = parent;
  }

  public void markConditionAsStatement() {
    if (multiCondition != null) {
      currentBlock.addChildBlock(multiCondition);
      currentBlock = multiCondition;
      multiCondition = null;
    }
  }

  public void saveConditionalBlock(Instruction ins, ConditionExpression expr) {
    if (multiCondition == null) {
      multiCondition = new ConditionalBlock(currentBlock, expr,
          ConditionalBlock.ConditionalType.CONDITION_IF);
      // By default it begins with an if condition.
    } else {
      // Look if we can possibly merge blocks to form a single condition
    }
  }

  public Block getMainBlock() {
    return mainBlock;
  }

  private ConditionalBlock multiCondition;

  private Stack<Instruction> gotoStack;

  private Block currentBlock;

  private Block mainBlock;

  private DecompilationContext decompiler;

}
