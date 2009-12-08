/**
 *  @(#) MethodAccessExpression.java
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
package net.sf.jrevpro.ast.expression;

import java.util.List;

import net.sf.jrevpro.jls.JLSConstants;

/**
 * Expression representing a method access along the following lines. 
 * <p>
 * Eg:
 * <code>string.substring(0,len) </code> 
 * 
 * 
 * @author akkumar
 * 
 */
public abstract class MethodAccessExpression extends Expression {
  
  /**
   * The list of arguments for the given method call , that occur as a List of Expressions.
   */
  private final List<Expression> args;

  /**
   * The name of the method in the method access expression.
   * 
   * <p>
   * In the expression -<br> <code>string.substring(0,len) <br> <br>   
   * <code>substring</code> would  be the method name.
   * 
   */
  protected final String methodName;  
  
  public MethodAccessExpression(String _methodName,
      String _methodType, List<Expression> _args) {
    super(_methodType, L_REF);
    methodName = _methodName;
    args = _args;
  }
  
  protected String serializedArgs() {
    final StringBuilder result = new StringBuilder();
    result.append(JLSConstants.OPEN_BRACKET);
    for (int i = 0; i < args.size(); i++) {
      if (i != 0) {
        result.append(JLSConstants.ARGS_SEPARATOR);
      }
      result.append(args.get(i).getJLSCode());
    }
    result.append(JLSConstants.CLOSE_BRACKET);
    return result.toString();    
  }
}
