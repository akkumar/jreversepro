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

import java.util.ArrayList;
import java.util.List;

import net.sf.jrevpro.jls.JLSConstants;
import net.sf.jrevpro.jls.JLSLanguageContext;

public class TypeInferrer implements JVMConstants {

  /**
   * Determines the Java representation , given the JVM representation of data
   * types.
   * <p>
   * <table>
   * <tr>
   * <th><code>dataType</code></th>
   * <th><code>getJLSType(dataType)</code></th>
   * </tr>
   * <tr>
   * <td><code>B</code></td>
   * <td>byte</td>
   * </tr>
   * <tr>
   * <td><code>C</code></td>
   * <td>char</td>
   * </tr>
   * <tr>
   * <td><code>D</code></td>
   * <td>double</td>
   * </tr>
   * <tr>
   * <td><code>F</code></td>
   * <td>float</td>
   * </tr>
   * <tr>
   * <td><code>I</code></td>
   * <td>int</td>
   * </tr>
   * <tr>
   * <td><code>J</code></td>
   * <td>long</td>
   * </tr>
   * <tr>
   * <td><code>S</code></td>
   * <td>short</td>
   * </tr>
   * <tr>
   * <td><code>V</code></td>
   * <td>void</td>
   * </tr>
   * <tr>
   * <td><code>Z</code></td>
   * <td>boolean</td>
   * </tr>
   * <tr>
   * <td><code>[Z</code></td>
   * <td>boolean [] , array representation</td>
   * </tr>
   * <tr>
   * <td><code>Ljava/lang/String </code></td>
   * <td> <code> java/lang/String</code></td>
   * </tr>
   * </table>
   * 
   * @param jvmType
   *          JVM representation of the data type.
   * @param flagQualifyArray
   *          If set, then an array representation is returned.
   * @return Java Language representation of aDataType.
   */
  public static String getJLSType(String jvmType, boolean flagQualifyArray) {

    char firstChar = jvmType.charAt(0);

    if (jvmType.length() == 1) {
      switch (firstChar) {
      case JVM_TYPE_BYTE:
        return language.getTypeBoolean();
      case JVM_TYPE_CHAR:
        return language.getTypeChar();
      case JVM_TYPE_DOUBLE:
        return language.getTypeDouble();
      case JVM_TYPE_FLOAT:
        return language.getTypeFloat();
      case JVM_TYPE_INT:
        return language.getTypeInt();
      case JVM_TYPE_LONG:
        return language.getTypeLong();
      case JVM_TYPE_SHORT:
        return language.getTypeShort();
      case JVM_TYPE_VOID:
        return language.getTypeVoid();
      case JVM_TYPE_BOOLEAN:
        return language.getTypeBoolean();
      default:
        throw new JVMTypeException("Unknown JVM Type " + firstChar);
      }
    } else if (firstChar == JVM_TYPE_ARRAY) {
      String type = getJLSType(jvmType.substring(1), flagQualifyArray);
      if (!flagQualifyArray) {
        return type + language.getArrayTypeSuffix();
      } else {
        return type;
      }
    } else if (firstChar == JVM_TYPE_CLASS) {
      int len = jvmType.length();
      if (jvmType.indexOf(JVM_TYPE_DELIMITER) == -1) {
        return jvmType.substring(1);
      } else {
        return jvmType.substring(1, len - 1);
      }
    }
    return jvmType;
  }

  /**
   * Determines the length of the JVM datatype representation given the JVM
   * signature.
   * <p>
   * <table>
   * <tr>
   * <th><code>dataType</code></th>
   * <th><code>getSignTokenLength(dataType)</code></th>
   * </tr>
   * <tr>
   * <td><code>all basic data types</code></td>
   * <td>1</td>
   * </tr>
   * <tr>
   * <td><code>[XYZ</code></td>
   * <td>len(XYZ) + 1</td>
   * </tr>
   * <tr>
   * <td><code>Ljava/lang/String </code></td>
   * <td> <code> len(java/lang/String) + 1</code></td>
   * </tr>
   * </table>
   * 
   * @param aDataType
   *          Signature of a method as present in the class file in JVM
   *          representation, containing a list of datatypes.
   * @return the length of the first valid datatype.
   */
  public static int getSignTokenLength(String aDataType) {
    char ch = aDataType.charAt(0);
    switch (ch) {
    case JVM_TYPE_BYTE:
    case JVM_TYPE_CHAR:
    case JVM_TYPE_DOUBLE:
    case JVM_TYPE_FLOAT:
    case JVM_TYPE_INT:
    case JVM_TYPE_LONG:
    case JVM_TYPE_SHORT:
    case JVM_TYPE_VOID:
    case JVM_TYPE_BOOLEAN:
      return 1;
    case JVM_TYPE_ARRAY:
      return (getSignTokenLength(aDataType.substring(1)) + 1);
    case JVM_TYPE_CLASS:
      int semiColon = aDataType.indexOf(JVM_TYPE_DELIMITER);
      if (semiColon == -1) {
        return aDataType.length();
      } else {
        return (semiColon + 1);
      }
    default:
      throw new JVMTypeException("Unknown JVM Type " + ch);
    }
  }

  private static LanguageContext language = new JLSLanguageContext();

