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
 ***/
package net.sf.jrevpro.parser.common;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.sf.jrevpro.CustomLoggerFactory;
import net.sf.jrevpro.jvm.JVMConstants;
import net.sf.jrevpro.jvm.TypeInferrer;
import net.sf.jrevpro.reflect.ConstantPool;
import net.sf.jrevpro.reflect.LocalVariableTable;
import net.sf.jrevpro.reflect.Method;

/**
 * <b>Attribute</b> has the ability to read the 'ATTRIBUTES' of the Field ,
 * Method and the Class as a whole.
 * 
 * @author Karthik Kumar
 * @version 1.00,
 */
public final class AttributeParser {

	/**
	 * Manipulates the 'ConstantValue' attribute of the Fields.
	 * <p>
	 * <b>ConstantValue</b> attribute <br>
	 * u2 attribute_name_index; <br>
	 * u4 attribute_length;<br>
	 * u2 constantvalue_index;<br>
	 * </p>
	 * 
	 * @param aDis
	 *            DataInputStream containing the bytes of the class.
	 * @param aCpInfo
	 *            ConstantPool Information.
	 * @return a String containing the Constant value, of the field.
	 * @throws IOException
	 *             Error in Class Stream of bytes.
	 */
	public static String readConstantValue(DataInputStream aDis,
			ConstantPool aCpInfo) throws IOException {

		int len = aDis.readInt();
		short index = aDis.readShort();
		return (aCpInfo.getBasicDataTypeValue(index));
	}

	/**
	 * Manipulates the 'Deprecated' attribute of the Fields.
	 * <p>
	 * <b>Deprecated</b> attribute <br>
	 * u2 attribute_name_index; <br>
	 * u4 attribute_length;<br>
	 * </p>
	 * 
	 * @param aDis
	 *            DataInputStream containing the bytes of the class.
	 * @throws IOException
	 *             Error in Class Stream of bytes.
	 */
	public static void readDeprecated(DataInputStream aDis) throws IOException {
		int len = aDis.readInt();// len must be zero.
	}

	/**
	 * Manipulates the 'Synthetic' attribute of the Fields.
	 * <p>
	 * <b>Synthetic</b> attribute <br>
	 * u2 attribute_name_index; <br>
	 * u4 attribute_length;<br>
	 * </p>
	 * 
	 * @param aDis
	 *            DataInputStream containing the bytes of the class.
	 * @throws IOException
	 *             Error in Class Stream of bytes.
	 */
	public static void readSynthetic(DataInputStream aDis) throws IOException {
		int len = aDis.readInt();// len must be zero.
	}

	/**
	 * Manipulates the 'Code' attribute of the Methods.
	 * <p>
	 * <b>Code</b> attribute <br>
	 * u2 attribute_name_index;<br>
	 * u4 attribute_length;<br>
	 * u2 max_stack; <br>
	 * u2 max_locals; <br>
	 * u4 code_length; <br>
	 * u1 code[code_length];<br>
	 * u2 exception_table_length;<br>
	 * <br>
	 * u2 start_pc; u2 end_pc; u2 handler_pc; u2 catch_type;<br>
	 * exception_table[exception_table_length]; <br>
	 * u2 attributes_count;<br>
	 * attribute_info attributes[attributes_count]; <br>
	 * </p>
	 * 
	 * @param aDis
	 *            DataInputStream containing the bytes of the class.
	 * @param aCpInfo
	 *            ConstantPool Information.
	 * @param aLocalMethod
	 *            Reference to the current method for which the code is to be
	 *            manipulated.
	 * @throws IOException
	 *             Error in Class Stream of bytes.
	 */
	public static void readCode(Method aLocalMethod, DataInputStream aDis,
			ConstantPool aCpInfo) throws IOException {

		/*
		 * This refers to the Code attribute length. All the attributes start
		 * with the index which has a CONSTANT_Utf8 (2 bytes) and then 4 bytes
		 * denoting length of the attribute [here Code]. Here we just read it to
		 * move the aDis pointer to read and pass the 4 bytes length.
		 */
		int len = aDis.readInt();

		short maxStack = aDis.readShort();
		aLocalMethod.setMaxStack(maxStack);

		short maxLocals = aDis.readShort();
		aLocalMethod.setMaxLocals(maxLocals);

		int codeLen = aDis.readInt();

		byte[] btArray = new byte[codeLen];
		aDis.readFully(btArray);
		aLocalMethod.setBytes(btArray);

		// exception_table_length
		short excLen = aDis.readShort();

		/**
		 * startPc - Offset of start of try/catch range. endPc - Offset of end
		 * of try/catch range. handlerPc - Offset of start of exception handler
		 * code. catchType - Type of exception handled.
		 */
		for (int i = 0; i < excLen; i++) {
			short startPc = aDis.readShort();
			short endPc = aDis.readShort();
			short handlerPc = aDis.readShort();
			short catchType = aDis.readShort();

			// If type of class caught is any, then CatchType is 0.
			aLocalMethod.addExceptionBlock(startPc, endPc, handlerPc, aCpInfo
					.getClassName(catchType));
		}

		short attrCount = aDis.readShort();

		for (int i = 0; i < attrCount; i++) {
			readCodeAttributes(aDis, aCpInfo, aLocalMethod);
		}
	}

