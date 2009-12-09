/**
 *  @(#) NewArrayEvaluator.java
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

import java.util.Arrays;

import org.jreversepro.ast.expression.ArrayInstantiationExpression;
import org.jreversepro.ast.expression.Expression;
import org.jreversepro.jvm.TypeInferrer;
import org.jreversepro.reflect.instruction.Instruction;


/**
 * @author akkumar
 * 
 */
public class NewArrayEvaluator extends AbstractInstructionEvaluator {

  /**
   * @param context
   */
  public NewArrayEvaluator(EvaluatorContext context) {
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
    ArrayInstantiationExpression expr = null;
    switch (ins.opcode) {
    case OPCODE_NEWARRAY: {
      int atype = ins.getArgByte();
      Expression arraySize = evalMachine.pop();
      char jvmtype = JVM_TYPE_UNDEFINED;

      switch (atype) {
      case 4:
        jvmtype = JVM_TYPE_BOOLEAN;
        break;
      case 5:
        jvmtype = JVM_TYPE_CHAR;
        break;
      case 6:
        jvmtype = JVM_TYPE_FLOAT;
        break;
      case 7:
        jvmtype = JVM_TYPE_DOUBLE;
        break;
      case 8:
        jvmtype = JVM_TYPE_BYTE;
        break;
      case 9:
        jvmtype = JVM_TYPE_SHORT;
        break;
      case 10:
        jvmtype = JVM_TYPE_INT;
        break;
      case 11:
        jvmtype = JVM_TYPE_LONG;
        break;
      default:
        throw new UnsupportedOperationException("Type  " + atype
            + " not supported as argument of opcode OPCODE_NEWARRAY");
      }
      expr = new ArrayInstantiationExpression(jvmtype, arraySize);
      break;
    }
    case OPCODE_ANEWARRAY: {
      int offset = ins.getArgUnsignedShort();
      String classType = pool.getClassName(offset);

      Expression arraySize1 = evalMachine.pop();
      expr = new ArrayInstantiationExpression(classType, arraySize1);
      break;
    }
    case OPCODE_MULTIANEWARRAY: {
      int offset = ins.getArgUnsignedShort();

      // Dimensions. Max 255.
      int dimensions = ins.getArgUnsignedByte(2);
      if (dimensions > 255) {
        throw new IllegalArgumentException(
            "OPCODE_MULTIANEWARRAY: Max. Number of dimensions is 255. Received "
                + dimensions);
      }
      Expression arrayIndices[] = new Expression[dimensions];

      // ClassType
      String classType = TypeInferrer.getJLSType(pool.getClassName(offset),
          false);

      // Get all array indices
      for (int i = dimensions - 1; i >= 0; i--) {
        arrayIndices[i] = evalMachine.pop();
      }
      expr = new ArrayInstantiationExpression(classType, Arrays
          .asList(arrayIndices));
    }

    }
    evalMachine.push(expr);

  } 

  /*
   * (non-Javadoc)
   * 
   * @seenet.sf.jrevpro.decompile.evaluator.AbstractInstructionEvaluator#
   * getProcessingOpcodes()
   */
  @Override
  Iterable<Integer> getProcessingOpcodes() {
    return Arrays.asList(OPCODE_NEWARRAY, OPCODE_ANEWARRAY,
        OPCODE_MULTIANEWARRAY);
  }

}
