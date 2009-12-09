package org.jreversepro.ast.expression;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.jreversepro.ast.expression.Constant;
import org.jreversepro.ast.expression.Expression;
import org.jreversepro.ast.expression.MethodAccessExpression;
import org.jreversepro.ast.expression.StaticMethodAccessExpression;
import org.junit.Test;

public class MethodAccessExpressionTest {

  @Test
  public void testSerializedArgs() {
    MethodAccessExpression mex;
    
    Expression exp1 = new Constant(4);
    Expression exp2 = new Constant(6);
    
    List<Expression> args = new ArrayList<Expression>();
    args.add(exp1);
    args.add(exp2);
    
    mex = new StaticMethodAccessExpression(null, null, null, args) ;
    
    Assert.assertEquals("(4,6)", mex.serializedArgs());
  }
}
