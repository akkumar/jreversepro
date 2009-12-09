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
 * 
 */
package org.jreversepro.ast.expression;

import org.jreversepro.jls.JLSConstants;
import org.jreversepro.jvm.JVMConstants;
import org.jreversepro.jvm.TypeInferrer;


/**
 * Represents all constants as represented by the java programming language.
 * 
 * We input the stringified value and the type.
 * 
 * @author akkumar
 * 
 */
public class Constant extends Expression {

  public Constant(String _value, char _type) {
    super(_type, VALUE);
    value = _value;
  }

  public Constant(int _value) {
    super(JVM_TYPE_INT, VALUE);
    value = String.valueOf(_value);

  }

  public Constant(String _value, String _type) {
    super(_type, VALUE);
    value = _value;
  }

  public Constant(int _value, char _type) {
    super(_type, VALUE);
    value = String.valueOf(_value);
  }

  public String getJLSCode() {
    return TypeInferrer.getValue(value, jvmType);
  }

  public static final Constant VALUE_1 = new Constant(JLSConstants.VALUE_1,
      JVMConstants.JVM_TYPE_INT);

  public static final Constant NULL = new Constant(JLSConstants.NULL,
      JVMConstants.JVM_TYPE_REFERENCE);

  public static final Constant DOUBLE_0 = new Constant("0.0",
      JVMConstants.JVM_TYPE_DOUBLE);

  public static final Constant DOUBLE_1 = new Constant("1.0",
      JVMConstants.JVM_TYPE_DOUBLE);

  public static final Constant FLOAT_0 = new Constant("0.0",
      JVMConstants.JVM_TYPE_FLOAT);

  public static final Constant FLOAT_1 = new Constant("1.0",
      JVMConstants.JVM_TYPE_FLOAT);

  public static final Constant FLOAT_2 = new Constant("2.0",
      JVMConstants.JVM_TYPE_FLOAT);

  private String value;

}
