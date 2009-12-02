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
 ***/
package net.sf.jrevpro.parser.common;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.jrevpro.jls.JLSConstants;
import net.sf.jrevpro.jvm.JVMConstants;
import net.sf.jrevpro.parser.ClassParserException;
import net.sf.jrevpro.reflect.ClassInfo;
import net.sf.jrevpro.reflect.ConstantPool;
import net.sf.jrevpro.reflect.Field;
import net.sf.jrevpro.reflect.Method;

/**
 * @author Karthik Kumar
 */
public class AbstractClassFileParser {

  /**
   * Reads the ConstantPool.
   * 
   * @throws ClassParserException
   *           Thrown if class file not in desired format.
   * @throws IOException
   *           Thrown if error in stream of bytes containing the class file.
   */
  public static ConstantPool readConstantPool(DataInputStream dis)
      throws IOException, ClassParserException {
    int numCpEntry = dis.readShort();
    ConstantPool cpInfo = new ConstantPool(numCpEntry);
    readCpEntries(dis, cpInfo, numCpEntry);
    return cpInfo;
  }

  /**
   * Reads the access specifier of the class.
   * 
   * @throws IOException
   *           Thrown if error in stream of bytes containing the class file.
   */
  public static short readAccess(DataInputStream dis) throws IOException {
    return dis.readShort();
  }

  /**
   * 
   * @throws IOException
   *           Thrown if error in stream of bytes containing the class file.
   */
  public static short readThisClassIndex(DataInputStream dis)
      throws IOException {
    return dis.readShort();
  }

  /**
   * 
   * @throws IOException
   *           Thrown if error in stream of bytes containing the class file.
   */
  public static short readSuperClassIndex(DataInputStream dis)
      throws IOException {
    return dis.readShort();
  }

  /**
   * Reads the fully qualified name of the interfaces <code>implemented</code>
   * by the Current class.
   * 
   * @see JClassInfo#addInterface(String)
   * @throws IOException
   *           Thrown if error in stream of bytes containing the class file.
   */
  public static List<String> readInterfaces(DataInputStream dis,
      ConstantPool cpInfo) throws IOException {
    short count = dis.readShort();
    List<String> interfaces = new ArrayList<String>();
    for (int i = 0; i < count; i++) {
      String interfaceName = cpInfo.getClassName(dis.readShort());
      interfaces.add(interfaceName);
    }
    return interfaces;
  }

  /**
   * Reads the fields <code>defined</code> in the Current class.
   * 
   * @see JField
   * @throws IOException
   *           Thrown if error in stream of bytes containing the class file.
   */
  public static List<Field> readFields(DataInputStream dis, ConstantPool cpInfo)
      throws IOException {
    short count = dis.readShort();

    List<Field> fields = new ArrayList<Field>();
    for (int i = 0; i < count; i++) {
      Field curField = new Field();

      short accessFlags = dis.readShort();
      short nameIndex = dis.readShort();
      short descIndex = dis.readShort();

      String name = cpInfo.getUtf8String(nameIndex);
      String descriptor = cpInfo.getUtf8String(descIndex);

      curField.setName(name);
      curField.setDatatype(descriptor);
      curField.setQualifier(accessFlags);

      short attrCount = dis.readShort();
      for (int j = 0; j < attrCount; j++) {
        readFieldAttributes(curField, dis, cpInfo);
      }

      fields.add(curField);
    }
    return fields;
  }

  /**
   * Reads the methods <code>defined</code> in the Current class.
   * 
   * @see JMethod
   * @throws IOException
   *           Thrown if error in stream of bytes containing the class file.
   */
  public static List<Method> readMethods(DataInputStream dis,
      ConstantPool cpInfo) throws IOException {
    short count = dis.readShort();

    List<Method> methods = new ArrayList<Method>();

    for (int i = 0; i < count; i++) {
      Method curMethod = new Method();

      short accessFlags = dis.readShort();
      short nameIndex = dis.readShort();
      short descIndex = dis.readShort();

      String name = cpInfo.getUtf8String(nameIndex);
      String descriptor = cpInfo.getUtf8String(descIndex);

      curMethod.setName(name);
      curMethod.setSignature(descriptor);
      curMethod.setQualifier(accessFlags);

      short attrCount = dis.readShort();
      for (int j = 0; j < attrCount; j++) {
        readMethodAttributes(curMethod, dis, cpInfo);
      }
      if (curMethod.getVariableTable() == null) {
        // It was not decompiled with variableTable- Hence setting it
        // using symboltable
        curMethod.initializeSymbolTable();
      }

      methods.add(curMethod);
    }
    return methods;
  }

