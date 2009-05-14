/**
 *  * JReversePro - Java Decompiler / Disassembler.
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
package net.sf.jrevpro;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import net.sf.jrevpro.output.AbstractOutputter;
import net.sf.jrevpro.output.DecompilerOutputter;
import net.sf.jrevpro.output.DisassemblerOutputter;
import net.sf.jrevpro.parser.ClassFileParser;
import net.sf.jrevpro.parser.ClassFileParserFactory;
import net.sf.jrevpro.parser.ClassParserException;
import net.sf.jrevpro.reflect.ClassInfo;

/**
 * 
 * @author akkumar Karthik Kumar
 * 
 */
public class JReverseProContext {

	public enum OutputType {
		NONE, DISASSEMBLER, DECOMPILER, VIEW_CONSTANTPOOL,
	}

	/**
	 * 
	 * @param pathToClass
	 *            Path to the class for which resource needs to be loaded
	 * @return
	 * @throws FileNotFoundException
	 * @throws ClassParserException
	 */
	public ClassInfo loadResource(String pathToClass)
			throws FileNotFoundException, IOException, ClassParserException {
		FileInputStream fis = new FileInputStream(pathToClass);
		DataInputStream dis = new DataInputStream(fis);

		ClassFileParser cfp = ClassFileParserFactory.getClassFileParser(dis);
		ClassInfo info = cfp.parseInputStream(dis, pathToClass);
		fis.close();
		return info;
	}

	public String print(OutputType outputType, ClassInfo info) {
		AbstractOutputter printer = null;
		switch (outputType) {
		case DISASSEMBLER:
			printer = new DisassemblerOutputter();
			break;
		case DECOMPILER:
			printer = new DecompilerOutputter();
			break;
		}
		return printer.output(info);
	}

	public static void checkJREVersion() {
		if (!VersionChecker.versionCheck()) {
			System.exit(1);
		}
		System.out.println(GPL_INFO);
	}

	/**
	 * Version of the software.
	 */
	public static final String VERSION = "1.5.0";

	/**
	 * GPL Information.
	 */
	public static String GPL_INFO = "// JReversePro v " + VERSION + " "
			+ (new Date()) + "\n// http://jrevpro.sourceforge.net"
			+ "\n// Copyright (C)2008 Karthik Kumar."
			+ "\n// JReversePro comes with ABSOLUTELY NO WARRANTY;"
			+ "\n// This is free software, and you are welcome to redistribute"
			+ "\n// it under certain conditions;See the File 'COPYING' for "
			+ "more details.\n";

	private Logger logger = CustomLoggerFactory.createLogger();
}
