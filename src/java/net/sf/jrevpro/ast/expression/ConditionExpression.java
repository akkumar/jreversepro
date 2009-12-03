/**
 *  @(#) ConditionExpression.java
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

import net.sf.jrevpro.jls.JLSConstants;
import net.sf.jrevpro.jvm.JVMConstants;

/**
 * @author akkumar
 * 
 */
public class ConditionExpression extends Expression {

  public enum RelationalOperator {
    LT, // <
    LE, // <=
    GT, // >
    GE, // >=
    NE, // !=
    EQ
    // ==
  }

  /**
   * @param _lhs L.H.S of the conditional expression
   * @param _rhs R.H.S of the conditional expression
   * @param _op Operator of the conditional expression
   * 
   */
  public ConditionExpression(Expression _lhs, Expression _rhs,
      RelationalOperator _op) {
    super(JVMConstants.JVM_TYPE_BOOLEAN, assignPrecedence(_op));
    lhs = _lhs;
    rhs = _rhs;
    op = _op;
  }

  private static int assignPrecedence(RelationalOperator argOp) {
    switch (argOp) {
    case EQ:
      return L_LOGEQ;
    case NE:
      return L_LOGNEQ;
    case LT:
    case GT:
    case LE:
    case GE:
      return L_LOGREL;
    default:
      throw new IllegalArgumentException(argOp
          + " is not a valid relational operator");
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see net.sf.jrevpro.jls.expression.Expression#getJLSRepresentation()
   */
  @Override
  public String getJLSCode() {
    String jlsExpression = "";
    switch (op) {
    case EQ:
      jlsExpression = JLSConstants.OPR_EQ;
      break;
    case NE:
      jlsExpression = JLSConstants.OPR_NE;
      break;
    case LT:
      jlsExpression = JLSConstants.OPR_LT;
      break;
    case GE:
      jlsExpression = JLSConstants.OPR_GE;
      break;
    case GT:
      jlsExpression = JLSConstants.OPR_GT;
      break;
    case LE:
      jlsExpression = JLSConstants.OPR_LE;
      break;
    }
    return lhs.getJLSCode() + jlsExpression + rhs.getJLSCode();
  }

  Expression lhs;
  Expression rhs;

  RelationalOperator op;
}
