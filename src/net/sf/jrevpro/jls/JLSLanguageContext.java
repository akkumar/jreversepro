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

import java.util.ArrayList;
import java.util.List;

import net.sf.jrevpro.jvm.LanguageContext;

public class JLSLanguageContext implements LanguageContext, JLSConstants {

	static {
		loadKeywords();
	}

	public String getTypeBoolean() {
		return JLS_TYPE_BOOLEAN;
	}

	public String getTypeChar() {
		return JLS_TYPE_CHAR;
	}

	public String getTypeDouble() {
		return JLS_TYPE_DOUBLE;
	}

	public String getTypeFloat() {
		return JLS_TYPE_FLOAT;
	}

	public String getTypeInt() {
		return JLS_TYPE_INT;
	}

	public String getTypeLong() {
		return JLS_TYPE_LONG;
	}

	public String getArrayTypeSuffix() {
		return JLS_TYPE_ARRAY_SUFFIX;
	}

	public String getTypeShort() {
		return JLS_TYPE_SHORT;
	}

	public String getTypeVoid() {
		return JLS_TYPE_VOID;
	}

	public static List<String> getKeywordList() {
		return keywordList;
	}

	private static void loadKeywords() {
		keywordList = new ArrayList<String>();

		keywordList.add(ACCESS_ABSTRACT);
		keywordList.add(JLS_TYPE_DOUBLE);
		keywordList.add(JLS_TYPE_INT);
		keywordList.add(ACCESS_STRICTFP);
		keywordList.add(JLS_TYPE_BOOLEAN);
		keywordList.add(ELSE);
		keywordList.add(INTERFACE);
		keywordList.add(SUPER);
		keywordList.add(ACCESS_SYNCHRONIZED);
		keywordList.add(BREAK);
		keywordList.add(EXTENDS);
		keywordList.add(JLS_TYPE_LONG);
		keywordList.add(SWITCH);
		keywordList.add(JLS_TYPE_BYTE);
		keywordList.add(ACCESS_FINAL);
		keywordList.add(ACCESS_NATIVE);
		keywordList.add(CASE);
		keywordList.add(FINALLY);
		keywordList.add(NEW);
		// keywordList.add("this");
		keywordList.add(CATCH);
		keywordList.add(JLS_TYPE_FLOAT);
		keywordList.add(PACKAGE);
		keywordList.add(THROW);
		keywordList.add(JLS_TYPE_CHAR);
		keywordList.add(FOR);
		keywordList.add(ACCESS_PRIVATE);
		keywordList.add(THROWS);
		keywordList.add(CLASS);
		keywordList.add(GOTO);
		keywordList.add(ACCESS_PROTECTED);
		keywordList.add(ACCESS_TRANSIENT);
		keywordList.add("const");// TODO: is const a java keyword
		keywordList.add(IF);
		keywordList.add(ACCESS_PUBLIC);
		keywordList.add(TRY);
		keywordList.add(CONTINUE);
		keywordList.add(IMPLEMENTS);
		keywordList.add(RETURN);
		keywordList.add(JLS_TYPE_VOID);
		keywordList.add(DEFAULT);
		keywordList.add(IMPORT);
		keywordList.add(JLS_TYPE_SHORT);
		keywordList.add(ACCESS_VOLATILE);
		keywordList.add(DO);
		keywordList.add(INSTANCEOF);
		keywordList.add(ACCESS_STATIC);
		keywordList.add(WHILE);

	}

	private static List<String> keywordList;

}
