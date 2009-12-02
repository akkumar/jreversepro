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

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import net.sf.jrevpro.CustomLoggerFactory;
import net.sf.jrevpro.ast.expression.ConditionExpression;
import net.sf.jrevpro.ast.intermediate.ConditionalLine;
import net.sf.jrevpro.ast.intermediate.LineOfCodeList;
import net.sf.jrevpro.jls.JLSConstants;
import net.sf.jrevpro.jvm.JVMConstants;
import net.sf.jrevpro.jvm.Opcodes;
import net.sf.jrevpro.reflect.ConstantPool;
import net.sf.jrevpro.reflect.instruction.Instruction;
import net.sf.jrevpro.reflect.variabletable.VariableTable;

/**
 * Evaluates the instructions (byte codes) as appropriate.
 * 
 * @author karthik.kumar
 * 
 */
public abstract class AbstractInstructionEvaluator implements JVMConstants,
    JLSConstants, Opcodes {

  public AbstractInstructionEvaluator(EvaluatorContext context) {
    pool = context.pool;
    varTable = context.varTable;
    evalStack = context.opStack;
    statements = context.statements;

  }

  abstract Iterable<Integer> getProcessingOpcodes();

  abstract void evaluate(Instruction ins);

  /**
   * No need for this present here. Remove this.
   * @param numbers
   * @return
   */
  @Deprecated
  protected List<Integer> numbersAsList(Integer... numbers) {
    return Arrays.asList(numbers);
  }

  protected void addConditional(Instruction ins, ConditionExpression expr) {
    statements.append(new ConditionalLine(ins, expr));
  }

  protected ConstantPool pool;
  protected VariableTable varTable;
  protected String methodReturnType;
  protected LineOfCodeList statements;
  protected EvaluatorStack evalStack;

  protected static final Logger logger = CustomLoggerFactory.createLogger();

}
