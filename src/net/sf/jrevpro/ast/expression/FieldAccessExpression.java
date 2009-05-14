/**
 *  @(#) FieldAccessExpression.java
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
public class FieldAccessExpression extends Expression {

	public FieldAccessExpression(Expression _accessTarget, String _fieldName,
			String _fieldType) {
		super(_fieldType, VALUE);

		accessTarget = _accessTarget;
		fieldName = _fieldName;
		isStatic = false;
	}

	public FieldAccessExpression(String _classType, String _fieldName,
			String _fieldType) {
		super(_fieldType, L_EVAL);

		classType = _classType;
		fieldName = _fieldName;
		isStatic = true;
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
		if (isStatic) {
			target = Import.getClassName(TypeInferrer.getJLSType(classType,
					false));

		} else {
			if (!objName.equals(JLSConstants.THIS)) {
				target = objName + JLSConstants.JLS_PACKAGE_DELIMITER;
			}
		}
		return target + fieldName;
	}

	private Expression accessTarget;
	private String fieldName;
	private boolean isStatic;

	private String classType;
}
