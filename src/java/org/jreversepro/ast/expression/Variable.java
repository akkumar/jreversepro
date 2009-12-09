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
package org.jreversepro.ast.expression;

/**
 *
 * 
 */

import org.jreversepro.reflect.variabletable.VariableTable;

public class Variable extends Expression {

  /**
   * 
   * @param _varTable
   *          The symbol table within which the variable is going to be live.
   * 
   * @param _jvmType
   *          Type of the variable loaded onto the stack.
   * 
   * @param _variableIndex
   *          index of the variable into the symbol table.
   * @param _instructionIndex
   *          index of the instruction into the bytecode array.
   * 
   */
  public Variable(VariableTable _varTable, String _jvmType, int _variableIndex,
      int _instructionIndex) {
    super(_jvmType, VALUE);
    logger.info("Creating Variable at instruction " + _instructionIndex
        + " with type " + _jvmType + " for variable " + _variableIndex);
    varTable = _varTable;
    instructionIndex = _instructionIndex;
    variableIndex = _variableIndex;

  }

  /**
   * 
   * @param _varTable
   *          The symbol table within which the variable is going to be live.
   * 
   * @param _jvmType
   *          Type of the variable loaded onto the stack.
   * 
   * @param _variableIndex
   *          index of the variable into the symbol table.
   * @param _instructionIndex
   *          index of the instruction into the bytecode array.
   * 
   */
  public Variable(VariableTable _varTable, char _jvmType, int _variableIndex,
      int _instructionIndex) {
    super(_jvmType, VALUE);
    varTable = _varTable;
    instructionIndex = _instructionIndex;
    variableIndex = _variableIndex;

  }

  public String getJLSCode() {

    // Try to get the optional debug information. if it is there follow the
    // happy path.
    String variableName = varTable.getName(variableIndex, instructionIndex);
    if (null != variableName) {
      return variableName;
    }

    // If the code was not compiled using debug information, use the Symbol
    // Table for a custom generated name
    return varTable.getName(variableIndex, instructionIndex);
  }

  private VariableTable varTable;

  private int variableIndex;

  private int instructionIndex;

}
