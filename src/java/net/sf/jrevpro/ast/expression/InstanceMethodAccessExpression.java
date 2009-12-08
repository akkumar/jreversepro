package net.sf.jrevpro.ast.expression;

import java.util.List;

import net.sf.jrevpro.jls.JLSConstants;

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
   * In the expression -<br> <code>string.substring(0,len) <br> <br>   
   * <code>string</code> would be the access target.
   */
  private final  Expression accessTarget;
  
  private final  boolean invokeSpecial;

  
  public InstanceMethodAccessExpression(Expression accessTarget,
      String methodName, String methodType, List<Expression> args,
      boolean invokeSpecial) {
    super(methodName, methodType, args);
    // TODO Auto-generated constructor stub
    
    this.accessTarget = accessTarget;
    this.invokeSpecial = invokeSpecial;
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

    if (invokeSpecial) {
      // invokespecial
      if (methodName.equals(INIT)) {
        result.append(objName);
      }

    } else {
      // invokevirtual
      if (!objName.equals(JLSConstants.THIS)) {
        result.append(objName + JLSConstants.JLS_PACKAGE_DELIMITER);
      }
      result.append(methodName);
    }
    result.append(serializedArgs());
    return result.toString();
  }
}
