package org.jreversepro.ast.expression;

import java.util.List;

import org.jreversepro.jls.JLSConstants;


/**
 * Method access expression specifically for instance methods.
 * 
 * @author karthik.kumar
 * 
 */
public class InstanceMethodAccessExpression extends MethodAccessExpression {

  /**
   * The target of the method access expression as needed.
   * <p>
   * In the expression -<br>
   * <code>string.substring(0,len) <br> <br>   
   * <code>string</code> would be the access target.
   */
  private final Expression accessTarget;

  public InstanceMethodAccessExpression(Expression accessTarget,
      String methodName, String methodType, List<Expression> args) {
    super(methodName, methodType, args);

    this.accessTarget = accessTarget;
  }

  /*
   * (non-Javadoc)
   * 
   * @see net.sf.jrevpro.jls.expression.Expression#getJLSRepresentation()
   */
  @Override
  public String getJLSCode() {

    String objName = accessTarget.getJLSCode();
    StringBuilder result = new StringBuilder();

    if (methodName.equals(INIT)) {
      result.append(objName); // This would be a ctor.
    } else {
      if (!objName.equals(JLSConstants.THIS)) {
        result.append(objName + JLSConstants.JLS_PACKAGE_DELIMITER);
      }
      result.append(methodName);
    }
    result.append(serializedArgs());
    return result.toString();
  }
}
