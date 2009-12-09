/**
 * @(#)Field.java  1.00 00/12/09
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
 * */
package org.jreversepro.reflect;

/**
 * Abstraction for the field in a class.
 * 
 * @author Karthik Kumar.
 **/
public class Field extends Member {

  /**
   * Initial value of field. Applicable only if the qualifier has a 'final'
   * keyword in it.
   **/
  private String value;

  /**
   * no-arg constructor.
   **/
  public Field() {
    value = "";
  }

  /**
   * Setter method for value.
   * 
   * @param rhsValue
   *          New Value
   **/
  public void setValue(String rhsValue) {
    value = rhsValue;
  }

  /**
   * Getter method for value.
   * 
   * @return Returns value
   **/
  public String getValue() {
    return value;
  }
}