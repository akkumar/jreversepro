/**
 * @(#)BranchEntry.java
 *
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
package net.sf.jrevpro.decompile.simulate;

import net.sf.jrevpro.reflect.Method;

/**
 * @author Karthik Kumar
 */
public class BranchEntry implements BranchConstants {

	public String opr1;
	public String opr2;
	public String operator;

	public BranchEntry(Method method, int startPc, int startPc2, int retPc,
			int typeJsr, String string, String string2, String string3) {
		// TODO Auto-generated constructor stub
	}

	public int getStartExecPc() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getEndBlockPc() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getType() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean doesStartWith(int insIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	public int getStartPc() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setType(int typeElseIf) {
		// TODO Auto-generated method stub

	}

}