  /**
   * Reads the ATTRIBUTES of the fields and methods.
   * <p>
   * The possible attributes here are Code, LineNumberTable, Exception
   * </p>
   * .
   * 
   * @see AttributeParser
   * @throws IOException
   *           Thrown if error in stream of bytes containing the class file.
   */
  public static void readClassAttributes(DataInputStream dis,
      ConstantPool cpInfo, ClassInfo clazz) throws IOException {
    short attrCount = dis.readShort();

    for (int i = 0; i < attrCount; i++) {
      readClassAttribute(dis, cpInfo, clazz);
    }
  }

  /**
   * Reads the ConstantPool Entries.
   * 
   * @param aNumEntry
   *          Number of constant pool entries.
   * @throws ClassParserException
   *           Thrown if class file not in desired format.
   * @throws IOException
   *           Thrown if error in stream of bytes containing the class file.
   */
  private static void readCpEntries(DataInputStream dis, ConstantPool cpInfo,
      int aNumEntry) throws IOException, ClassParserException {
    cpInfo.addNullEntry();

    for (int i = 1; i < aNumEntry; i++) {
      byte tagByte = dis.readByte();

      switch (tagByte) {
      case ConstantPool.TAG_UTF8:
        readTagUtf8(dis, cpInfo, i);
        break;
      case ConstantPool.TAG_INTEGER:
        readTagInteger(dis, cpInfo, i);
        break;
      case ConstantPool.TAG_FLOAT:
        readTagFloat(dis, cpInfo, i);
        break;
      case ConstantPool.TAG_LONG:
        readTagLong(dis, cpInfo, i);
        // Long takes two ConstantPool Entries.
        i++;
        break;
      case ConstantPool.TAG_DOUBLE:
        readTagDouble(dis, cpInfo, i);
        // Double takes two ConstantPool Entries.
        i++;
        break;
      case ConstantPool.TAG_CLASS:
        readTagClass(dis, cpInfo, i);
        break;
      case ConstantPool.TAG_STRING:
        readTagString(dis, cpInfo, i);
        break;
      case ConstantPool.TAG_FIELDREF:
        readTagFieldRef(dis, cpInfo, i);
        break;
      case ConstantPool.TAG_METHODREF:
        readTagMethodRef(dis, cpInfo, i);
        break;
      case ConstantPool.TAG_INTERFACEREF:
        readTagInterfaceRef(dis, cpInfo, i);
        break;
      case ConstantPool.TAG_NAMETYPE:
        readTagNameType(dis, cpInfo, i);
        break;
      default:
        throw new ClassParserException("TagByte " + tagByte
            + " Invalid for ConstantPool Entry #" + i);
      }
    }
  }

  /**
   * Reads an UTF8 entry.
   * 
   * @param aIndex
   *          Index of a ConstantPool Entry.
   * @throws IOException
   *           Thrown if error in stream of bytes containing the class file.
   */
  private static void readTagUtf8(DataInputStream dis, ConstantPool cpInfo,
      int aIndex) throws IOException {
    String utfString = dis.readUTF();
    cpInfo.addUtf8Entry(utfString);
  }

  /**
   * Reads an integer entry.
   * 
   * @param aIndex
   *          Index of a ConstantPool Entry.
   * @throws IOException
   *           Thrown if error in stream of bytes containing the class file.
   */
  private static void readTagInteger(DataInputStream dis, ConstantPool cpInfo,
      int aIndex) throws IOException {
    int intValue = dis.readInt();

    cpInfo.addIntegerEntry(String.valueOf(intValue));
  }

