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
package net.sf.jrevpro.ast.evaluator;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

import net.sf.jrevpro.ast.expression.Expression;
import net.sf.jrevpro.ast.expression.MethodAccessExpression;
import net.sf.jrevpro.ast.intermediate.CompleteLine;
import net.sf.jrevpro.jvm.TypeInferrer;
import net.sf.jrevpro.reflect.instruction.Instruction;

/**
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

      processInvokeInstruction(ins, false);
      break;

    case OPCODE_INVOKESPECIAL:
      processInvokeInstruction(ins, true);
      break;

    case OPCODE_INVOKESTATIC:
      processInvokeStatic(ins);
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
  List<Integer> getProcessingOpcodes() {
    return numbersAsList(OPCODE_INVOKEVIRTUAL, OPCODE_INVOKEINTERFACE,
        OPCODE_INVOKESPECIAL, OPCODE_INVOKESTATIC);
  }

  /**
   * Processes an invoke instruction - invokespecial, invokeinterface,
   * invokevirtual.
   * 
   * @param ins
   *          Current Instruction that is to be operated on the JVM stack.
   * @param flagInvokeSpecial
   *          if this instruction is invokespecial.
   */
  private void processInvokeInstruction(Instruction ins,
      boolean flagInvokeSpecial) {
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
    if (flagInvokeSpecial) { // possibly a constructor.
      // then the type is the same as className
      methodType = className;
    }
    List<Expression> argValues = new ArrayList<Expression>(popMax);
    for (int i = popMax - 1; i >= 0; i--) {
      // add arguments in reverse order
      argValues.add(0, evalStack.pop());
    }

    Expression accessTarget = evalStack.pop();

    MethodAccessExpression mex = new MethodAccessExpression(accessTarget,
        methodName, methodType, argValues, flagInvokeSpecial);
    if (flagInvokeSpecial) {
      // Peek the top and replace the top object reference. Stack remains
      // the same.
      try {
        evalStack.pop(); // Popped expression is not needed. we are just
        // replacing it.
        evalStack.push(mex);
      } catch (EmptyStackException ex) {
        logger
            .warning("Cannot peek the stack when pushing " + mex.getJLSCode());
      }

    } else {
      if (!methodType.equals(String.valueOf(JVM_TYPE_VOID))) {
        // Non-void method - Push the result back onto the stack
        evalStack.push(mex);
      } else {
        statements.append(new CompleteLine(ins, mex));
      }
    }
  }

  /**
   * Processes the invoke instruction - Invoke static
   * 
   * @param ins
   *          Current Instruction that is to be operated on the JVM stack.
   */
  private void processInvokeStatic(Instruction ins) {
    int offset = ins.getArgUnsignedShort();
    int classIndex = pool.getPtr1(offset);
    String classType = pool.getClassName(classIndex);

    // GetMethodName
    int nameIndex = pool.getPtr2(offset);
    String methodName = pool.getFirstDirectName(nameIndex);

    // Get No: of arguments
    int argsIndex = pool.getPtr2(nameIndex);
    String argsList = pool.getEntryValue(argsIndex);
    List<String> args = TypeInferrer.getArguments(argsList);
    int popMax = args.size();

    String methodType = TypeInferrer.getReturnType(argsList);
    // Get Return type

    List<Expression> argValues = new ArrayList<Expression>(popMax);
    for (int i = popMax - 1; i >= 0; i--) {
      argValues.add(0, evalStack.pop());
    }

    MethodAccessExpression mex = new MethodAccessExpression(classType,
        methodName, methodType, argValues);

    if (!methodType.equals(String.valueOf(JVM_TYPE_VOID))) {
      // Non-void method.Push the result.
      evalStack.push(mex);
    } else {
      // Otherwise store it as a statement.
      statements.append(new CompleteLine(ins, mex));
    }
  }
}
