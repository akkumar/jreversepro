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

public class ReturnExpression extends Expression {

  public ReturnExpression(Expression _expr, char _type) {
    this(_expr, String.valueOf(_type));
  }

  public ReturnExpression(Expression _expr, String _type) {
    super(_type, VALUE);
    expr = _expr;
  }

  public ReturnExpression() {
    super(JVM_TYPE_VOID, VALUE);
    expr = null;
  }

  @Override
  public String getJLSCode() {
    StringBuilder sb = new StringBuilder(50);
    sb.append(JLSConstants.RETURN);
    if (expr != null) {
      sb.append(" ");
      sb.append(expr.getJLSCode());
    }
    return sb.toString();
  }

  private Expression expr;

}
