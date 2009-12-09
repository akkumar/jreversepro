/**
 *  @(#)Method.java
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
package org.jreversepro.reflect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jreversepro.jvm.JVMConstants;
import org.jreversepro.jvm.TypeInferrer;
import org.jreversepro.reflect.variabletable.DynamicVariableTable;
import org.jreversepro.reflect.variabletable.DynamicVariableTableContext;
import org.jreversepro.reflect.variabletable.VariableTable;


/**
 * <b>Method</b> is the abstract representation of a method in the class
 * method..
 * 
 * @author Karthik Kumar
 * 
 *         Modified by karthikeyan c - added LocalvariableTable an optional
 *         attribute
 */
public class Method extends Member {

  /**
   * 
   */
  public Method() {

    exceptionBlocks = new ArrayList<MethodException>();

    throwsClasses = new ArrayList<String>(2);

    variableTable = null;

  }

  /**
   * Setter method for Signature
   * 
   * @param rhsSign
   *          Signature field value.
   */
  public void setSignature(String rhsSign) {
    signature = rhsSign;
  }

  /**
   * Add an exception block.
   * 
   * @param startPc
   *          Start of the try block
   * @param endPc
   *          End of try block
   * @param handlerPc
   *          Beginning of handler block
   * @param datatype
   *          Type of the class that the handler is going to handle.
   */
  public void addExceptionBlock(int startPc, int endPc, int handlerPc,
      String datatype) {

    MethodException exc = new MethodException(startPc, endPc, handlerPc,
        datatype);

    // Probably some changes to the keys put in the list.
    int tryIndex = exceptionBlocks.indexOf(exc);
    if (tryIndex == -1) {
      exceptionBlocks.add(exc);
    } else {
      MethodException oldTry = exceptionBlocks.get(tryIndex);
      oldTry.addCatchBlock(handlerPc, datatype);
    }
  }

  /**
   * Sets the line number table.
   * 
   * @param rhsLineTable
   *          Line number Table that is created by the compiler if the debugging
   *          option is on.
   */
  public void setLineTable(LineNumberTable rhsLineTable) {
    lineTable = rhsLineTable;
  }

  /**
   * Returns the return type of the method.
   * 
   * @return Returns the return type of the method.
   */
  public String getReturnType() {
    return TypeInferrer.getReturnType(signature);
  }

  /**
   * Returns the signature of the method.
   * 
   * @return Returns the signature of the method.
   */
  public String getSignature() {
    return signature;
  }

  /**
   * Returns the argument list. The members of the list are String.
   * 
   * @return Returns the argument list.
   */
  public List<String> getArgList() {
    return TypeInferrer.getArguments(signature);
  }

  /**
   * Returns the .list of classes thrown by this method. The individual members
   * are string.
   * 
   * @return Returns the list of thrown classes
   */
  public List<String> getThrowsClasses() {
    return throwsClasses;
  }

  /**
   * Returns the maximum local members of this method.
   * 
   * @return Returns the Max Locals
   */
  public int getMaxLocals() {
    return maxLocals;
  }

  /**
   * Returns the max. stack
   * 
   * @return Returns max stack.
   */
  public int getMaxStack() {
    return maxStack;
  }

  /**
   * Sets the .list of classes thrown by this method. The individual members are
   * string.
   * 
   * @param throwsClasses
   *          Classes Thrown
   */
  public void setThrowsClasses(List<String> throwsClasses) {
    this.throwsClasses = throwsClasses;
  }

  /**
   * Sets the max. local variable field
   * 
   * @param maxLocals
   *          Max Locals
   */
  public void setMaxLocals(int maxLocals) {
    this.maxLocals = maxLocals;
  }

  /**
   * Sets the max. stack variable field.
   * 
   * @param maxStack
   *          Max. Stack length
   */
  public void setMaxStack(int maxStack) {
    this.maxStack = maxStack;
  }

  /**
   * Returns the bytecode array of the method
   * 
   * @return bytecode array.
   */
  public byte[] getBytes() {
    return bytecodes;
  }