	/**
	 * Manipulates the 'SourceFile' attribute of the Fields.
	 * <p>
	 * <b> SourceFile </b> attribute <br>
	 * u2 attribute_name_index; <br>
	 * u4 attribute_length;<br>
	 * u2 sourcefile_index;
	 * </p>
	 * 
	 * @param aDis
	 *            DataInputStream containing the bytes of the class.
	 * @param aCpInfo
	 *            ConstantPool Information.
	 * @return NO_STRING
	 * @throws IOException
	 *             Error in Class Stream of bytes.
	 */
	public static String readSourceFile(DataInputStream aDis,
			ConstantPool aCpInfo) throws IOException {

		aDis.readInt();
		short srcIndex = aDis.readShort();
		String srcName = aCpInfo.getUtf8String(srcIndex);
		return srcName;
	}

	/**
	 * Manipulates the Exceptions attribute.
	 * <p>
	 * <b> Exceptions_attribute </b> <br>
	 * u2 attribute_name_index;<br>
	 * u4 attribute_length;<br>
	 * u2 number_of_exceptions;<br>
	 * u2 exception_index_table[number_of_exceptions]; <br>
	 * 
	 * Present , if there is a throws clause in the declaration of the method.<br>
	 * </p>
	 * 
	 * @param aDis
	 *            DataInputStream containing the bytes of the class.
	 * @param aCpInfo
	 *            ConstantPool Information.
	 * 
	 * @return Set of classes present in the 'throws' clause statement.
	 * @throws IOException
	 *             Error in Class Stream of bytes.
	 */
	public static List<String> readExceptions(DataInputStream aDis,
			ConstantPool aCpInfo) throws IOException {
		// Responsible for the 'throws' clause
		List<String> classes = new ArrayList<String>(2);
		aDis.readInt();
		short numException = aDis.readShort();
		for (int i = 0; i < numException; i++) {
			short classIndex = aDis.readShort();
			classes.add(aCpInfo.getClassName(classIndex));
		}
		return classes;
	}

	/**
	 * Reads the possible attributes of Code.
	 * <p>
	 * Possible attributes of Code are <b> LineNumberTable </b> and <b>
	 * LocalVariableTable </b>
	 * </p>
	 * 
	 * @param aDis
	 *            DataInputStream containing the bytes of the class.
	 * @param aCpInfo
	 *            ConstantPool Information.
	 * @throws IOException
	 *             Error in Class Stream of bytes.
	 */
	private static void readCodeAttributes(DataInputStream aDis,
			ConstantPool aCpInfo, Method method) throws IOException {

		short attrNameIndex = aDis.readShort();
		String attrName = aCpInfo.getUtf8String(attrNameIndex);

		if (attrName.equals(JVMConstants.ATTRIBUTE_LINENUMBERTABLE)) {
			readLineNumberTable(aDis);
		} else if (attrName.equals(JVMConstants.ATTRIBUTE_LOCALVARIABLETABLE)) {
			readLocalVariableTable(aDis, aCpInfo, method);
		}
	}

	/**
	 * Manipulates the LineNumberTable attribute.
	 * <p>
	 * <b> LineNumberTable_attribute </b> <br>
	 * u2 attribute_name_index; <br>
	 * u4 attribute_length; <br>
	 * u2 line_number_table_length; { <br>
	 * u2 start_pc; <br>
	 * u2 line_number;<br>
	 * line_number_table[line_number_table_length]; <br>
	 * </p>
	 * 
	 * @param aDis
	 *            DataInputStream containing the bytes of the class.
	 * @throws IOException
	 *             Error in Class Stream of bytes.
	 */
	private static void readLineNumberTable(DataInputStream aDis)
			throws IOException {
		int len = aDis.readInt();
		byte[] btRead = new byte[len];
		// TODO - New: Do some manipulation with the LineNumberTable
		// attribute..
		aDis.readFully(btRead);
		btRead = null;
	}

	/**
	 * Manipulates the LocalVariableTable attribute.
	 * <p>
	 * LocalVariableTable_attribute { u2 attribute_name_index; u4
	 * attribute_length; u2 local_variable_table_length; { u2 start_pc; u2
	 * length; u2 name_index; u2 descriptor_index; u2 index; }
	 * local_variable_table[local_variable_table_length]; }
	 * 
	 * @param aDis
	 *            DataInputStream containing the bytes of the class.
	 * @throws IOException
	 *             Error in Class Stream of bytes.
	 * 
	 */
	private static void readLocalVariableTable(DataInputStream aDis,
			ConstantPool constPool, Method method) throws IOException {
		int len = aDis.readInt();

		LocalVariableTable localVarTable = new LocalVariableTable(method
				.isStatic());

		short localVarArrLen = aDis.readShort();
		for (int ctr = 1; ctr <= localVarArrLen; ctr++) {
			short startPc = aDis.readShort();
			short length = aDis.readShort();
			short nameIndex = aDis.readShort();
			short descIndex = aDis.readShort();
			short frameIndex = aDis.readShort();

			localVarTable.addLocalVariable(startPc, length, nameIndex,
					descIndex, constPool.getEntryValue(nameIndex), constPool
							.getEntryValue(descIndex), frameIndex);

			String jvmType = constPool.getEntryValue(descIndex);
			if (TypeInferrer.doesTypeOccupy2EntriesInVariableTable(jvmType)) {
				// Add another entry.
				localVarTable.addLocalVariable(startPc, length, nameIndex,
						descIndex, constPool.getEntryValue(nameIndex),
						constPool.getEntryValue(descIndex),
						(short) (frameIndex + 1));

			}

		}

		method.setVariableTable(localVarTable);

	}

	private static final Logger logger = CustomLoggerFactory.createLogger();

}
