/**
 * @(#)Member.java
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
package net.sf.jrevpro.reflect;

import net.sf.jrevpro.jvm.JVMConstants;

/**
 * Describes a class member ( both field and method ).
 * 
 * @author Karthik Kumar.
 */
public class Member implements JVMConstants {

	/**
	 * Setter method for datatype.
	 * 
	 * @param rhsType
	 *            data type value.
	 */
	public void setDatatype(String rhsType) {
		datatype = rhsType;
	}

	/**
	 * Setter method for name
	 * 
	 * @param rhsName
	 *            New Name
	 */
	public void setName(String rhsName) {
		name = rhsName;
	}

	/**
	 * Setter method for qualifiers.
	 * 
	 * @param rhsQualify
	 *            qualifier value
	 */
	public void setQualifier(int rhsQualify) {
		qualifier = rhsQualify;
	}

	/**
	 * Getter method for name
	 * 
	 * @return Returns name of the member.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter method for the qualifier.
	 * 
	 * @return Returns the qualifier integer.
	 */
	public int getQualifier() {
		return qualifier;
	}

	/**
	 * @return Returns the data type
	 */
	public String getDatatype() {
		return datatype;
	}

	/**
	 * Returns if this member is a 'final' one or not.
	 * 
	 * @return Returns true, if final. false, otherwise.
	 */
	public boolean isFinal() {
		return (qualifier & ACC_FINAL) != 0;
	}

	/**
	 * Returns if this member is a 'static' one or not.
	 * 
	 * @return Returns true, if static. false, otherwise.
	 */
	public boolean isStatic() {
		return (qualifier & ACC_STATIC) != 0;
	}

	/**
	 * This field contains the datatype of the member.
	 */
	protected String datatype;

	/**
	 * This field contains the name of the member ( field/ method).
	 */
	protected String name;

	/**
	 * This contains the integer representation of the qualifier of the member
	 * with the appropriate combination of bits set to know the qualifier
	 * string.
	 */
	protected int qualifier;

}