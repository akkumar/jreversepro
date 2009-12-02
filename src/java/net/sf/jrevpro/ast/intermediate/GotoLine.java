package net.sf.jrevpro.ast.intermediate;

import net.sf.jrevpro.decompile.BlockInferrer;
import net.sf.jrevpro.reflect.instruction.Instruction;

public class GotoLine extends AbstractLineOfCode {

  public GotoLine(Instruction _ins) {
    super(_ins);
  }

  @Override
  public void regenerateBlock(BlockInferrer ctx) {
    // TODO Auto-generated method stub

  }

}
