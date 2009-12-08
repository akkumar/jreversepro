/**
 *  @(#) FieldReferenceEvaluator.java
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

import net.sf.jrevpro.ast.expression.Assignment;
import net.sf.jrevpro.ast.expression.Expression;
import net.sf.jrevpro.ast.expression.FieldAccessExpression;
import net.sf.jrevpro.ast.expression.StaticFieldAccessExpression;
import net.sf.jrevpro.ast.intermediate.CompleteLine;
import net.sf.jrevpro.reflect.instruction.Instruction;

/**
 * @author akkumar
 * 
 */
public class StaticFieldReferenceEvaluator extends AbstractInstructionEvaluator {

  /**
   * @param context
   */
  public StaticFieldReferenceEvaluator(EvaluatorContext context) {
    super(context);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * net.sf.jrevpro.decompile.evaluator.AbstractInstructionEvaluator#evaluate
   * (net.sf.jrevpro.reflect.instruction.Instruction)
   */
  @Override
  void evaluate(Instruction ins) {
    int offset = ins.getArgUnsignedShort();

    int classPtr = pool.getPtr1(offset);
    int fieldPtr = pool.getPtr2(offset);

    String classType = pool.getClassName(classPtr);

    String fieldName = pool.getFieldName(fieldPtr);
    String fieldType = pool.getFieldType(fieldPtr);

    FieldAccessExpression expr = new StaticFieldAccessExpression(classType,
        fieldName, fieldType);

    switch (ins.opcode) {
    case OPCODE_GETSTATIC:
      evalMachine.push(expr);
      break;
    case OPCODE_PUTSTATIC:
      Expression rhs = evalMachine.pop();
      statements.append(new CompleteLine(ins, new Assignment(expr, rhs)));
      break;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @seenet.sf.jrevpro.decompile.evaluator.AbstractInstructionEvaluator#
   * getProcessingOpcodes()
   */
  @Override
  Iterable<Integer> getProcessingOpcodes() {
    return Arrays.asList(OPCODE_GETSTATIC, OPCODE_PUTSTATIC);
  }

}
