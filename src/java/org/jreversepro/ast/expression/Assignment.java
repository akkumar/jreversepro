/**
 *  @(#) Assignment.java
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
package org.jreversepro.ast.expression;

import org.jreversepro.jls.JLSConstants;

/**
 * @author akkumar
 * 
 */
public class Assignment extends Expression {

  private final Expression lhs;

  private final Expression rhs;
  
  /**
   * @param _lhs L.H.S of the given expression
   * @param _rhs R.H.S of the given expression
   * 
   */
  public Assignment(Expression _lhs, Expression _rhs) {
    super(_rhs.getType(), L_EVAL);
    lhs = _lhs;
    rhs = _rhs;
  }

  /*
   * (non-Javadoc)
   * 
   * @see net.sf.jrevpro.jls.expression.Expression#getJLSRepresentation()
   */
  @Override
  public String getJLSCode() {
    return lhs.getJLSCode() + JLSConstants.EQUALTO + rhs.getJLSCode();

  }



}
