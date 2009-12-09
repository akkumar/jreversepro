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

public class ArrayMemberReferenceExpression extends Expression {

  public ArrayMemberReferenceExpression(Expression _arrayObject,
      Expression _subscript, String type) {
    super(type, VALUE);
    arrayObject = _arrayObject;
    subscript = _subscript;
  }

  public ArrayMemberReferenceExpression(Expression _arrayObject,
      Expression _subscript, char type) {
    super(type, VALUE);
    arrayObject = _arrayObject;
    subscript = _subscript;
  }

  @Override
  public String getJLSCode() {
    return arrayObject.getJLSCode() + JLSConstants.OPEN_SQUARE_BRACKET
        + subscript.getJLSCode() + JLSConstants.CLOSE_SQUARE_BRACKET;
  }

  private Expression arrayObject;

  private Expression subscript;

}
