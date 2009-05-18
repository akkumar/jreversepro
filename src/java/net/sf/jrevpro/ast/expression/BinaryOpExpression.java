/**
 *  @(#) BinaryOpExpression.java
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

/**
 * @author akkumar
 * 
 */
public class BinaryOpExpression extends Expression {

	public enum BinaryOperator {
		PLUS, MINUS, MULTIPLY, DIVIDE, MODULO, SHIFTLEFT, SHIFTRIGHT, LOGICAL_AND, LOGICAL_OR, LOGICAL_XOR, SMART_MINUS, SMART_PLUS
	};

	public BinaryOpExpression(Expression _lhs, BinaryOperator _op,
			Expression _rhs, char type) {
		super(type, L_EVAL);
		op = _op;
		lhs = _lhs;
		rhs = _rhs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.jrevpro.jls.expression.Expression#getJLSRepresentation()
	 */
	@Override
	public String getJLSCode() {
		switch (op) {
		case PLUS:
			return lhs.getValueEx(L_ADD) + JLSConstants.PLUS
					+ rhs.getValueEx(L_ADD);
		case MINUS:
			return lhs.getValueEx(L_SUB) + JLSConstants.MINUS
					+ rhs.getValueEx(L_SUB + 1);
		case MULTIPLY:
			return lhs.getValueEx(L_MUL) + JLSConstants.MULTIPLY
					+ rhs.getValueEx(L_MUL);
		case DIVIDE:
			return lhs.getValueEx(L_DIV) + JLSConstants.DIVIDE
					+ rhs.getValueEx(L_DIV);
		case MODULO:
			return lhs.getValueEx(L_MOD) + JLSConstants.MODULO
					+ rhs.getValueEx(L_MOD);
		case SHIFTLEFT:
			return lhs.getValueEx(L_SHIFT) + JLSConstants.SHIFTLEFT
					+ rhs.getValueEx(L_SHIFT);
		case SHIFTRIGHT:
			return lhs.getValueEx(L_SHIFT) + JLSConstants.SHIFTRIGHT
					+ rhs.getValueEx(L_SHIFT);
		case LOGICAL_AND:
			return lhs.getValueEx(L_BITAND) + JLSConstants.LOGICAL_AND
					+ rhs.getValueEx(L_BITAND);
		case LOGICAL_OR:
			return lhs.getValueEx(L_BITOR) + JLSConstants.LOGICAL_OR
					+ rhs.getValueEx(L_BITOR);
		case LOGICAL_XOR:
			return lhs.getValueEx(L_BITXOR) + JLSConstants.LOGICAL_XOR
					+ rhs.getValueEx(L_BITXOR);
		default:
			throw new IllegalArgumentException("Illegal binary operator " + op);
		}
	}

	public static void evaluateBinary(EvaluatorStack opStack,
			BinaryOperator binop, char type) {
		Expression op2 = opStack.pop();
		Expression op1 = opStack.pop();
	
		BinaryOpExpression boe = new BinaryOpExpression(op1, binop, op2, type);
		opStack.push(boe);
	}

	private Expression lhs;

	private Expression rhs;

	private BinaryOperator op;
}
