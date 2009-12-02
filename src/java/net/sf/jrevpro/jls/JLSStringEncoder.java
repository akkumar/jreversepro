/**
 * @(#)JLSStringEncoder.java
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
 */
package net.sf.jrevpro.jls;

/**
 * JLSStringEncoder contains a list of assorted methods that helps in converting
 * a given string to a representation that can be programatically represented in
 * the Java Programming Language.
 * 
 * @author Karthik Kumar
 */
public class JLSStringEncoder {

  /**
   * Inserts a '\' before all the escape characters , line '\n' , '\t' to
   * provide better readability.
   * <p>
   * 
   * @param aLiteral
   *          String containing the escape characters.
   * @return the new String containing the new escape sequence of characters.
   */
  public static String encodeStringInJLSSource(String aLiteral) {
    StringBuilder result = new StringBuilder("");
    for (int i = 0; i < aLiteral.length(); i++) {
      result.append(encodeCharInJLSSource(aLiteral.charAt(i)));
    }
    return result.toString();
  }

  /**
   * Returns the String representation of the character .
   * 
   * @param aChar
   *          - Character.
   * @return the new String representing the character.
   */
  private static String encodeCharInJLSSource(char aChar) {
    switch (aChar) {
    case '\n':
      return "\\n";
    case '\t':
      return "\\t";
    case '\\':
      return "\\\\";
    case '"':
      return "\\" + "\"";
    default:
      return String.valueOf(aChar);
    }
  }

  /**
   * Private constructor to prevent any instance from being created.
   */
  private JLSStringEncoder() {
  }

}
