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
package net.sf.jrevpro.ast.block;

import net.sf.jrevpro.ast.expression.Expression;
import net.sf.jrevpro.reflect.instruction.Instruction;

/**
 * Represents the abstraction of a statement
 * 
 * @author akkumar
 * 
 */
public class Statement extends Block {

	/**
	 * 
	 * @param _expr
	 *            Expression contained within a given statement.
	 */
	public Statement(Block _parent, Instruction _instruction, Expression _expr) {
		super(_parent);
		expr = _expr;
		instruction = _instruction;
	}

	@Override
	public int endOfBlock() {
		return instruction.getNextIndex();
	}

	public Expression getExpression() {
		return expr;
	}

	private Expression expr;
	private Instruction instruction;

}
