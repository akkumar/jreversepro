/**
 *  @(#) UnaryOpExpression.java
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

import net.sf.jrevpro.ast.evaluator.EvaluatorStack;
import net.sf.jrevpro.jls.JLSConstants;
import net.sf.jrevpro.jvm.JVMConstants;
import net.sf.jrevpro.jvm.TypeInferrer;
import net.sf.jrevpro.reflect.Import;

/**
 * @author akkumar
 * 
 */
public class UnaryOpExpression extends Expression {

	public enum UnaryOperator {
		NEGATE, CAST_LONG, CAST_FLOAT, CAST_DOUBLE, CAST_INT, CAST_BYTE, CAST_CHAR, CAST_SHORT, UNARY_PLUS, UNARY_MINUS, CAST_REFERENCE
	};

	public UnaryOpExpression(Expression _expr, UnaryOperator op, char type) {
		super(type, L_UNARY);
		expr = _expr;

	}

	public UnaryOpExpression(Expression _expr, UnaryOperator op, String type) {
		super(type, L_UNARY);
		expr = _expr;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.jrevpro.jls.expression.Expression#getJLSRepresentation()
	 */
	@Override
	public String getJLSCode() {
		switch (op) {
		case NEGATE:
			return JLSConstants.MINUS + expr.getValueEx(L_UNARY);
		case CAST_LONG:
			return JLSConstants.OPEN_BRACKET + JLSConstants.JLS_TYPE_LONG
					+ JLSConstants.CLOSE_BRACKET + expr.getValueEx(L_CAST);
		case CAST_FLOAT:
			return JLSConstants.OPEN_BRACKET + JLSConstants.JLS_TYPE_FLOAT
					+ JLSConstants.CLOSE_BRACKET + expr.getValueEx(L_CAST);
		case CAST_DOUBLE:
			return JLSConstants.OPEN_BRACKET + JLSConstants.JLS_TYPE_DOUBLE
					+ JLSConstants.CLOSE_BRACKET + expr.getValueEx(L_CAST);
		case CAST_INT:
			return JLSConstants.OPEN_BRACKET + JLSConstants.JLS_TYPE_INT
					+ JLSConstants.CLOSE_BRACKET + expr.getValueEx(L_CAST);
		case CAST_BYTE:
			return JLSConstants.OPEN_BRACKET + JLSConstants.JLS_TYPE_BYTE
					+ JLSConstants.CLOSE_BRACKET + expr.getValueEx(L_CAST);
		case CAST_CHAR:
			return JLSConstants.OPEN_BRACKET + JLSConstants.JLS_TYPE_CHAR
					+ JLSConstants.CLOSE_BRACKET + expr.getValueEx(L_CAST);
		case CAST_SHORT:
			return JLSConstants.OPEN_BRACKET + JLSConstants.JLS_TYPE_SHORT
					+ JLSConstants.CLOSE_BRACKET + expr.getValueEx(L_CAST);
		case CAST_REFERENCE:
			return JLSConstants.OPEN_BRACKET
					+ Import.getClassName(TypeInferrer.getJLSType(jvmType,
							false)) + JLSConstants.CLOSE_BRACKET
					+ expr.getValueEx(L_CAST);
		default:
			throw new IllegalArgumentException("Unary operator " + op
					+ " not supported");
		}
	}

	public static void evaluateCasting(EvaluatorStack opStack, UnaryOperator unop) {
		Expression expr = opStack.pop();

		UnaryOperator op = UnaryOperator.CAST_BYTE;
		char type = JVMConstants.JVM_TYPE_BYTE;

		switch (unop) {
		case CAST_LONG:
			op = UnaryOperator.CAST_LONG;
			type = JVMConstants.JVM_TYPE_LONG;
			break;
		case CAST_FLOAT:
			op = UnaryOperator.CAST_FLOAT;
			type = JVMConstants.JVM_TYPE_FLOAT;
			break;
		case CAST_DOUBLE:
			op = UnaryOperator.CAST_DOUBLE;
			type = JVMConstants.JVM_TYPE_DOUBLE;
			break;
		case CAST_INT:
			op = UnaryOperator.CAST_INT;
			type = JVMConstants.JVM_TYPE_INT;
			break;
		case CAST_BYTE:
			op = UnaryOperator.CAST_BYTE;
			type = JVMConstants.JVM_TYPE_BYTE;
			break;
		case CAST_CHAR:
			op = UnaryOperator.CAST_CHAR;
			type = JVMConstants.JVM_TYPE_CHAR;
			break;
		case CAST_SHORT:
			op = UnaryOperator.CAST_SHORT;
			type = JVMConstants.JVM_TYPE_SHORT;
			break;
		default:
			throw new IllegalArgumentException("Invalid cast exception" + unop);
		}

		opStack.push(new UnaryOpExpression(expr, op, type));

	}

	public static void evaluateUnary(EvaluatorStack opStack, UnaryOperator unop,
			char type) {
		Expression expr = opStack.pop();
		UnaryOpExpression exp = new UnaryOpExpression(expr, unop, type);
		opStack.push(exp);
	}

	private Expression expr;

	private UnaryOperator op;

}
