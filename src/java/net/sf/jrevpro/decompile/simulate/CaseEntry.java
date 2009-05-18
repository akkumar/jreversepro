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
package net.sf.jrevpro.decompile.simulate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import net.sf.jrevpro.jls.JLSConstants;

public class CaseEntry {

	/**
	 * Empty constructor.
	 */
	public CaseEntry() {
		values = new ArrayList<String>();
	}

	/**
	 * @param name
	 *            Name of the case target.
	 * @param targetPc
	 *            PC of the corresponding handler for this case target.
	 */
	public CaseEntry(String name, int targetPc) {
		target = targetPc;
		values = new ArrayList<String>();
		values.add(name);
	}

	/**
	 * Adds another case target.
	 * 
	 * @param name
	 *            Name of the case target.
	 */
	public void addValue(String name) {
		values.add(name);
	}

	/**
	 * @return Returns the List of case targets. Members are 'String'.
	 */
	public List<String> getValues() {
		return values;
	}

	/**
	 * @return Returns the targetPc of the beginning branch
	 */
	public int getTarget() {
		return target;
	}

	/**
	 * Setter method for TargetPc
	 * 
	 * @param targetPc
	 *            TargetPc
	 */
	public void setTarget(int targetPc) {
		target = targetPc;
	}

	/**
	 * @return Returns End of the target for this case block.
	 */
	public int getEndTarget() {
		return endTarget;
	}

	/**
	 * Setter method for endTarget.
	 * 
	 * @param endTarget
	 *            End Target for this case group block.
	 */
	public void setEndTarget(int endTarget) {
		this.endTarget = endTarget;
	}

	/**
	 * Returns a string with the case targets and the corresponding branch PCs
	 * listed.
	 * 
	 * @return Disassembled piece of code.
	 */
	public String disAssemble() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < values.size(); i++) {
			sb.append(values.get(i) + ":\n\t\t\t");
		}
		sb.append("\n\t\t\t\t" + JLSConstants.GOTO + " " + target);
		return sb.toString();
	}

	/**
	 * @return Returns a Stringified form of the class.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(disAssemble() + " upto " + endTarget);
		return sb.toString();
	}

	/**
	 * List of case targets that have similar target. Ordinarily they have just
	 * one entry. But sometimes they may have more than one entry. For eg.
	 * 
	 * case 12: case 13: case 18: <<do Something>>
	 * 
	 * In this case there will be three entries in the list.
	 */
	List<String> values;

	/**
	 * Target of this group of case entry.
	 */
	int target;

	/**
	 * End Pc of this case target and the beginnin of the next target.
	 */
	int endTarget;

}

/**
 * Comparator for comparing two case entries.
 * 
 * @author Karthik Kumar.
 */
class CaseComparator implements Comparator<CaseEntry> {

	/**
	 * @param o1
	 *            First Object to be compared.
	 * @param o2
	 *            Second object to be compared.
	 * @return 0 if both the case statements' target are equal. 1, if first
	 *         target > second target. -1, otherwise.
	 */
	public int compare(CaseEntry e1, CaseEntry e2) {
		int exec1 = e1.getTarget();
		int exec2 = e2.getTarget();

		if (exec1 < exec2) {
			return -1;
		} else if (exec1 == exec2) {
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * @param obj
	 *            Object to be compared.
	 * @return true, if the object is JCaseEntry. false, otherwise.
	 */
	public boolean equals(Object obj) {
		return obj instanceof CaseEntry;
	}
}
