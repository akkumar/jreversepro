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
package org.jreversepro.reflect;

import java.util.ArrayList;
import java.util.List;

import org.jreversepro.jls.JLSConstants;
import org.jreversepro.jls.JLSStringEncoder;
import org.jreversepro.jvm.JVMConstants;
import org.jreversepro.jvm.TypeInferrer;


/**
 * <b>JConstantPool</b> represents the abstraction of the ConstantPool.
 * 
 * @author Karthik Kumar
 */
public class ConstantPool {

  /**
   * Constructor.
   * 
   * @param cpMax
   *          Maximum size of the ConstantPool.
   */
  public ConstantPool(int cpMax) {
    listEntries = new ArrayList<ConstantPoolEntry>(cpMax);
    importedClasses = null;
  }

  /**
   * Returns the number of ConstantPool Entries.
   * 
   * @return Returns the cp entry count.
   */
  public int getMaxCpEntry() {
    return listEntries.size();
  }

  /**
   * Returns the constantpool entries. The individual elements are
   * JConstantPoolEntry.
   * 
   * @return Returns list of constantpool entries.
   */
  public List<ConstantPoolEntry> getEntries() {
    return listEntries;
  }

  /**
   * Adds a new TAG_FIELDREF entry to the constantpool.
   * 
   * @param ptr1
   *          Pointer to TAG_CLASS
   * @param ptr2
   *          Pointer to TAG_NAMETYPE
   */
  public void addFieldRefEntry(int ptr1, int ptr2) {
    listEntries
        .add(new ConstantPoolEntry(TAG_FIELDREF, STR_INVALID, ptr1, ptr2));
  }

  /**
   * Adds a new TAG_METHODREF entry to the constantpool.
   * 
   * @param ptr1
   *          Pointer to TAG_CLASS
   * @param ptr2
   *          Pointer to TAG_NAMETYPE
   */
  public void addMethodRefEntry(int ptr1, int ptr2) {
    listEntries.add(new ConstantPoolEntry(TAG_METHODREF, STR_INVALID, ptr1,
        ptr2));
  }

  /**
   * Adds a new TAG_INTERFACEREF entry to the constantpool.
   * 
   * @param ptr1
   *          Pointer to TAG_CLASS
   * @param ptr2
   *          Pointer to TAG_NAMETYPE
   */
  public void addInterfaceRefEntry(int ptr1, int ptr2) {
    listEntries.add(new ConstantPoolEntry(TAG_INTERFACEREF, STR_INVALID, ptr1,
        ptr2));
  }

  /**
   * Adds a new TAG_NAMETYPE entry to the constantpool.
   * 
   * @param ptr1
   *          Pointer to TAG_UTF8
   * @param ptr2
   *          Pointer to TAG_UTF8
   */
  public void addNameTypeEntry(int ptr1, int ptr2) {
    listEntries
        .add(new ConstantPoolEntry(TAG_NAMETYPE, STR_INVALID, ptr1, ptr2));
  }

  /**
   * Adds a NULL entry to the ConstantPool. Mainly useful when we add long/
   * double.
   */
  public void addNullEntry() {
    listEntries.add(new ConstantPoolEntry(TAG_NOTHING, STR_INVALID,
        PTR_INVALID, PTR_INVALID));
  }

  /**
   * Adds a new TAG_UTF8 entry to the constantpool.
   * 
   * @param value
   *          Value of the UTF8 String.
   */
  public void addUtf8Entry(String value) {
    listEntries.add(new ConstantPoolEntry(TAG_UTF8, value, PTR_INVALID,
        PTR_INVALID));
  }

  /**
   * Adds a new TAG_INTEGER entry to the constantpool.
   * 
   * @param value
   *          Value of the integer.
   */
  public void addIntegerEntry(String value) {
    listEntries.add(new ConstantPoolEntry(TAG_INTEGER, value, PTR_INVALID,
        PTR_INVALID));
  }

