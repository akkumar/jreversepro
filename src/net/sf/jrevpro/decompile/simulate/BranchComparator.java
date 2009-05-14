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
package net.sf.jrevpro.decompile.simulate;

import java.util.Comparator;

/**
 * Comparator for comparing two branch entries.
 * 
 * @author Karthik Kumar.
 */
public class BranchComparator implements Comparator<BranchEntry> {

	/**
	 * Method to compare two JBranchComparator objects
	 * 
	 * @param o1
	 *            First Object to be compared.
	 * @param o2
	 *            Second object to be compared.
	 * @return -1 if First branch overlaps Second branch, 1 if first branch
	 *         doesnt overlap and exactly outside the second branch.
	 */
	public int compare(BranchEntry e1, BranchEntry e2) {
		int exec1 = e1.getStartExecPc();
		int exec2 = e2.getStartExecPc();
		int end1 = e1.getEndBlockPc();
		int end2 = e2.getEndBlockPc();

		if (exec1 < exec2) {
			return -1;
		} else if (exec1 == exec2) {
			if (end1 > end2) {
				return -1;
			} else {
				return 1;
			}
		} else {
			return 1;
		}

	}

	/**
	 * @param obj
	 *            R.H.S object to be compared.
	 * @return true, if both are equal. false, otherwise.
	 */
	public boolean equals(Object obj) {
		return (obj instanceof BranchEntry);
	}
}