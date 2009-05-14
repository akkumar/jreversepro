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
 * limitations under the License. * 
 */
package net.sf.jrevpro.output;

import java.util.logging.Logger;

import net.sf.jrevpro.CustomLoggerFactory;
import net.sf.jrevpro.parser.instruction.InstructionListParserException;
import net.sf.jrevpro.parser.instruction.InstructionListParserFactory;
import net.sf.jrevpro.reflect.ClassInfo;
import net.sf.jrevpro.reflect.Method;
import net.sf.jrevpro.reflect.instruction.Instruction;
import net.sf.jrevpro.reflect.instruction.InstructionList;

public class DisassemblerOutputImpl extends AbstractClassOutputterImpl {

	protected DisassemblerOutputImpl(ClassInfo _clazz, CodeStyler _styler) {
		super(_clazz, _styler);

	}

	@Override
	public void process() {
		clearContents();

		outputHeaderComments();
		outputPackageImports();

		outputThisSuperClasses();
		outputInterfaces();

		openBlock();

		outputFields();
		outputMethods();

		closeBlock();
	}

	/**
	 * Returns the stringified disassembled/decompiled method.
	 * 
	 * @param getBytecode
	 *            If TRUE, returns the disassembled code IF the method has
	 *            already been disassembled. If FALSE, returns the decompiled
	 *            code IF the method has been decompiled. Otherwise, returns
	 *            null;
	 * @param includeMetadata -
	 *            TRUE if method stack & exception data should be output
	 * @return Stringified methods in this class
	 */
	protected void outputMethods() {

		for (Method method : clazz.getMethods()) {
			outputMethodHeader(method);
			openBlock();
			try {
				InstructionList list = InstructionListParserFactory
						.createInstructionListParser().parseBytes(
								method.getBytes());
				outputInstructionList(list);
			} catch (InstructionListParserException e) {
				logger.warning(e.toString());
			}
			closeBlock();
		}
	}

	private void outputInstructionList(InstructionList list) {
		for (Instruction ins : list.getAllInstructions()) {
			outputString( styler.outputLine(ins.toString()) );
		}
	}

	private Logger logger = CustomLoggerFactory.createLogger();

}
