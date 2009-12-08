package net.sf.jrevpro.ast.expression;

import net.sf.jrevpro.jls.JLSConstants;
import net.sf.jrevpro.jvm.TypeInferrer;
import net.sf.jrevpro.reflect.Import;

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
