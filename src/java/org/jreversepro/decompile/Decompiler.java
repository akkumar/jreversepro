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

import java.util.logging.Logger;

import org.jreversepro.CustomLoggerFactory;
import org.jreversepro.ast.block.Block;
import org.jreversepro.ast.evaluator.EvaluatorContext;
import org.jreversepro.ast.evaluator.RuntimeFrame;
import org.jreversepro.ast.intermediate.AbstractLineOfCode;
import org.jreversepro.ast.intermediate.LineOfCodeList;
import org.jreversepro.reflect.instruction.Instruction;
import org.jreversepro.reflect.variabletable.VariableTable;


public class Decompiler {

  public Decompiler(DecompilationContext _ctx) {
    context = _ctx;

  }

  public Block extractAST() {
    EvaluatorContext evalInfo = evaluateInstructions();
    // After evaluating all the instructions -
    // the EvaluatorContext in RuntimeFrame
    // would have consolidated the list of statements in the program/
    // (ignoring all control flows).
    // We are retrieving the same.
    return inferBlocks(evalInfo.getStatements());
  }

  private Block inferBlocks(LineOfCodeList lines) {
    BlockInferrer ctx = new BlockInferrer(context);
    int nextPc = 0;

    for (AbstractLineOfCode line : lines.getAsList()) {
      nextPc = line.feedToInferrer(ctx);
    }

    return ctx.getMainBlock();
  }

  private EvaluatorContext evaluateInstructions() {
    RuntimeFrame frame = new RuntimeFrame(context.constantPool, context.method
        .getVariableTable());

    for (Instruction ins : context.list.getAllInstructions()) {
      frame.evaluateInstruction(ins);
    }
    return frame.getEvaluationContext();

  }

  private VariableTable symbolTable;

  private DecompilationContext context;

  private Logger logger = CustomLoggerFactory.createLogger();
}
