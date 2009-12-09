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
 ** 
 */
package org.jreversepro.reflect.variabletable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.jreversepro.CustomLoggerFactory;
import org.jreversepro.jls.JLSConstants;
import org.jreversepro.jls.JLSLanguageContext;
import org.jreversepro.jvm.JVMConstants;
import org.jreversepro.jvm.TypeInferrer;
import org.jreversepro.reflect.Import;
import org.jreversepro.reflect.instruction.Instruction;


/**
 * SymbolTable - Symbol Table of a method containing local variables only.
 * 
 * @author Karthik Kumar (super star) Karthikeyan C (bercolax)
 * 
 *         Modification History 01-June-2008 SymbolTable(SymbolTableContext)
 *         Constructor
 * 
 */
public class SymbolTable implements VariableTable {

  /**
   * @param context
   *          Context in which symbol table is going to be based on.
   */
  public SymbolTable(SymbolTableContext context) {

    maxSymbols = context.maxSymbols;

    symbols = new ArrayList<List<SymbolEntry>>(context.maxSymbols);
    for (int i = 0; i < maxSymbols; ++i) {
      List<SymbolEntry> singleVariableIndexList = new ArrayList<SymbolEntry>();
      symbols.add(singleVariableIndexList);
    }

    localVariableNameBasis = DEFAULT_LOCAL_VARIABLE_NAME_BASIS;

    methodReturnType = context.jvmMethodReturnType;

    // Loads the Method Arguments onto symbol table.

    symbolNames = new HashSet<String>();
    loadMethodArgumentsToSymbolTable(context.args, context.isMethodStatic);

  }

  /*
   * (non-Javadoc)
   * 
   * @see net.sf.jrevpro.ast.block.symboltable.VariableTable#
   * recordLocalDatatypeReference(int, char, int)
   */
  public void recordLocalDatatypeReference(int localVariableIndex,
      char jvmVariableType, int referredBytecodeIndex) {
    recordLocalDatatypeReference(localVariableIndex, String
        .valueOf(jvmVariableType), referredBytecodeIndex);
  }

