/**
 *  @(#) InvokeEvaluator.java
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
 **/
package org.jreversepro.ast.evaluator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jreversepro.ast.expression.Expression;
import org.jreversepro.ast.expression.InstanceMethodAccessExpression;
import org.jreversepro.ast.expression.MethodAccessExpression;
import org.jreversepro.ast.intermediate.CompleteLine;
import org.jreversepro.jvm.TypeInferrer;
import org.jreversepro.reflect.instruction.Instruction;


/**
 * Evaluates the following invoke instructions.
 * 
 * <p>
 * <ul>
 * <li>invokeVirtual</li>
 * <li>invokeInterface</li>
 * <li>invokeSpecial</li>
 * <li>invokeStatic</li>
 * </ul>
 * 
 * @author akkumar
 * 
 */
public class InvokeEvaluator extends AbstractInstructionEvaluator {

  /**
   * @param context
   */
  public InvokeEvaluator(EvaluatorContext context) {
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

    switch (ins.opcode) {
    case OPCODE_INVOKEVIRTUAL:
    case OPCODE_INVOKEINTERFACE:
      processInvokeInstruction(ins);
      break;

    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see net.sf.jrevpro.decompile.evaluator.AbstractInstructionEvaluator#
   * getProcessingOpcodes()
   */
  @Override
  Iterable<Integer> getProcessingOpcodes() {
    return Arrays.asList(OPCODE_INVOKEVIRTUAL, OPCODE_INVOKEINTERFACE);
  }

  /**
   * Processes an invoke instruction - invokeinterface,
   * invokevirtual.
   * 
   * @param ins
   *          Current Instruction that is to be operated on the JVM stack.
   */
  private void processInvokeInstruction(Instruction ins) {
    int offset = ins.getArgUnsignedShort();
    int classIndex = pool.getPtr1(offset);

    int nameIndex = pool.getPtr2(offset);
    String methodName = pool.getFirstDirectName(nameIndex);
    String className = pool.getClassName(classIndex);
    String argsList = pool.getEntryValue(pool.getPtr2(nameIndex));

    List<String> jvmArgTypes = TypeInferrer.getArguments(argsList);
    int popMax = jvmArgTypes.size();
    // Equals Number of Arguments

    String methodType = TypeInferrer.getReturnType(argsList);

    List<Expression> argValues = this.getArguments(popMax);
    Expression accessTarget = evalMachine.pop();

    MethodAccessExpression mex = new InstanceMethodAccessExpression(
        accessTarget, methodName, methodType, argValues);

    if (!methodType.equals(String.valueOf(JVM_TYPE_VOID))) {
      // Non-void method - Push the result back onto the stack
      evalMachine.push(mex);
    } else {
      statements.append(new CompleteLine(ins, mex));
    }

  }

  /**
   * Retrieve the top N expressions from the expression stack.
   * 
   * @param numArguments
   *          Number of arguments for which expressions need to be retrieved
   *          from the stack.
   * @return
   */
  List<Expression> getArguments(int numArguments) {
    List<Expression> expressions = new ArrayList<Expression>(numArguments);
    for (int i = numArguments - 1; i >= 0; i--) {
      // add arguments in reverse order
      expressions.add(0, evalMachine.pop());
    }
    return expressions;
  }

}
