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
package org.jreversepro.parser;

/**
 * Thrown if Class File does not follow the prescribed format.
 * 
 * @author Karthik Kumar
 * @version 1.3
 */
@SuppressWarnings("serial")
public class ClassParserException extends Exception {

  /**
   * Constructor.
   * 
   * @param aMsg
   *          Exception Message.
   */
  public ClassParserException(String aMsg) {
    msg = aMsg;
  }

  /**
   * Constructor.
   * 
   * @param aMsg
   *          Exception Message as a java.lang.StringBuilder
   */
  public ClassParserException(StringBuilder aMsg) {
    msg = aMsg.toString();
  }

  /**
   * Serialized version.
   * 
   * @return Returns a Stringified version of the string.
   */
  public String toString() {
    return getClass().getPackage() + msg;
  }

  /**
   * Exception message.
   */
  private String msg;

}
