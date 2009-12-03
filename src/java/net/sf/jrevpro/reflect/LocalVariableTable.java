/**
 *  @(#) LocalVariableTable.java
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
 ***/
package net.sf.jrevpro.reflect;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.sf.jrevpro.CustomLoggerFactory;
import net.sf.jrevpro.jls.JLSConstants;
import net.sf.jrevpro.reflect.variabletable.VariableTable;

/**
 * @author karthikeyanc
 * 
 */
public class LocalVariableTable implements VariableTable {

  public LocalVariableTable(boolean isStatic) {
    variableList = new ArrayList<LocalVariable>();
    if (!isStatic) {
      // Add an entry for the this pointer.
      // TODO: Revisit this.
      // this pointer is all over the scope- Hence length is
      // Integer.MAX_VALUE
      addLocalVariable((short) 0, (short) Integer.MAX_VALUE, (short) 0,
          (short) 0, JLSConstants.THIS, "", (short) 0);
    }
  }

  public void addLocalVariable(short startPc, short length, short nameIndx,
      short descindx, String name, String desc, short frameIndex) {

    logger
        .finer((startPc + ":" + length + ":" + name + ":" + desc + ":" + frameIndex));

    LocalVariable localVar = new LocalVariable(startPc, length, nameIndx,
        descindx, name, desc, frameIndex);
    variableList.add(localVar);

  }

  public int getMaxSymbols() {
    return variableList.size();
  }

  public String getName(int varIndex, int insIndex) {

    // when long or double is in the argument it increments index by two.
    // so the varIndex should be adjusted as per that

    for (LocalVariable locVar : variableList) {
      if (locVar.getIndex() == (varIndex)
          && (locVar.getStartPc() + locVar.getLength()) >= insIndex) {
        return locVar.getName();
      }

    }
    throw new IllegalArgumentException("varIndex: " + varIndex + " , "
        + " insIndex: " + insIndex + " not present in LocalVariableTable");
  }

  public void recordLocalDatatypeReference(int localVariableIndex,
      char jvmVariableType, int referredBytecodeIndex) {
    // This would be a NOP for this class

  }

  public void recordLocalDatatypeReference(int localVariableIndex,
      String jvmVariableType, int referredBytecodeIndex) {
    // This would be a NOP for this class
  }

  private final List<LocalVariable> variableList;

  public static class LocalVariable {
    short startPc;
    short length;
    short nameIndex;
    short descIndex;
    String name;
    String descriptor;
    // local variable Frame Index
    short index;

    public LocalVariable(short stPc, short len, short nameIndx, short descindx,
        String nm, String desc, short indx) {
      startPc = stPc;
      length = len;
      nameIndex = nameIndx;
      descIndex = descindx;
      name = nm;
      descriptor = desc;
      index = indx;
    }

    public String getDescriptor() {
      return descriptor;
    }

    public short getIndex() {
      return index;
    }

    public short getLength() {
      return length;
    }

    public String getName() {
      return name;
    }

    public short getStartPc() {
      return startPc;
    }

    public short getDescIndex() {
      return descIndex;
    }

    public short getNameIndex() {
      return nameIndex;
    }

  }

  private static final Logger logger = CustomLoggerFactory.createLogger();

}
