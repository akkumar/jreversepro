package org.jreversepro.ast.expression;

import org.jreversepro.jls.JLSConstants;
import org.jreversepro.jvm.TypeInferrer;
import org.jreversepro.reflect.Import;


public class StaticFieldAccessExpression extends FieldAccessExpression {

  private final String classType;

  public StaticFieldAccessExpression(String _classType, String fieldName, String fieldType) {
    super(fieldName, fieldType);
    this.classType = _classType;
  }

  /*
   * (non-Javadoc)
   * 
   * @see net.sf.jrevpro.jls.expression.Expression#getJLSRepresentation()
   */
  @Override
  public String getJLSCode() {
    String target = Import.getClassName(TypeInferrer.getJLSType(classType, false));
    return target + JLSConstants.JLS_PACKAGE_DELIMITER + fieldName;
  }
}