  /**
   * Reads an float entry.
   * 
   * @param aIndex
   *          Index of a ConstantPool Entry.
   * @throws IOException
   *           Thrown if error in stream of bytes containing the class file.
   */
  private static void readTagFloat(DataInputStream dis, ConstantPool cpInfo,
      int aIndex) throws IOException {
    float floatValue = dis.readFloat();

    cpInfo.addFloatEntry(String.valueOf(floatValue)
        + JLSConstants.JLS_FLOATING_NUMBER_SUFFIX);
  }

  /**
   * Reads a long entry.
   * 
   * @param aIndex
   *          Index of a ConstantPool Entry.
   * @throws IOException
   *           Thrown if error in stream of bytes containing the class file.
   */
  private static void readTagLong(DataInputStream dis, ConstantPool cpInfo,
      int aIndex) throws IOException {

    long longValue = dis.readLong();

    cpInfo.addLongEntry(String.valueOf(longValue)
        + JLSConstants.JLS_LONG_NUMBER_SUFFIX);
    cpInfo.addNullEntry();
  }

  /**
   * Reads a double entry.
   * 
   * @param aIndex
   *          Index of a ConstantPool Entry.
   * @throws IOException
   *           Thrown if error in stream of bytes containing the class file.
   */
  private static void readTagDouble(DataInputStream dis, ConstantPool cpInfo,
      int aIndex) throws IOException {

    double doubleValue = dis.readDouble();

    cpInfo.addDoubleEntry(String.valueOf(doubleValue));
    cpInfo.addNullEntry();
  }

  /**
   * Reads an TAG_CLASS entry.
   * <p>
   * u1 tag; <br>
   * u2 name_index;<br>
   * </p>
   * Reads a class entry.
   * 
   * @param aIndex
   *          Index of a ConstantPool Entry.
   * @throws IOException
   *           Thrown if error in stream of bytes containing the class file.
   */
  private static void readTagClass(DataInputStream dis, ConstantPool cpInfo,
      int aIndex) throws IOException {
    int classIndex = dis.readShort();

    cpInfo.addClassEntry(classIndex);
  }

  /**
   * Reads a string entry.
   * 
   * @param aIndex
   *          Index of a ConstantPool Entry.
   * @throws IOException
   *           Thrown if error in stream of bytes containing the class file.
   */
  private static void readTagString(DataInputStream dis, ConstantPool cpInfo,
      int aIndex) throws IOException {
    int stringIndex = dis.readShort();
    cpInfo.addStringEntry(stringIndex);
  }

  /**
   * Reads a TAG_FIELDREF entry.
   * <p>
   * u1 tag; <br>
   * u2 class_index; <br>
   * u2 name_and_type_index; <br>
   * </p>
   * 
   * @param aIndex
   *          Index of a ConstantPool Entry.
   * @throws IOException
   *           Thrown if error in stream of bytes containing the class file.
   */
  private static void readTagFieldRef(DataInputStream dis, ConstantPool cpInfo,
      int aIndex) throws IOException {
    int classIndex = dis.readShort();
    int nameType = dis.readShort();

    cpInfo.addFieldRefEntry(classIndex, nameType);
  }

  /**
   * Reads a TAG_METHODREF entry.
   * <p>
   * u1 tag; <br>
   * u2 class_index; <br>
   * u2 name_and_type_index; <br>
   * </p>
   * 
   * @param aIndex
   *          Index of a ConstantPool Entry.
   * @throws IOException
   *           Thrown if error in stream of bytes containing the class file.
   */
  private static void readTagMethodRef(DataInputStream dis,
      ConstantPool cpInfo, int aIndex) throws IOException {
    int classIndex = dis.readShort();
    int nameType = dis.readShort();

    cpInfo.addMethodRefEntry(classIndex, nameType);
  }

  /**
   * Reads a TAG_INTERFACEREF entry.
   * <p>
   * u1 tag;<br>
   * u2 class_index;<br>
   * u2 name_and_type_index;<br>
   * </p>
   * 
   * @param aIndex
   *          Index of a ConstantPool Entry.
   * @throws IOException
   *           Thrown if error in stream of bytes containing the class file.
   */
  private static void readTagInterfaceRef(DataInputStream dis,
      ConstantPool cpInfo, int aIndex) throws IOException {
    int classIndex = dis.readShort();
    int nameType = dis.readShort();

    cpInfo.addInterfaceRefEntry(classIndex, nameType);
  }

