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
package net.sf.jrevpro.jls;

public interface JLSConstants {

	String JLS_TYPE_BYTE = "byte";

	String JLS_TYPE_CHAR = "char";

	String JLS_TYPE_DOUBLE = "double";

	String JLS_TYPE_FLOAT = "float";

	String JLS_TYPE_INT = "int";

	String JLS_TYPE_LONG = "long";

	String JLS_TYPE_SHORT = "short";

	String JLS_TYPE_VOID = "void";

	String JLS_TYPE_BOOLEAN = "boolean";

	String JLS_TYPE_INVALID = "invalid";

	String JLS_TYPE_ARRAY_SUFFIX = "[]";

	String BREAK = "break";
	String CONTINUE = "continue";
	String DEFAULT = "default";

	String CASE = "case";
	String GOTO = "goto";

	/**
	 * Default Package that is included when the JVM is launched in the
	 * beginning.
	 */
	String DEFAULT_PACKAGE = "java.lang";

	/**
	 * 'null' datatype.
	 */
	String NULL = "null";

	/**
	 * 'void' datatype.
	 */
	String VOID = "void";

	/**
	 * this pointer variable name
	 */
	String THIS = "this";

	String BOOLEAN_TRUE = "true";

	String BOOLEAN_FALSE = "false";

	String CLASS = "class";

	String INTERFACE = "interface";

	String LENGTH = "length";

	String THROW = "throw";

	String INSTANCEOF = "instanceof";

	String NEW = "new";

	char SPACE = ' ';

	String OPEN_SQUARE_BRACKET = "[";

	String CLOSE_SQUARE_BRACKET = "]";

	String EQUALTO = " = ";

	String RETURN = "return";

	String SWITCH = "switch";

	String STATIC = "static";

	String SUPER = "super";

	// Operators
	String OPR_GT = ">";
	String OPR_GE = ">=";
	String OPR_LT = "<";
	String OPR_LE = "<=";
	String OPR_NE = "!=";
	String OPR_EQ = "==";
	String OPR_NOT = "!";

	String COND_OR = "||";
	String COND_AND = "&&";
	String COND_NOT = "!";

	String JLS_FLOATING_NUMBER_SUFFIX = "f";
	String JLS_LONG_NUMBER_SUFFIX = "L";

	String IMPORT = "import";

	String END_OF_STATEMENT = ";";

	char JLS_PACKAGE_DELIMITER = '.';

	String IMPLEMENTS = "implements";

	String INTERFACE_DELIMITER = ", ";

	String EXTENDS = "extends";

	String PACKAGE = "package";

	/** * List of language qualifiers * */

	String ACCESS_PUBLIC = "public";

	String ACCESS_PRIVATE = "private";

	String ACCESS_PROTECTED = "protected";

	String ACCESS_STATIC = "static";

	String ACCESS_FINAL = "final";

	String ACCESS_ABSTRACT = "abstract";

	String ACCESS_VOLATILE = "volatile";

	String ACCESS_TRANSIENT = "transient";

	String ACCESS_SYNCHRONIZED = "synchronized";

	String ACCESS_NATIVE = "native";

	String ACCESS_STRICTFP = "strictfp";

	String THROWS = "throws";

	String ELSE = "else";

	String FINALLY = "finally";

	String CATCH = "catch";

	String DO = "do";

	String WHILE = "while";

	String FOR = "for";

	String IF = "if";

	String TRY = "try";

	String UNARY_PLUS = "++";

	String UNARY_MINUS = "--";

	String PLUS = "+";

	String MINUS = "-";

	String MULTIPLY = "*";

	String DIVIDE = "/";

	String MODULO = "%";

	String SHIFTLEFT = "<<";

	String SHIFTRIGHT = ">>";

	String LOGICAL_AND = "&";

	String LOGICAL_OR = "|";

	String LOGICAL_XOR = "^";

	String SMART_MINUS = "-=";

	String SMART_PLUS = "+=";

	String OPEN_BRACKET = "(";

	String CLOSE_BRACKET = ")";

	String VALUE_0 = "0";

	String VALUE_1 = "1";

	String ARGS_SEPARATOR = ",";
}
