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
 **/
package net.sf.jrevpro.gui;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import net.sf.jrevpro.reflect.ConstantPool;

/**
 * Provides a reusable JPoolTable that uses JTablePoolModel used to provide
 * Constant Pool Entries. Provides the TableModel for the ConstantPool Model.
 */
@SuppressWarnings("serial")
public class TableConstantPool extends JTable {
  public TableConstantPool(ConstantPool RhsCpInfo) {
    super(new JPoolTableModel(RhsCpInfo));
  }
}

/*
 * Has the JPoolTableModel for the JTable.
 */
@SuppressWarnings("serial")
class JPoolTableModel extends AbstractTableModel {
  private int TotRows;
  private static final int MAX_COLUMNS = 5;

  String[] ColName;
  ConstantPool CpInfo;

  public JPoolTableModel(ConstantPool RhsCpInfo) {
    TotRows = RhsCpInfo.getMaxCpEntry();

    CpInfo = RhsCpInfo;
    initColumnNames();
  }

  public int getColumnCount() {
    return MAX_COLUMNS;
  }

  public int getRowCount() {
    return TotRows;
  }

  public Object getValueAt(int row, int col) {
    // Col : 0..4 index.
    if (row == 0) {
      return (Object) (new Integer(0));
    } else {
      switch (col) {
      case 0:
        return String.valueOf(row);
      case 1:
        return fillTagByte(row);
      case 2:
        return fillValue(row);
      case 3:
        return fillPtr1(row);
      case 4:
        return fillPtr2(row);
      default:
        return new Integer(0); // Error
      }
    }
  }

  // The default implementations of these methods in
  // AbstractTableModel would work, but we can refine them.
  public boolean isCellEditable(int row, int col) {
    return false;
  }

  public String getColumnName(int column) {
    return ColName[column];
  }

  private void initColumnNames() {
    ColName = new String[MAX_COLUMNS];

    ColName[0] = "Index";
    ColName[1] = "Tag Type";
    ColName[2] = "Tag Info";
    ColName[3] = "Pointer I";
    ColName[4] = "Pointer II";
  }

  // Private Methods

  private Object fillPtr1(int Index) {
    int Ptr = CpInfo.getPtr1(Index);
    if (Ptr == ConstantPool.PTR_INVALID) {
      return "PTR_INVALID";
    } else {
      return new Integer(Ptr);
    }
  }

  private Object fillPtr2(int Index) {
    int Ptr = CpInfo.getPtr2(Index);
    if (Ptr == ConstantPool.PTR_INVALID) {
      return "PTR_INVALID";
    } else {
      return new Integer(Ptr);
    }
  }

  private Object fillTagByte(int Index) {
    switch (CpInfo.getTagByte(Index)) {
    case ConstantPool.TAG_UTF8:
      return ("TAG_UTF8");
    case ConstantPool.TAG_INTEGER:
      return ("TAG_INTEGER");
    case ConstantPool.TAG_FLOAT:
      return ("TAG_FLOAT");
    case ConstantPool.TAG_LONG:
      return ("TAG_LONG");
    case ConstantPool.TAG_DOUBLE:
      return ("TAG_DOUBLE");
    case ConstantPool.TAG_CLASS:
      return ("TAG_CLASS");
    case ConstantPool.TAG_STRING:
      return ("TAG_STRING");
    case ConstantPool.TAG_FIELDREF:
      return ("TAG_FIELDREF");
    case ConstantPool.TAG_METHODREF:
      return ("TAG_METHOREF");
    case ConstantPool.TAG_INTERFACEREF:
      return ("TAG_INTERFACEREF");
    case ConstantPool.TAG_NAMETYPE:
      return ("TAG_NAMETYPE");
    case ConstantPool.TAG_NOTHING:
      return ("");
    default:
      return ("Invalid Tag");
    }
  }

  private Object fillValue(int Index) {
    return (CpInfo.getEntryValue(Index));
  }

}