  /**
   * Reads a TAG_NAMETYPE entry.
   * <p>
   * u1 tag; <br>
   * u2 name_index;<br>
   * u2 descriptor_index;<br>
   * </p>
   * 
   * @param aIndex
   *          Index of a ConstantPool Entry.
   * @throws IOException
   *           Thrown if error in stream of bytes containing the class file.
   */
  private static void readTagNameType(DataInputStream dis, ConstantPool cpInfo,
      int aIndex) throws IOException {
    int nameIndex = dis.readShort();
    int descIndex = dis.readShort();

    cpInfo.addNameTypeEntry(nameIndex, descIndex);
  }

  /**
   * Reads the ATTRIBUTES of the field defined in the Current class.
   * <p>
   * The possible attributes here are ConstantValue , Deprecated , Synthetic.
   * </p>
   * .
   * 
   * @param aRhsField
   *          Reads attributes into this field.
   * @see AttributeParser
   * @throws IOException
   *           Thrown if error in stream of bytes containing the class file.
   */
  private static void readFieldAttributes(Field aRhsField, DataInputStream dis,
      ConstantPool cpInfo) throws IOException {

    String attrName = cpInfo.getUtf8String(dis.readShort());

    if (attrName.equals(JVMConstants.ATTRIBUTE_CONSTANT_VALUE)) {
      aRhsField.setValue(AttributeParser.readConstantValue(dis, cpInfo));
    } else if (attrName.equals(JVMConstants.ATTRIBUTE_SYNTHETIC)) {
      AttributeParser.readSynthetic(dis);
    } else if (attrName.equals(JVMConstants.ATTRIBUTE_DEPRECATED)) {
      AttributeParser.readDeprecated(dis);
    }
  }

  /**
   * Reads the ATTRIBUTES of the method defined in the Current class.
   * <p>
   * The possible attributes here are Deprecated , Synthetic , <b> Code </b> and
   * Exceptions
   * </p>
   * .
   * 
   * @param aRhsMethod
   *          Reads attributes into this method.
   * @see AttributeParser
   * @throws IOException
   *           Thrown if error in stream of bytes containing the class file.
   */
  private static void readMethodAttributes(Method method, DataInputStream dis,
      ConstantPool cpInfo) throws IOException {
    String attrName = cpInfo.getUtf8String(dis.readShort());

    if (attrName.compareTo(JVMConstants.ATTRIBUTE_CODE) == 0) {
      AttributeParser.readCode(method, dis, cpInfo);
    } else if (attrName.compareTo(JVMConstants.ATTRIBUTE_EXCEPTIONS) == 0) {
      // counterpart to 'throws' clause in the source code.
      method.setThrowsClasses(AttributeParser.readExceptions(dis, cpInfo));
    } else if (attrName.compareTo(JVMConstants.ATTRIBUTE_SYNTHETIC) == 0) {
      AttributeParser.readSynthetic(dis);
    } else if (attrName.compareTo(JVMConstants.ATTRIBUTE_DEPRECATED) == 0) {
      AttributeParser.readDeprecated(dis);
    } else if (attrName.compareTo(JVMConstants.ATTRIBUTE_INNERCLASSES) == 0) {
      // TODO Should discuss with akkumar if he has implemented it
      // already in some other code location.

    }
  }

  /**
   * Reads the ATTRIBUTES of the whole class itself.
   * <p>
   * The possible attributes here are Deprecated , SourceFile
   * </p>
   * .
   * 
   * @see AttributeParser
   * @throws IOException
   *           Thrown if error in stream of bytes containing the class file.
   */
  private static void readClassAttribute(DataInputStream dis,
      ConstantPool cpInfo, ClassInfo clazz) throws IOException {
    int attrIndex = dis.readShort();
    String attrName = cpInfo.getUtf8String(attrIndex);

    if (attrName.compareTo(JVMConstants.ATTRIBUTE_SOURCEFILE) == 0) {
      clazz.setSourceFile(AttributeParser.readSourceFile(dis, cpInfo));
    } else if (attrName.compareTo(JVMConstants.ATTRIBUTE_DEPRECATED) == 0) {
      AttributeParser.readDeprecated(dis);
    } else {
      // System.out.println("Attribute " + AttrName );
    }
  }

}