  /**
   * Adds a new TAG_FLOAT entry to the constantpool.
   * 
   * @param value
   *          Value of the float number.
   */
  public void addFloatEntry(String value) {
    listEntries.add(new ConstantPoolEntry(TAG_FLOAT, value, PTR_INVALID,
        PTR_INVALID));
  }

  /**
   * Adds a new TAG_DOUBLE entry to the constantpool.
   * 
   * @param value
   *          Value of the double.
   */
  public void addDoubleEntry(String value) {
    listEntries.add(new ConstantPoolEntry(TAG_DOUBLE, value, PTR_INVALID,
        PTR_INVALID));
  }

  /**
   * Adds a new TAG_LONG entry to the constantpool.
   * 
   * @param value
   *          Value of the Long.
   */
  public void addLongEntry(String value) {
    listEntries.add(new ConstantPoolEntry(TAG_LONG, value, PTR_INVALID,
        PTR_INVALID));
  }

  /**
   * Adds a new TAG_CLASS entry to the constantpool.
   * 
   * @param classIndex
   *          Index to UTF8 string containing class name.
   */
  public void addClassEntry(int classIndex) {
    listEntries.add(new ConstantPoolEntry(TAG_CLASS, STR_INVALID, classIndex,
        PTR_INVALID));
  }

  /**
   * Adds a new TAG_STRING entry to the constantpool.
   * 
   * @param stringIndex
   *          Index to the UTF8 string containing the stringvalue.
   */
  public void addStringEntry(int stringIndex) {
    listEntries.add(new ConstantPoolEntry(TAG_STRING, STR_INVALID, stringIndex,
        PTR_INVALID));
  }

  /**
   * Returns the data type of the given ConstantPool Index.
   * 
   * @param index
   *          Index to the ConstantPool Entry.
   * @return long/double/String/integer appropriately
   */
  public String getDataType(int index) {
    switch (getTagByte(index)) {
    case TAG_LONG:
      return JLSConstants.JLS_TYPE_LONG;
    case TAG_DOUBLE:
      return JLSConstants.JLS_TYPE_DOUBLE;
    case TAG_STRING:
      return JVMConstants.CLASS_LANG_STRING;
    case TAG_INTEGER:
      return JLSConstants.JLS_TYPE_INT;
    default:
      return JLSConstants.NULL;
    }
  }

  /**
   * Returns if the given index to the ConstantPool is TAG_DOUBLE or not.
   * 
   * @param index
   *          Index to ConstantPool
   * @return Returns true, if it is a TAG_DOUBLE. false, otherwise.
   */
  public boolean isTagDouble(int index) {
    return getTagByte(index) == TAG_DOUBLE;
  }

  /**
   * Returns the first pointer of the ConstantPool Data.
   * 
   * @param index
   *          Index to ConstantPool
   * @return Returns the integer.
   */
  public int getPtr1(int index) {
    return listEntries.get(index).getPtr1();
  }

  /**
   * Returns the second pointer of the ConstantPool Data.
   * 
   * @param index
   *          Index to ConstantPool
   * @return Returns the integer.
   */
  public int getPtr2(int index) {
    return listEntries.get(index).getPtr2();
  }

  /**
   * Returns the tagbyte of the ConstantPool.
   * 
   * @param index
   *          Index to ConstantPool
   * @return Returns the tag byte.
   */
  public int getTagByte(int index) {
    return listEntries.get(index).getTagByte();
  }

  /**
   * Returns the ConstantPool value.
   * 
   * @param index
   *          Index to ConstantPool
   * @return Returns the value of that cp entry.
   */
  public String getEntryValue(int index) {
    return listEntries.get(index).getValue();
  }

  /**
   * Returns the Utf8 value pointed by the first pointer of the index to the
   * ConstantPool. Say for example, if entry #6 has ptr1 to be #8 and the utf8
   * value of #8 is "MyString", this method on given input 6 returns "MyString".
   * 
   * @param index
   *          Index to ConstantPool
   * @return Returns a String a Utf8 value.
   */
  public String getFirstDirectName(int index) {
    int ptr1 = listEntries.get(index).getPtr1();
    return listEntries.get(ptr1).getValue();
  }

