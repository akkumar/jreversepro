/*
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
 */

package net.sf.jrevpro.ast.expression;

/**
 * This contains constants maintaining the precedence of expressions.
 * 
 * @author Jiri Malak.
 */
interface ExpressionPrecedenceConstants {

  /**
   * All values take higher precedence.
   */
  int VALUE = 99;

  /**
   * 
   * referring to method or field operator . .
   */
  int L_REF = 15;

  /**
   * Array indexing operator . []
   */
  int L_INDEX = 15;

  /**
   * Casting operator. (..) casting.
   */
  int L_CAST = 15;

  /**
   * ++ -- - + ~ operators.
   */
  int L_UNARY = 14;

  /**
   * instanceof operator.
   */
  int L_LOGIOF = 14;

  /**
   * * Mulitply operator.
   */
  int L_MUL = 13;

  /**
   * / Division operator
   */
  int L_DIV = 13;

  /**
   * % operator.
   */
  int L_MOD = 13;

  /**
   * .. + ..
   */
  int L_ADD = 12;

  /**
   * .. - ..
   */
  int L_SUB = 12;

  /**
   * << >>> >>
   */
  int L_SHIFT = 11;

  /**
   * < <= >= >
   */
  int L_LOGREL = 10;

  /**
   * ==
   */
  int L_LOGEQ = 9;

  /**
   * .. != ..
   */
  int L_LOGNEQ = 9;

  /**
   * .. & ..
   */
  int L_BITAND = 8;

  /**
   * // .. ^ ..
   */
  int L_BITXOR = 7;

  /**
   * .. | ..
   */
  int L_BITOR = 6;

  /**
   * .. && ..
   */
  int L_LOGAND = 5;

  /**
   * .. || ..
   */
  int L_LOGOR = 4;

  /**
   * (cond..) ? :
   */
  int L_TERN = 3;

  /**
   * evaluation = += -= *= ......
   */
  int L_EVAL = 2; //

  /**
   * comma in for statement
   */
  int L_COMMA = 1;
}
