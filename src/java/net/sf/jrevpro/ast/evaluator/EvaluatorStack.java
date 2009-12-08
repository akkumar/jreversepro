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
 **/

package net.sf.jrevpro.ast.evaluator;

import java.util.List;
import java.util.Stack;
import java.util.Vector;

import net.sf.jrevpro.ast.expression.ConditionExpression;
import net.sf.jrevpro.ast.expression.Expression;

/**
 * EvaluatorStack Stack is the abstraction of the Java Method evaluator Stack.
 * The expressions are pushed to and popped from, this stack.
 * 
 * @author Karthik Kumar.
 */
public class EvaluatorStack {

  /**
   * A List of constants. They are primarily used in the following case. Lets
   * say - we have a code called .
   * 
   * String [] args = { "run", "args", "args1" };
   * 
   * In this case all the individual members of the array namely args come into
   * the constants.
   */
  Vector<String> constants;

  /* Currently active statement */
  // Statement statement;
  /* Stores the precedence of the operator */
  int precedence;

  /* Context of a given condition */
  ConditionExpression conditionExpression;

  /**
   * Last processed opcode
   */
  int prevOpcode;
  
  /**
   * Empty constructor
   */
  public EvaluatorStack() {
    stack = new Stack<Expression>();
    constants = new Vector<String>();
  }

  public Expression push(Expression rhs) {
    return stack.push(rhs);
  }

  public Expression pop() {
    return stack.pop();
  }

  public Expression peek() {
    return stack.peek();
  }

  /**
   * Adds a new constant.
   * 
   * @param constant
   *          New Constant to be added to the vector.
   */
  public void addConstant(String constant) {
    constants.add(constant);
  }

  /**
   * Deletes all the constants that were stored in the vector already.
   */
  public void removeAllConstants() {
    constants.removeAllElements();
  }

  /**
   * @return List of constants that were stored.
   */
  public List<String> getConstants() {
    return constants;
  }

  /**
   * Returns the values of the constants serialized such that it corresponds to
   * java syntax code too.
   * 
   * @return Constant values.
   */
  public String getConstantValues() {
    StringBuilder result = new StringBuilder("{");
    for (int i = 0; i < constants.size(); i++) {
      if (i != 0) {
        result.append(",");
      }
      result.append(constants.get(i));
    }
    result.append("}");
    return result.toString();
  }

  public void setPreviousOpcode(int _opcode) {
    prevOpcode = _opcode;

  }

  private final Stack<Expression> stack;



}
