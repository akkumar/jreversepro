/**
 *  @(#) MethodAccessExpression.java
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

import java.util.List;

import net.sf.jrevpro.jls.JLSConstants;
import net.sf.jrevpro.jvm.TypeInferrer;
import net.sf.jrevpro.reflect.Import;

/**
 * @author akkumar
 * 
 */
public class MethodAccessExpression extends Expression {

	public MethodAccessExpression(Expression _accessTarget, String _methodName,
			String _methodType, List<Expression> _args, boolean _invokeSpecial) {
		super(_methodType, L_REF);

		accessTarget = _accessTarget;
		methodName = _methodName;
		isStatic = false;
		args = _args;
		invokeSpecial = _invokeSpecial;
	}

	public MethodAccessExpression(String _classType, String _methodName,
			String _methodType, List<Expression> _args) {
		super(_methodType, L_REF);

		classType = _classType;
		methodName = _methodName;
		isStatic = true;
		args = _args;
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

		if (isStatic) {
			// invokestatic
			result.append(Import.getClassName(TypeInferrer.getJLSType(
					classType, false))
					+ JLSConstants.JLS_PACKAGE_DELIMITER + methodName);
		} else if (invokeSpecial) {
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

		result.append(JLSConstants.OPEN_BRACKET);
		for (int i = 0; i < args.size(); i++) {
			result.append(args.get(i).getJLSCode());
			if (i != 0) {
				result.append(JLSConstants.ARGS_SEPARATOR);
			}
		}
		result.append(JLSConstants.CLOSE_BRACKET);
		return result.toString();
	}

	private Expression accessTarget;
	private String methodName;
	private boolean isStatic;
	private List<Expression> args;

	private String classType;
	private boolean invokeSpecial;

	/*
	 * 
	 * // Takes care of modifying the input if
	 * (objRef.compareTo(JLSConstants.THIS) != 0) { if (flagInvokeSpecial &&
	 * methodName.equals(INIT)) { opStack.statement = objRef; // Constructor }
	 * else { opStack.statement = objRef + "." + methodName; } } else { if
	 * (flagInvokeSpecial && methodName.equals(INIT)) { if
	 * ((className.equals(JVMConstants.CLASS_LANG_OBJECT))) { opStack.statement
	 * = ""; // filter out the default Object() // constructor. return; } else {
	 * opStack.statement = JLSConstants.SUPER; // Code for super constructor
	 * here. } } else { opStack.statement = methodName; } } opStack.precedence =
	 * L_REF; opStack.statement += getArgValues(jvmArgTypes, argValues);
	 * 
	 * if (methodType.compareTo(String.valueOf(JVM_TYPE_VOID)) == 0) { if
	 * (flagInvokeSpecial && !opStack.empty()) { Operand op1 = opStack.pop();
	 * opStack.push(new Operand(opStack.statement, op1.getDatatype(),
	 * opStack.precedence)); } } else { opStack.push(opStack.statement,
	 * methodType, opStack.precedence); }
	 */

}
