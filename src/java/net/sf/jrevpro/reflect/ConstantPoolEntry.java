/**
 * @(#)ConstantPoolEntry.java
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
package net.sf.jrevpro.reflect;

/**
 * ConstantPoolEntry is an Entry of the CONSTANT POOL data structure the class
 * file.
 * 
 * @author Karthik Kumar.
 **/
public class ConstantPoolEntry {

  /**
   * Constructor
   * 
   * @param tagByte
   *          Tag Byte
   * @param value
   *          Value
   * @param ptr1
   *          Pointer 1
   * @param ptr2
   *          Pointer 2
   **/
  public ConstantPoolEntry(int tagByte, String value, int ptr1, int ptr2) {
    this.tagByte = tagByte;
    this.value = value;
    this.ptr1 = ptr1;
    this.ptr2 = ptr2;
  }

  /**
   * @return Returns Pointer 1.
   **/
  public int getPtr1() {
    return ptr1;
  }

  /**
   * @return Returns Pointer 2.
   **/
  public int getPtr2() {
    return ptr2;
  }

  /**
   * @return Returns value of this ConstantPoolEntry.
   **/
  public String getValue() {
    return value;
  }

  /**
   * @return Returns Tag Byte
   **/
  public int getTagByte() {
    return tagByte;
  }

  /**
   * @return Returns stringified form of this class.
   **/
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(ConstantPool.getTagName(tagByte));
    sb.append(",");
    if (ptr1 == INVALID_CONSTANT_POOL_POINTER) {
      sb.append(" - ");
    } else {
      sb.append(ptr1);
    }
    sb.append(",");
    if (ptr2 == INVALID_CONSTANT_POOL_POINTER) {
      sb.append(" - ");
    } else {
      sb.append(ptr2);
    }
    sb.append(",");
    if (tagByte == ConstantPool.TAG_UTF8) {
      sb.append(value);
    } else {
      sb.append(" - ");
    }
    return sb.toString();
  }

  public static final int INVALID_CONSTANT_POOL_POINTER = -1;

  /**
   * ptr1 is the first pointer from this ConstantPoolEntry. An example could be
   * TAG_CLASS. TagClass would have an entry pointing to a TAG_UTF8 that
   * contains the class name. In that case the ptr1 of TAG_CLASS entry would
   * give lead to TAG_UTF8.
   **/
  int ptr1;

  /**
   * ptr2 is the second pointer from this ConstantPoolEntry. An example could be
   * TAG_FIELDTYPE. TagFieldType will have two entries - the first one to
   * TAG_CLASS and the second one to TAG_NAMETYPE.In this case ptr2 would have
   * the number of TAG_NAMETYPE index.
   **/
  int ptr2;

  /**
   * This is applicable to TAG_UTF8 TAG_INTEGER TAG_FLOAT TAG_DOUBLE TAG_LONG
   * that contains the actual value of the tag.
   **/
  String value;

  /**
   * Tag Byte tells us about what tag it is. It can be one of the following.
   * TAG_UTF8 corresponds to CONSTANT_UTF8 TAG_INTEGER corresponds to
   * CONSTANT_INTEGER TAG_FLOAT corresponds to CONSTANT_FLOAT TAG_LONG
   * corresponds to CONSTANT_LONG TAG_DOUBLE corresponds to CONSTANT_DOUBLE
   * TAG_CLASS corresponds to CONSTANT_CLASS TAG_STRING corresponds to
   * CONSTANT_STRING TAG_FIELDREF corresponds to CONSTANT_FIELDREF TAG_METHODREF
   * corresponds to CONSTANT_METHODREF TAG_INTERFACEREF corresponds to
   * CONSTANT_INTERFACEREF TAG_NAMETYPE corresponds to CONSTANT_NAMETYPE
   **/
  int tagByte;
}