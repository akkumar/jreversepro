package org.jreversepro.ast.expression;

import org.jreversepro.jls.JLSConstants;

public class InstanceFieldAccessExpression extends FieldAccessExpression {

  private final Expression accessTarget;

  public InstanceFieldAccessExpression(Expression accessTarget,
      String fieldName, String fieldType) {
    super(fieldName, fieldType);
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
    String target = "";
    if (!objName.equals(JLSConstants.THIS)) {
      target = objName + JLSConstants.JLS_PACKAGE_DELIMITER;
    }
    return target + fieldName;
  }

}
