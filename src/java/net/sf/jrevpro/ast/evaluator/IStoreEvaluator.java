/**
 *  @(#) ILoadEvaluator.java
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
 *
 **/
package net.sf.jrevpro.ast.evaluator;

import java.util.Arrays;
import java.util.logging.Logger;

import net.sf.jrevpro.CustomLoggerFactory;
import net.sf.jrevpro.ast.expression.Assignment;
import net.sf.jrevpro.ast.expression.Expression;
import net.sf.jrevpro.ast.expression.Variable;
import net.sf.jrevpro.ast.intermediate.CompleteLine;
import net.sf.jrevpro.reflect.instruction.Instruction;

/**
 * @author akkumar
 * 
 */
public class IStoreEvaluator extends AbstractInstructionEvaluator {

  /**
   * @param context
   */
  public IStoreEvaluator(EvaluatorContext context) {
    super(context);
  }

  /*
   * (non-Javadoc)
   * 
   * @see net.sf.jrevpro.decompile.instructioneval.AbstractInstructionEvaluator
   * #evaluate(net.sf.jrevpro.reflect.instruction.Instruction)
   */
  @Override
  void evaluate(Instruction ins) {
    Expression exp = evalStack.pop();
    switch (ins.opcode) {
    case OPCODE_ISTORE:
      logger.info("Processing Instruction " + ins.currentPc + " istore "
          + ins.getArgUnsignedWide());
      operateStoreInstruction(ins, ins.getArgUnsignedWide(), exp);
      break;
    case OPCODE_ISTORE_0:
    case OPCODE_ISTORE_1:
    case OPCODE_ISTORE_2:
    case OPCODE_ISTORE_3:
      operateStoreInstruction(ins, ins.opcode - OPCODE_ISTORE_0, exp);
      break;

    }

  }

  protected void operateStoreInstruction(Instruction ins,
      int variableIndexToSymbolTable, Expression expr) {
    Variable lhs = new Variable(varTable, expr.getType(),
        variableIndexToSymbolTable, ins.currentPc);
    statements.append(new CompleteLine(ins, new Assignment(lhs, expr)));

    // Hint to the symbol table about the type.
    varTable.recordLocalDatatypeReference(variableIndexToSymbolTable, expr
        .getType(), ins.currentPc);

  }

  /*
   * (non-Javadoc)
   * 
   * @see net.sf.jrevpro.decompile.instructioneval.AbstractInstructionEvaluator
   * #getProcessingOpcodes()
   */
  @Override
  Iterable<Integer> getProcessingOpcodes() {
    return Arrays.asList(OPCODE_ISTORE, OPCODE_ISTORE_0, OPCODE_ISTORE_1,
        OPCODE_ISTORE_2, OPCODE_ISTORE_3);
  }

  private static final Logger logger = CustomLoggerFactory.createLogger();
}
