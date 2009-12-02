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
 * limitations under the License. * 
 */
package net.sf.jrevpro.reflect.variabletable;

public interface VariableTable {

  final int FULL_SCOPE_INSTRUCTION_INDEX = 0;

  /**
   * Records the reference for a given local variable index of inferred type -
   * jvmVariableType in the symbol table. are the
   * 
   * @param localVariableIndex
   *          variable index
   * @param jvmVariableType
   *          Data type
   * @param referredBytecodeIndex
   *          variable store index.
   */
  void recordLocalDatatypeReference(int localVariableIndex,
      char jvmVariableType, int referredBytecodeIndex);

  /**
   * Records the reference for a given local variable index of inferred type -
   * jvmVariableType in the symbol table. are the
   * 
   * @param localVariableIndex
   *          variable index
   * @param jvmVariableType
   *          Data type
   * @param referredBytecodeIndex
   *          variable store index.
   */
  void recordLocalDatatypeReference(int localVariableIndex,
      String jvmVariableType, int referredBytecodeIndex);

  /**
   * @return Get the maximum number of symbols available in the scope of the
   *         current method.
   */
  int getMaxSymbols();

  /**
   * @param aVarIndex
   *          Index of local variable into symbol table.
   * @param aInsIndex
   *          Index of instruction into bytecode array of method.
   * @return Returns a name of the variable given the variable index and the
   *         instruction index.
   */
  String getName(int aVarIndex, int aInsIndex);

}