  /**
   * Returns the Utf8 value pointed by the II pointer of the index to the
   * ConstantPool. Say for example, if entry #6 has ptr2 to be #8 and the utf8
   * value of #8 is "MyString", this method on given input 6 returns "MyString".
   * 
   * @param index
   *          Index to ConstantPool
   * @return Returns a String a Utf8 value.
   */
  public String getSecondDirectName(int index) {
    int ptr2 = listEntries.get(index).getPtr2();
    return listEntries.get(ptr2).getValue();
  }

  /**
   * Given an index to TAG_CLASS this returns the class name pointed to by it.
   * If the index is 0 then a class of type ANY is returned.
   * 
   * @param index
   *          Index to ConstantPool
   * @return Returns a String - class name
   */
  public String getClassName(int index) {
    return (index == 0) ? JVMConstants.ANY : getFirstDirectName(index);
  }

  /**
   * Given an index to TAG_NAMETYPE this returns the name of the member ( field
   * / method )
   * 
   * @param index
   *          Index to ConstantPool
   * @return Returns name of the member
   */
  public String getFieldName(int index) {
    return getFirstDirectName(index);
  }

  /**
   * Given an index to TAG_NAMETYPE this returns the type of the member ( field
   * / method )
   * 
   * @param index
   *          Index to ConstantPool
   * @return Returns type of the member
   */
  public String getFieldType(int index) {
    return getSecondDirectName(index);
  }

  /**
   * Given an index to TAG_UTF8 this returns the string value.
   * 
   * @param index
   *          Index to ConstantPool
   * @return Returns value of that Cp Entry.
   */
  public String getUtf8String(int index) {
    return getEntryValue(index);
  }

  /**
   * Given an Cp index this returns the proper constant pool value depending on
   * the tag type. If TAG_INTEGER, returned as such. Else if TAG_STRING all the
   * escape characters are properly quoted and returned.
   * 
   * @param index
   *          Index to ConstantPool
   * @return Returns value of that Cp Entry.
   */
  public String getLdcString(int index) {
    String result = STR_INVALID;
    int tagByte = getTagByte(index);

    if (tagByte == TAG_STRING) {
      String strVal = getFirstDirectName(index);
      strVal = JLSStringEncoder.encodeStringInJLSSource(strVal);
      result = "\"" + strVal + "\"";
    } else if (tagByte == TAG_INTEGER) {
      result = getEntryValue(index);
    }
    return result;
  }

  /**
   * The constantpool is the one and only source that contains the references to
   * external types. Hence all the entries are analyzed for all external types
   * referred to by this class and are consolidated into a class called Import.
   * This Import can further be used to get the short form of classname etc.
   * 
   * @return Reference to Import.
   */
  public Import getImportedClasses() {
    if (importedClasses != null) {
      return importedClasses;
    } else {
      importedClasses = new Import();
      for (int i = 0; i < listEntries.size(); i++) {
        ConstantPoolEntry ent = listEntries.get(i);
        switch (ent.getTagByte()) {
        case TAG_CLASS:
          importedClasses.addClass(TypeInferrer.getJLSType(
              getFirstDirectName(i), true));
          break;

        case TAG_FIELDREF:
          String type = getType(ent);
          importedClasses.addClass(TypeInferrer.getJLSType(type, true));
          break;

        case TAG_METHODREF:
        case TAG_INTERFACEREF:
          String methodType = getType(ent);
          List<String> args = TypeInferrer.getArguments(methodType);
          for (String str : args) {
            importedClasses.addClass(TypeInferrer.getJLSType(str, true));
          }
          importedClasses.addClass(TypeInferrer.getJLSType(TypeInferrer
              .getReturnType(methodType), true));
          break;
        }
      }
      return importedClasses;
    }
  }

