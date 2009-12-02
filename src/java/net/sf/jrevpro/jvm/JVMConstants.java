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
package net.sf.jrevpro.jvm;

/**
 * Interface representing the types natively supported by the JVM.
 * 
 * @author akkumar
 * 
 *         Modification by Karthikeyan C (bercolax) - Added InnerClasses
 *         Attribute Constant
 * 
 */
public interface JVMConstants {

  /**
   * datatype is a reference to an object
   */
  char JVM_TYPE_REFERENCE = 'A';

  char JVM_TYPE_BYTE = 'B';

  char JVM_TYPE_CHAR = 'C';

  char JVM_TYPE_DOUBLE = 'D';

  char JVM_TYPE_FLOAT = 'F';

  char JVM_TYPE_INT = 'I';

  char JVM_TYPE_LONG = 'J';

  char JVM_TYPE_SHORT = 'S';

  char JVM_TYPE_VOID = 'V';

  char JVM_TYPE_BOOLEAN = 'Z';

  char JVM_TYPE_ARRAY = '[';

  char JVM_TYPE_CLASS = 'L';

  char JVM_TYPE_DELIMITER = ';';

  char JVM_TYPE_UNDEFINED = 'U';

  char JVM_METHOD_ARG_BEGIN_DELIMITER = '(';

  char JVM_METHOD_ARG_END_DELIMITER = ')';

  String JVM_BOOLEAN_TRUE = "1"; // representation of true in JVM

  String JVM_BOOLEAN_FALSE = "0"; // representation of false in JVM

  // Constants containing KeyWords
  String FOREIGN_CLASS = "<ForeignClass>";
  String FOREIGN_OBJ = "<ForeignObject>";

  /**
   * JVM Representation of java.lang.String
   */
  String CLASS_LANG_STRING = "java/lang/String";

  /**
   * datatype is of type returnaddress
   */
  String RET_ADDR = "returnaddress";

  /**
   * Refers to the name of the current class type.
   */
  String THISCLASS = "**this_class**";

  /**
   * JVM representation of the method static {.. }
   */
  String CLINIT = "<clinit>";

  /**
   * JVM representation of the constructor method.
   */
  String INIT = "<init>";

  /**
   * JVM representation of the class java.lang.Object
   */
  String CLASS_LANG_OBJECT = "java/lang/Object";

  /**
   * Exception Class of type 'any'.
   */
  String ANY = "<any>";

  int MAGIC = 0xCAFEBABE;

  /**
   * ACC_SUPER bit required to be set on all modern classes.
   */
  int ACC_SUPER = 0x0020;

  /**
   * ACC_INTERFACE bit required to be set if it is an interface and not a class.
   */
  int ACC_INTERFACE = 0x0200;

  /**
   * ConstantValue attribute of a Method.
   */
  String ATTRIBUTE_CONSTANT_VALUE = "ConstantValue";

  /**
   * Deprecated attribute of a Method.
   */
  String ATTRIBUTE_DEPRECATED = "Deprecated";

  /**
   * Synthetic attribute of a Method.
   */
  String ATTRIBUTE_SYNTHETIC = "Synthetic";

  /**
   * Code attribute of a Method.
   */
  String ATTRIBUTE_CODE = "Code";

  /**
   * Exceptions attribute of a Method.
   */
  String ATTRIBUTE_EXCEPTIONS = "Exceptions";

  /**
   * InnerClasses attribute of a Method.
   */
  String ATTRIBUTE_INNERCLASSES = "InnerClasses";

  /**
   * LineNumberTable attribute of a Method.
   */
  String ATTRIBUTE_LINENUMBERTABLE = "LineNumberTable";

  /**
   * LocalVariableTable attribute of a Method.
   */
  String ATTRIBUTE_LOCALVARIABLETABLE = "LocalVariableTable";

  /**
   * SourceFile attribute of a Method.
   */
  String ATTRIBUTE_SOURCEFILE = "SourceFile";

  char JVM_PACKAGE_DELIMITER = '/';

  /**
   * Public access specifier.
   */
  int ACC_PUBLIC = 0x0001;

  /**
   * Private access specifier.
   */
  int ACC_PRIVATE = 0x0002;

  /**
   * Protected access specifier.
   */
  int ACC_PROTECTED = 0x0004;

  /**
   * Qualifer 'static'
   */
  int ACC_STATIC = 0x0008;

  /**
   * Qualifier 'final'
   */
  int ACC_FINAL = 0x0010;

  /**
   * Qualifer 'synchronized'
   */
  int ACC_SYNCHRONIZED = 0x0020;

  /**
   * Qualifer 'native'
   */
  int ACC_NATIVE = 0x0100;

  /**
   * Qualifer 'abstract'
   */
  int ACC_ABSTRACT = 0x0400;

  /**
   * Qualifer 'strictfp'
   */
  int ACC_STRICT = 0x0800;

  /**
   * Qualifer 'volatile'. This qualifer is valid for fields only.
   */
  int ACC_VOLATILE = 0x0040;

  /**
   * Qualifer 'transient' This qualifer is valid for fields only.
   */
  int ACC_TRANSIENT = 0x0080;

}