  /**
   * Sets the bytecode array.
   * 
   * @param bytecodes
   *          the bytecode array input.
   */
  public void setBytes(byte[] bytecodes) {
    this.bytecodes = bytecodes;
  }

  /**
   * 
   * @return the optional local variable table info
   */
  public VariableTable getVariableTable() {
    return variableTable;
  }

  public void setVariableTable(VariableTable localVarTable) {
    this.variableTable = localVarTable;
  }

  /**
   * Returns the exception table.
   * 
   * @return Returns the exception table.
   */
  public List<MethodException> getexceptionBlocks() {
    return exceptionBlocks;
  }

  /**
   * Returns a map
   * 
   * @return Returns a map of exception tables.
   */
  public Map<Integer, String> getAllExceptionsAsMap() {
    Map<Integer, String> newMap = new HashMap<Integer, String>();
    for (MethodException exc : exceptionBlocks) {
      newMap.putAll(exc.excCatchTable);
    }
    return newMap;
  }

  /**
   * Returns the LineNumberTable of the method.
   * 
   * @return Returns the LineNumberTable of the method.
   */
  public LineNumberTable getLineTable() {
    return lineTable;
  }

  public void initializeSymbolTable() {
    if (variableTable == null) {
      DynamicVariableTableContext ctx = new DynamicVariableTableContext(this);
      variableTable = new DynamicVariableTable(ctx);
    } else {
      throw new IllegalStateException(
          "VariableTable is already set. We should not be calling this");
    }
  }

  /**
   * Returns the initial code / information about the method. It consists of
   * maximum local information maximum stack information and the exception class
   * handler information.
   * 
   * @return Returns the code/information about the method.
   */
  public String getLocalStackInfo() {
    StringBuilder result = new StringBuilder("");
    result.append("\n\t  // Max Locals " + maxLocals);
    result.append("  , Max Stack " + maxStack);
    if (exceptionBlocks != null) {
      result.append("\n\n\t  /**");
      result.append("\n\t\tFrom  To  Handler\tClass\n");
      for (MethodException exc : exceptionBlocks) {
        result.append(exc.toString());
      }
      result.append("\t  **/\n");
    }
    // TODO: appending the Exception table info. to be done.
    return result.toString();
  }

  /**
   * Returns ifthis method is static.
   * 
   * @return Returns true, if this is a static method. false, otherwise.
   */
  @Override
  public boolean isStatic() {
    return (super.isStatic() || name.equals(JVMConstants.CLINIT));
  }

  /**
   * Signature of a method.
   * 
   * For example for a method as follows.
   * 
   * public void doSomething( int ,int ) //random method signature the signature
   * will be (II)V .
   */
  private String signature;

  /**
   * Throws classes is a List containing String of the java data types that are
   * thrown by this method.
   */
  private List<String> throwsClasses;

  /**
   * Maximum size that the JVM stack will occupy on execution of all
   * instructions present in this method.
   */
  private int maxStack;

  /**
   * Maximum number of local variables present in the local variable table at
   * any time.
   */
  private int maxLocals;

  /**
   * This contains the bytecodes that are present for the method.
   */
  private byte[] bytecodes;

  /**
   * This list contains the exception tables that are defined for this method.
   * The members of this list are - JException
   */
  private final List<MethodException> exceptionBlocks; // exception table

  /**
   * This contains the LineNumberTable that may be compiled for this method.
   * Nevertheless for decompiling purposes this cant be used since this is
   * optional information in a class file. If debugging is turned off then the
   * compiler wont bother to generate this LineNumberTable.
   */
  private LineNumberTable lineTable;

  /**
   * The LocalVariableTable optional variable-length attribute providing debug
   * information and also to provide meaningful names to parameters of a method
   * as well as local variables in a method. This will be populated and used
   * only when debugging is turned on [using -g option]. Else the deompiler will
   * assign its own name.
   * 
   */
  private VariableTable variableTable;

}