  /**
   * Returns the value for the ConstantPool Entries according to their types.
   * 
   * @param index
   *          Index to ConstantPool
   * @return Returns value of that Cp Entry.
   */
  public String getBasicDataTypeValue(int index) {
    int tagByte = getTagByte(index);
    switch (tagByte) {
    case TAG_LONG:
    case TAG_FLOAT:
    case TAG_DOUBLE:
    case TAG_INTEGER:
      return getEntryValue(index);
    case TAG_STRING:
      StringBuilder sb = new StringBuilder("\"");
      sb.append(JLSStringEncoder
          .encodeStringInJLSSource(getFirstDirectName(index))
          + "\"");
      return sb.toString();
    default:
      return STR_INVALID;
    }
  }

  /**
   * Returns the actual name of the tag . according to their types.
   * 
   * @param tagByte
   *          Tag Byte value
   * @return Returns the string representation of the integer tagByte
   */
  public static String getTagName(int tagByte) {
    switch (tagByte) {
    case TAG_UTF8:
      return ("TAG_UTF8");

    case TAG_INTEGER:
      return ("TAG_INTEGER");

    case TAG_FLOAT:
      return ("TAG_FLOAT");

    case TAG_LONG:
      return ("TAG_LONG");

    case TAG_DOUBLE:
      return ("TAG_DOUBLE");

    case TAG_CLASS:
      return ("TAG_CLASS");

    case TAG_STRING:
      return ("TAG_STRING");

    case TAG_FIELDREF:
      return ("TAG_FIELDREF");

    case TAG_METHODREF:
      return ("TAG_METHODREF");

    case TAG_INTERFACEREF:
      return ("TAG_INTERFACEREF");

    case TAG_NAMETYPE:
      return ("TAG_NAMETYPE");

    case TAG_NOTHING:
      return ("TAG_NOTHING");

    default:
      return ("Invalid Tag");
    }
  }

  /**
   * Describes the tag in brief. If a tag is relative tag like TAG_FIELDREF or
   * TAG_METHODREF then it lists out the related tag information too,
   * 
   * @param index
   *          Index to the ConstantPool.
   * @return Returns a string describing the tag entry.
   */
  public String getTagDescriptor(int index) {
    StringBuilder result = new StringBuilder("");
    int tagByte = getTagByte(index);
    switch (tagByte) {
    case TAG_METHODREF:
    case TAG_FIELDREF:
    case TAG_INTERFACEREF:
      ConstantPoolEntry ent = listEntries.get(index);
      result.append(getType(ent) + "," + getName(ent));
      break;
    case TAG_STRING:
      result.append(getLdcString(index));
      break;
    case TAG_CLASS:
      result.append(getClassName(index));
      break;
    }
    return result.toString();
  }

  /**
   * Returns the name pointed to by this JConstantPoolEntry. Usually this tag
   * happens to be one of TAG_FIELDREF, TAG_METHODREF or TAG_INTERFACEREF.
   * 
   * @param ent
   *          Constant Pool Entry
   * @return Returns name of the member (field/method/interface) pointed to by
   *         the Constant Pool Entry.
   */
  public String getName(ConstantPoolEntry ent) {
    ConstantPoolEntry entNameType = listEntries.get(ent
        .getPtr2());
    ConstantPoolEntry entName = listEntries.get(entNameType
        .getPtr1());
    return entName.getValue();
  }

  /**
   * Returns the type pointed to by this JConstantPoolEntry. Usually this tag
   * happens to be one of TAG_FIELDREF, TAG_METHODREF or TAG_INTERFACEREF.
   * 
   * @param ent
   *          Constant Pool Entry
   * @return Returns type of the member (field/method/interface) pointed to by
   *         the Constant Pool Entry.
   */
  public String getType(ConstantPoolEntry ent) {
    ConstantPoolEntry entNameType = listEntries.get(ent
        .getPtr2());
    ConstantPoolEntry entType = listEntries.get(entNameType
        .getPtr2());
    return entType.getValue();
  }

