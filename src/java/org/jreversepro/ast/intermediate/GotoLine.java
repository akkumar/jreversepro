package org.jreversepro.ast.intermediate;

import org.jreversepro.decompile.BlockInferrer;
import org.jreversepro.reflect.instruction.Instruction;


public class GotoLine extends AbstractLineOfCode {

  public GotoLine(Instruction _ins) {
    super(_ins);
  }

  @Override
  public void regenerateBlock(BlockInferrer ctx) {
    // TODO Auto-generated method stub

  }

}
