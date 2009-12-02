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
package net.sf.jrevpro.ast.evaluator;


import net.sf.jrevpro.ast.expression.Assignment;
import net.sf.jrevpro.ast.expression.Expression;
import net.sf.jrevpro.ast.expression.Variable;
import net.sf.jrevpro.ast.intermediate.CompleteLine;
import net.sf.jrevpro.reflect.instruction.Instruction;

public class ReferenceStoreEvaluator extends AbstractInstructionEvaluator {

  public ReferenceStoreEvaluator(EvaluatorContext context) {
    super(context);
  }

  @Override
  void evaluate(Instruction ins) {
    switch (ins.opcode) {
    case OPCODE_ASTORE:
      operateStoreInstruction(ins, ins.getArgUnsignedWide());
      break;
    case OPCODE_ASTORE_0:
    case OPCODE_ASTORE_1:
    case OPCODE_ASTORE_2:
    case OPCODE_ASTORE_3:
      operateStoreInstruction(ins, ins.opcode - OPCODE_ASTORE_0);
      break;

    }
  }

  private void operateStoreInstruction(Instruction ins,
      int variableIndexToSymbolTable) {
    int indexToSymbolTable = variableIndexToSymbolTable;
    Expression rhs = evalStack.pop();
    Variable lhs = new Variable(varTable, rhs.getType(), indexToSymbolTable,
        ins.currentPc);
    statements.append(new CompleteLine(ins, new Assignment(lhs, rhs)));

    // Hint to the symbol table about the type.
    varTable.recordLocalDatatypeReference(variableIndexToSymbolTable, rhs
        .getType(), ins.currentPc);
  }

  @Override
  Iterable<Integer> getProcessingOpcodes() {
    return numbersAsList(OPCODE_ASTORE, OPCODE_ASTORE_0, OPCODE_ASTORE_1,
        OPCODE_ASTORE_2, OPCODE_ASTORE_3);
  }
}
