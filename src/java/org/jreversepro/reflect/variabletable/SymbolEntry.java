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
import java.util.List;

/**
 * LocalEntry is an entry in the SymbolTable.
 * 
 * @author Karthik Kumar
 */
class SymbolEntry {

  /**
   * 
   * @param aVarIndex
   *          Variable Index into the Symbol Table
   * @param aStoreIndex
   *          Opcode index into the method bytecode array when this variable
   *          stored / first initialized.
   * @param datatype
   *          datatype of the variable
   * @param aName
   *          Name of the variable.
   */
  SymbolEntry(int aVarIndex, int aStoreIndex, String datatype, String aName) {
    this.symbolIndex = aVarIndex;
    this.datatype = datatype;
    this.name = aName;

    references = new ArrayList<Integer>();
    references.add(aStoreIndex);
  }

  /**
   * 
   * @param datatype
   *          data type of the variable
   */
  SymbolEntry(String datatype) {
    this(-1, -1, datatype, "");
  }

  /**
   * Setter method for name,
   * 
   * @param name
   *          Name to be assigned.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return Returns the name of the variable.
   */
  public String getName() {
    return name;
  }

  /**
   * @return Returns the store index of this variable,
   */
  int getStoreIndex() {
    return references.get(0); // First reference
  }

  /**
   * @return Returns Variable Index into the symbol table for this method.
   */
  int getVarIndex() {
    return symbolIndex;
  }

  /**
   * Sets the last ReferredIndex of this line.
   * 
   * @param index
   *          Last ReferredIndex of the variable.
   */
  void appendReference(int index) {
    references.add(Integer.valueOf(index));
  }

  /**
   * @return Returns the last referred index of this variable.
   */
  int getLastReferredIndex() {
    return references.get(references.size() - 1);
  }

  /**
   * @return Returns the declaration type.
   */
  String getDeclarationType() {
    return datatype;
  }

  /**
   * @param aType
   *          Declaration Type to be set.
   */
  void setDeclarationType(String aType) {
    datatype = aType;
  }

  /**
   * @param obj
   *          Object to be compared.
   * @return if both object are equal. false, otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof SymbolEntry) {
      SymbolEntry jle = (SymbolEntry) obj;
      return (this.datatype.equals(jle.datatype));
    } else {
      return false;
    }
  }

  /**
   * @return Hashcode of this method.
   */
  @Override
  public int hashCode() {
    return datatype.hashCode();
  }

  /**
   * @return Returns the string fields form, add all references.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{ " + datatype + "  " + name + "  " + getStoreIndex()
        + " LastRef  " + getLastReferredIndex() + " }\n");
    if (true) {
      sb.append(" Ref [ ");
      for (Integer i : references) {
        sb.append(i.intValue() + " ");
      }
      sb.append("]");
    }
    sb.append("\n");
    return sb.toString();
  }

  /**
   * Index of the variable into the local symbol table.
   */
  int symbolIndex;

  /**
   * datatype of this local variable entry
   */
  String datatype;

  /**
   * Name of the symbol.
   */
  String name;

  /**
   * set of opcode indexes when this variable was referred to.
   */
  List<Integer> references;
}