  /**
   * Returns the whole ConstantPool info in a formatter manner.
   * 
   * @return Returns a string.
   */
  public String getEntryInfo() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < listEntries.size(); i++) {
      sb.append("\n" + i + " : " + listEntries.get(i));
    }
    return sb.toString();
  }

  /**
   * Returns the name pointed to by this JConstantPoolEntry. Usually this tag
   * happens to be one of TAG_FIELDREF, TAG_METHODREF or TAG_INTERFACEREF.
   * 
   * @param cpIndex
   *          Index to Constant Pool
   * @return Returns name of the member (field/method/interface) pointed to by
   *         the Constant Pool Entry.
   */
  public String getEntryInfo(int cpIndex) {
    StringBuilder sb = new StringBuilder();
    getSingleEntryInfo(sb, cpIndex);
    return sb.toString();
  }

  /**
   * Describes the tag in brief. If a tag is relative tag like TAG_FIELDREF or
   * TAG_METHODREF then it continues to report the dependent tag information
   * until it reaches a tag which is not dependent on anything else. Usually the
   * final tag happens to a TAG_UTF8.
   * 
   * @param sb
   *          StringBuilder containing the info of tags dependent on this
   *          already ( cpIndex ).
   * @param cpIndex
   *          Index to ConstantPool.
   */
  private void getSingleEntryInfo(StringBuilder sb, int cpIndex) {
    if (cpIndex >= 1 && cpIndex < listEntries.size()) {
      ConstantPoolEntry ent = listEntries.get(cpIndex);
      sb.append("\n" + cpIndex + " : " + ent);
      getSingleEntryInfo(sb, ent.getPtr1());
      getSingleEntryInfo(sb, ent.getPtr2());
    }
  }

  /**
   * STR_INVALID corresponds to an invalid entry in the ConstantPool.
   */
  public static final String STR_INVALID = "Invalid String";

  /**
   * PTR_INVALID of a pointer of a ConstantPool tag means that they are not
   * applicable for that ConstantPool tag.
   */
  public static final int PTR_INVALID = -1;

  /**
   * TAG_NOTHING means that the ConstantPool Entry is invalid.
   */
  public static final int TAG_NOTHING = -1;

  /**
   * TAG_UTF8 corresponds to CONSTANT_UTF8
   */
  public static final int TAG_UTF8 = 1;

  /**
   * TAG_INTEGER corresponds to CONSTANT_INTEGER
   */
  public static final int TAG_INTEGER = 3;

  /**
   * TAG_FLOAT corresponds to CONSTANT_FLOAT
   */
  public static final int TAG_FLOAT = 4;

  /**
   * TAG_LONG corresponds to CONSTANT_LONG
   */
  public static final int TAG_LONG = 5;

  /**
   * TAG_DOUBLE corresponds to CONSTANT_DOUBLE
   */
  public static final int TAG_DOUBLE = 6;

  /**
   * TAG_CLASS corresponds to CONSTANT_CLASS
   */
  public static final int TAG_CLASS = 7;

  /**
   * TAG_STRING corresponds to CONSTANT_STRING
   */
  public static final int TAG_STRING = 8;

  /**
   * TAG_FIELDREF corresponds to CONSTANT_FIELDREF
   */
  public static final int TAG_FIELDREF = 9;

  /**
   * TAG_METHODREF corresponds to CONSTANT_METHODREF
   */
  public static final int TAG_METHODREF = 10;

  /**
   * TAG_INTERFACEREF corresponds to CONSTANT_INTERFACEREF
   */
  public static final int TAG_INTERFACEREF = 11;

  /**
   * TAG_NAMETYPE corresponds to CONSTANT_NAMETYPE
   */
  public static final int TAG_NAMETYPE = 12;

  /**
   * listEntries contains the ConstantPool Entries. The individual elements of
   * the list are JConstantPoolEntry.
   */
  private final List<ConstantPoolEntry> listEntries;

  /**
   * Reference to importedClasses that contains the list of imported classes.
   */
  private Import importedClasses;

}
