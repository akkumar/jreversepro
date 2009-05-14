/**
 *  @(#) Expression.java
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

import java.util.logging.Logger;

import net.sf.jrevpro.CustomLoggerFactory;
import net.sf.jrevpro.jls.JLSConstants;
import net.sf.jrevpro.jvm.JVMConstants;

/**
 * 
 * Represents the abstraction of the Java Language Syntax, Expression.
 * 
 * @author akkumar
 * 
 */
public abstract class Expression implements JVMConstants,
		ExpressionPrecedenceConstants, Cloneable {

	/**
	 * Retrieves the JLS code corresponding to this expression
	 * 
	 * @return String supporting Java Language.
	 */
	public abstract String getJLSCode();

	/**
	 * Release all the resources associated with this Expression
	 */
	// public abstract void release();
	protected Expression(String _jvmType, int _precedence) {
		jvmType = _jvmType;
		precedence = _precedence;
	}

	protected Expression(char _jvmType, int _precedence) {
		jvmType = String.valueOf(_jvmType);
		precedence = _precedence;
	}

	public String getType() {
		return jvmType;
	}

	/**
	 * @param precedence
	 *            precedence of the operand.
	 * @return Returns value taking into account precedence too
	 **/
	public String getValueEx(int precedence) {
		if (this.precedence >= precedence) {
			return getJLSCode();
		} else {
			return JLSConstants.OPEN_BRACKET + getJLSCode()
					+ JLSConstants.CLOSE_BRACKET;
		}
	}

	/**
	 * @return Returns true, if this type is one of the following. integer,
	 *         boolean, byte, character, short, float, reference, return
	 *         address. In case it is either Long / Double the datatype belongs
	 *         to cat2
	 */
	public boolean isCategory1() {
		if (jvmType.length() == 1) {
			char ch = jvmType.charAt(0);
			return !(ch == JVMConstants.JVM_TYPE_LONG || ch == JVM_TYPE_DOUBLE);
		}
		// References are ok too.
		return true;
	}

	/**
	 * Have clone object
	 */
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	protected String jvmType;
	protected int precedence;

	protected static final Logger logger = CustomLoggerFactory.createLogger();

}
