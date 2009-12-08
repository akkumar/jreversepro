package net.sf.jrevpro.ast.expression;

import java.util.List;

import net.sf.jrevpro.jls.JLSConstants;
import net.sf.jrevpro.jvm.TypeInferrer;
import net.sf.jrevpro.reflect.Import;

/**
 * Method access expression for static method calls.
 * <p>
 * Eg: <code>String.valueOf(4)</code>
 * 
 * @author karthik.kumar
 *
 */
public class StaticMethodAccessExpression extends MethodAccessExpression {

  /**
   * Class Type to which this method belongs to.
   */
  private final String classType;
  

  public StaticMethodAccessExpression(String _classType, String _methodName,
      String _methodType, List<Expression> _args) {
    super(_methodName, _methodType, _args);

    classType = _classType;
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see net.sf.jrevpro.jls.expression.Expression#getJLSRepresentation()
   */
  @Override
  public String getJLSCode() {

    StringBuilder result = new StringBuilder();

    result.append(Import.getClassName(TypeInferrer.getJLSType(classType,
          false))
          + JLSConstants.JLS_PACKAGE_DELIMITER + methodName);
    
    result.append(serializedArgs());
    return result.toString();
  }  
}
