/**
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
 * 
 */
package net.sf.jrevpro.ast.expression;

import net.sf.jrevpro.jls.JLSConstants;
import net.sf.jrevpro.jvm.TypeInferrer;
import net.sf.jrevpro.reflect.Import;

public class InstanceOfExpression extends Expression {

	public InstanceOfExpression(Expression _reference, String _referenceType) {
		super(JVM_TYPE_BOOLEAN, L_LOGIOF);
		reference = _reference;
		referenceType = _referenceType;

	}

	@Override
	public String getJLSCode() {
		String classType = Import.getClassName(TypeInferrer.getJLSType(
				referenceType, false));

		return reference.getJLSCode() + JLSConstants.SPACE
				+ JLSConstants.INSTANCEOF + JLSConstants.SPACE + classType;
	}

	Expression reference;

	String referenceType;

}