  /**
   * Returns the arguments in array form given the JVM signature.
   * <p>
   * For example , <code>IILjava/lang/String</code> could be returned as <br>
   * <code>( I , I , Ljava/lang/String )</code>.
   * 
   * @param methodSignature
   *          Signature of the method.
   * @return The method argument types (jvm types) as a List<String>
   */
  public static List<String> getArguments(String methodSignature) {
    List<String> args = new ArrayList<String>();
    int argBeginIndex = methodSignature.indexOf(JVM_METHOD_ARG_BEGIN_DELIMITER);
    if (argBeginIndex == -1) {
      throw new RuntimeException("Method Signature " + methodSignature
          + " does not have " + JVM_METHOD_ARG_BEGIN_DELIMITER);
    }
    int endIndex = methodSignature.indexOf(JVM_METHOD_ARG_END_DELIMITER);
    if (endIndex > (argBeginIndex + 1)) {// at least one argument is present
      methodSignature = methodSignature.substring(argBeginIndex + 1, endIndex);

      String origStr = methodSignature;
      int length = origStr.length();
      // Start Processing Rhs
      int curIndex = 0;

      while (curIndex < length) {
        methodSignature = origStr.substring(curIndex);
        int tokenLength = getSignTokenLength(methodSignature);
        String tokenString = methodSignature.substring(0, tokenLength);
        int semiColon = tokenString.indexOf(JVM_TYPE_DELIMITER);
        if (semiColon != -1) {
          tokenString = tokenString.substring(0, semiColon);
        }
        args.add(tokenString);
        curIndex += tokenLength;
      }
    }
    return args;
  }

  /**
   * Given the Signature of the method , this provides us the return type.
   * <p>
   * 
   * @param aSignature
   *          Signature of the method.
   * @return the return type associated with the method signature, The type
   *         returned corresponds to JVM representation.
   */
  public static String getReturnType(String aSignature) {
    int index = aSignature.indexOf(JVM_METHOD_ARG_END_DELIMITER);
    return aSignature.substring(index + 1);
  }

  /**
   * Checks if the given datatype is a basic data type or not.
   * 
   * @param type
   *          the datatype to be checked. (in VM notation).
   * @return true , if it is. false , otherwise.
   */
  public static boolean isBasicType(String type) {
    boolean flag = type.length() == 1;
    if (flag) {
      char ch = type.charAt(0);
      flag = (ch == JVM_TYPE_INT // Integer
          || ch == JVM_TYPE_BOOLEAN // Boolean
          || ch == JVM_TYPE_BYTE // byte
          || ch == JVM_TYPE_CHAR // char
          || ch == JVM_TYPE_SHORT // short
          || ch == JVM_TYPE_FLOAT // float
          || ch == JVM_TYPE_LONG // long
      || ch == JVM_TYPE_DOUBLE);// double
    }
    return flag;
  }

  /**
   * Both boolean and char are represented as integers . This takes care of the
   * conversions
   * 
   * @param value
   *          Old Value.
   * @param jvmType
   *          Datatype of the value.
   * @return Returns the new value after making appropriate changes.
   */
  public static String getAtomicValue(String value, String jvmType) {
    value = value.trim();
    if (jvmType.equals(Character.valueOf(JVM_TYPE_BOOLEAN).toString())) { // boolean
      if (value.compareTo(JVM_BOOLEAN_TRUE) == 0) {
        return JLSConstants.BOOLEAN_TRUE;
      } else if (value.compareTo(JVM_BOOLEAN_FALSE) == 0) {
        return JLSConstants.BOOLEAN_FALSE;
      } else {
        return value;
      }
    } else if (jvmType.equals(Character.valueOf(JVM_TYPE_CHAR).toString())) { // Character
      try {
        int intvalue = Integer.parseInt(value);
        return String.format("'%c'", (char)intvalue);
      } catch (NumberFormatException _ex) {
        return value;
      }
    }
    return value;
  }

  /**
   * Both boolean and char are represented as integers . This takes care of the
   * conversions
   * 
   * @param value
   *          Old Value.
   * @param jvmType
   *          Datatype of the value.
   * @return Returns the new value after making appropriate changes.
   */
  public static String getValue(String value, String jvmType) {
    if (jvmType == null) {
      return value;
    }
    int lastQIndex = value.lastIndexOf('?');
    int lastColonIndex = value.lastIndexOf(":");

    if (lastQIndex == -1 || lastColonIndex == -1 || lastQIndex > lastColonIndex) {
      return getAtomicValue(value, jvmType);
    }
    String condition = value.substring(0, lastQIndex);
    String val1 = value.substring(lastQIndex + 1, lastColonIndex);
    String val2 = value.substring(lastColonIndex + 1);

    StringBuilder result = new StringBuilder(condition);
    result.append("? " + getAtomicValue(val1, jvmType));
    result.append(": " + getAtomicValue(val2, jvmType));

    return result.toString();
  }

  /**
   * 
   * @param type
   *          Input Type is a JVM Array Type. For eg, "[I" representing int[]
   *          etc.
   * @return the type of the member of the array. In the above example, this
   *         would return 'I'.
   * 
   */
  public static String getArrayMemberType(String type) {
    int firstArrayIndex = type.indexOf(JVM_TYPE_ARRAY);
    if (firstArrayIndex == -1) {
      throw new IllegalArgumentException("Type " + type
          + " not an array type to begin with");
    }
    return type.substring(firstArrayIndex + 1);
  }

  public static boolean doesTypeOccupy2EntriesInVariableTable(String jvmType) {
    return Character.toString(JVMConstants.JVM_TYPE_LONG).equals(jvmType)
        || Character.toString(JVMConstants.JVM_TYPE_DOUBLE).equals(jvmType);

  }
}