  /*
   * (non-Javadoc)
   * 
   * @see net.sf.jrevpro.ast.block.symboltable.VariableTable#
   * recordLocalDatatypeReference(int, java.lang.String, int)
   */
  public void recordLocalDatatypeReference(int localVariableIndex,
      String jvmVariableType, int referredBytecodeIndex) {

    if (localVariableIndex > Instruction.INVALID_VAR_INDEX
        && localVariableIndex <= maxArgs) {
      logger.warning("Reference hint ignored for " + localVariableIndex
          + " with type " + jvmVariableType);
      return;
      // No reassignment of data types for arguments.
    }
    SymbolEntry ent = retrieveActiveLocalEntry(localVariableIndex,
        referredBytecodeIndex);
    if (ent != null) {
      // An entry is already present.
      if (ent.datatype.equals(jvmVariableType)) {
        // If they are the same - just set the last referred index.
        ent.appendReference(referredBytecodeIndex);
      } else {
        logger.info("Collision Detected. Adding a new local variable index  "
            + localVariableIndex + " of type " + jvmVariableType);
        addEntry(localVariableIndex, referredBytecodeIndex, jvmVariableType);

      }
    } else {

      // If type is unknown at this point - let us assume
      // it is java/lang/Object since all objects derive from the
      // same.
      // jvmVariableType = JVMConstants.CLASS_LANG_OBJECT;
      logger.info("Adding a new local variable index  " + localVariableIndex
          + " of type " + jvmVariableType + " with bytecode index "
          + referredBytecodeIndex);
      addEntry(localVariableIndex, referredBytecodeIndex, jvmVariableType);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see net.sf.jrevpro.ast.block.symboltable.VariableTable#getMaxSymbols()
   */
  public int getMaxSymbols() {
    return maxSymbols;
  }

  /*
   * (non-Javadoc)
   * 
   * @see net.sf.jrevpro.ast.block.symboltable.VariableTable#getName(int, int)
   */
  public String getName(int aVarIndex, int aInsIndex) {
    SymbolEntry entry = retrieveActiveLocalEntry(aVarIndex, aInsIndex);
    if (entry == null) {
      toString();
      throw new IllegalArgumentException(
          "Does not have any symbol entry for Variable Index:" + aVarIndex
              + ", " + "instruction index: " + aInsIndex);
    }
    return entry.getName();
  }

  /**
   * @param methodArguments
   *          List of arguments containing the argument types.
   * @param staticMethod
   *          Indicating if the given method is static or not.
   */
  private void loadMethodArgumentsToSymbolTable(List<String> methodArguments,
      boolean staticMethod) {
    int symIndex = 1; // 0- reserved for this pointer
    maxArgs = methodArguments.size();
    if (staticMethod) {
      maxArgs--;
      symIndex = 0;
      // No - this pointer / reference. Hence, maxArgs
    } else {
      addEntry(0, -1, JVMConstants.THISCLASS);
    }

    for (String argType : methodArguments) {
      addEntry(symIndex++, ARG_BYTECODE_REFERRED_INDEX, argType);
      if (argType.equals(String.valueOf(JVMConstants.JVM_TYPE_LONG))
          || argType.equals(String.valueOf(JVMConstants.JVM_TYPE_DOUBLE))) {
        // Since both long and double take two entries.
        // increment the symbol table count once again
        symIndex++;
      }
    }
  }

  /**
   * @param aVarIndex
   *          Index of local variable into symbol table.
   * @param aInsIndex
   *          Index of instruction into bytecode array of method.
   * @return Returns a matching LocalTable entry given the variable index and
   *         the instruction index.
   */
  private SymbolEntry retrieveActiveLocalEntry(int aVarIndex, int aInsIndex) {
    List<SymbolEntry> currentList = symbols.get(aVarIndex);
    SymbolEntry rightEntry = null;
    for (int i = currentList.size() - 1; i >= 0; i--) {
      SymbolEntry ent = currentList.get(i);
      if (aInsIndex >= ent.getStoreIndex()) {
        rightEntry = ent;
      }
    }
    return rightEntry;
  }

  /**
   * Adds a new entry to the Symboltable.
   * 
   * @param localVariableIndex
   *          Index of local variable into symbol table.
   * @param storeIndex
   *          Index when the variable is first initialized/ stored.
   * @param jvmType
   *          Datatype of the local variable entry.
   */
  private void addEntry(int localVariableIndex, int storeIndex, String jvmType) {
    List<SymbolEntry> currentList = symbols.get(localVariableIndex);

    String name = generateLocalVariableName(jvmType, localVariableIndex);
    SymbolEntry ent = new SymbolEntry(localVariableIndex, storeIndex, jvmType,
        name);
    if (!currentList.contains(ent)) {
      currentList.add(ent);
      if (!symbolNames.add(name)) {
        logger.warning("Symbol Name " + name + " already exists.");
        throw new RuntimeException(
            "Symbol name already exists. Cannot add new one. Hence Quitting.");
      }
    }
  }

  /**
   * Generates an name for the type and the variableIndex.
   * 
   * @param jvmType
   *          type of the variable.
   * @param aVarIndex
   *          Variable Index to symbol table.
   * @return Returns a name generated for the variable.
   */
  private String generateLocalVariableName(String jvmType, int aVarIndex) {
    int lastArrIndex = jvmType.lastIndexOf(JVMConstants.JVM_TYPE_ARRAY);
    String name;
    boolean arrType = false;
    if (lastArrIndex != -1) {
      arrType = true;
      jvmType = jvmType.substring(lastArrIndex + 1);
    }
    if (TypeInferrer.isBasicType(jvmType)) {
      name = String.valueOf((char) localVariableNameBasis);
      localVariableNameBasis++;
    } else if (jvmType.compareTo(JVMConstants.THISCLASS) == 0) {
      name = JLSConstants.THIS;
    } else {
      String jlsType = Import.getClassName(TypeInferrer.getJLSType(jvmType,
          true));
      name = jlsType.toLowerCase();
    }
    if (arrType) {
      name += LOCAL_VARIABLE_ARRAY_SUFFIX;
    }
    if (symbolNames.contains(name)
        || JLSLanguageContext.getKeywordList().contains(name)) {
      name = generateUniqueName(name, aVarIndex);
    }
    return name;
  }

  /**
   * Generate Unique name for the variables.
   * 
   * @param name
   *          Name of the variable.
   * @param aVarIndex
   *          variable index for which the name is to be generated.
   * @return Returns a new unique name in the scope of this symbol table.
   */
  private String generateUniqueName(String name, int aVarIndex) {
    StringBuilder sb = new StringBuilder(name);
    sb.append(aVarIndex);
    return sb.toString();
  }

  /**
   * @return Returns Stringified form of this class
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    int i = 0;
    for (List<SymbolEntry> entries : symbols) {
      if (entries.size() > 0) {
        sb.append(i + " = ");
        for (SymbolEntry ent : entries) {
          sb.append(ent);
        }
        ++i;
      }
    }
    return sb.toString();
  }

  /**
   * Map of the Symbols -
   * 
   * Key - local variable index // 0-based index
   * 
   * Value - List of LocalEntry. since for the same localvariable index, more
   * than one datatypes may exist (of different scope within the method).
   */
  List<List<SymbolEntry>> symbols;

  /**
   * List of symbol names of all 'LocalEntry' variables in the method.
   */
  Set<String> symbolNames;

  /**
   * Maximum number of symbols that can be in the table at any given time.
   * 
   * 0-based index.
   */
  int maxSymbols;

  /**
   * Maximum args in that symbol count mentioned in maxSymbols.
   * 
   * 0-based index.
   */
  int maxArgs;

  /**
   * Current index of basic fundamental datatype local variables
   * (non-reference), beginning at {@link #DEFAULT_LOCAL_VARIABLE_NAME_BASIS}
   */
  int localVariableNameBasis;

  /**
   * Return of the method for which the symbol table is being constructed.
   */
  String methodReturnType;

  /**
   * To represent the index of a variable into the bytecode array of a method,
   * when we store it - we use a -ve number to represent the referred bytecode
   * index for a method argument, since they are referred in the arguments and
   * hence should be out of bounds (0.. methods.length).
   */
  public static final int ARG_BYTECODE_REFERRED_INDEX = -1;

  /**
   * Local variables names start with 'i'
   */
  private static final char DEFAULT_LOCAL_VARIABLE_NAME_BASIS = 'i';

  /**
   * Suffix to the local variables that are of type Array. TODO: Better way to
   * abstract out the nomenclature part of it.
   */
  private static final String LOCAL_VARIABLE_ARRAY_SUFFIX = "Arr";

  private static final Logger logger = CustomLoggerFactory.createLogger();

}
