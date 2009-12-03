/**
 *  @(#) ObjectInstantiationExpression.java
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
import net.sf.jrevpro.jvm.TypeInferrer;
import net.sf.jrevpro.reflect.Import;

/**
 * @author akkumar
 * 
 */
public class ObjectInstantiationExpression extends Expression {

  /**
   * @param jvmType Type of the object in the JVM notation 
   */
  public ObjectInstantiationExpression(String jvmType) {
    super(jvmType, L_REF);
  }

  /*
   * (non-Javadoc)
   * 
   * @see net.sf.jrevpro.jls.expression.Expression#getJLSRepresentation()
   */
  @Override
  public String getJLSCode() {

    String className = Import.getClassName(TypeInferrer.getJLSType(jvmType,
        false));

    return JLSConstants.NEW + " " + className;
  }

}
