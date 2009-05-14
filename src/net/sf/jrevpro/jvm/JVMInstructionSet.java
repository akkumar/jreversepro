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
package net.sf.jrevpro.jvm;

import net.sf.jrevpro.jls.JLSConstants;
import net.sf.jrevpro.reflect.instruction.Instruction;

/**
 * This contains the instruction set of the JVM.
 * 
 * @author Karthik Kumar
 */
public class JVMInstructionSet implements Opcodes {

	/**
	 * Initializes thr JVM Opcode set.
	 */
	static {
		sOpCode = new String[256];
		sLength = new int[256];

		assign0();
		assign1();
		assign2();
		assign3();
		assign4();

		assign5();
		assign6();
		assign7();
		assign8();
		assign9();

		assign10();
		assign11();
		assign12();
		assign13();
		assign14();
	}

	/**
	 * Returns the OpCode string.
	 * 
	 * @param opcode
	 *            Index of the Opcode
	 * @return Returns the OpCode string corresponding to the opcode aIndex.
	 */
	public static final String getOpcodeString(int opcode) {
		return sOpCode[opcode];
	}

	/**
	 * Returns the OpCode Length.
	 * 
	 * @param index
	 *            Index of the Opcode
	 * @param wide
	 *            Wide instruction.
	 * @return Returns the Length of the JVM Instruction with this opcode.
	 */
	public static final int getOpcodeLength(int index, boolean wide) {
		// wide instructions
		switch (index) {
		case OPCODE_ILOAD:
		case OPCODE_LLOAD:
		case OPCODE_FLOAD:
		case OPCODE_DLOAD:
		case OPCODE_ALOAD:
		case OPCODE_ISTORE:
		case OPCODE_LSTORE:
		case OPCODE_FSTORE:
		case OPCODE_DSTORE:
		case OPCODE_ASTORE:
		case OPCODE_RET:
			if (wide) {
				return sLength[index] + 1;
			}
			break;
		case OPCODE_IINC:
			if (wide) {
				return sLength[index] + 2;
			}
			break;
		}
		return sLength[index];
	}

	/**
	 * Assigns opcode information for opcodes 0-15.
	 */
	private static void assign0() {

		sOpCode[OPCODE_NOP] = "nop";
		sLength[OPCODE_NOP] = 1;

		sOpCode[OPCODE_ACONST_NULL] = "aconst_null";
		sLength[OPCODE_ACONST_NULL] = 1;

		sOpCode[OPCODE_ICONST_M1] = "iconst_ml";
		sLength[OPCODE_ICONST_M1] = 1;

		sOpCode[OPCODE_ICONST_0] = "iconst_0";
		sLength[OPCODE_ICONST_0] = 1;

		sOpCode[OPCODE_ICONST_1] = "iconst_1";
		sLength[OPCODE_ICONST_1] = 1;

		sOpCode[OPCODE_ICONST_2] = "iconst_2";
		sLength[OPCODE_ICONST_2] = 1;

		sOpCode[OPCODE_ICONST_3] = "iconst_3";
		sLength[OPCODE_ICONST_3] = 1;

		sOpCode[OPCODE_ICONST_4] = "iconst_4";
		sLength[OPCODE_ICONST_4] = 1;

		sOpCode[OPCODE_ICONST_5] = "iconst_5";
		sLength[OPCODE_ICONST_5] = 1;

		sOpCode[OPCODE_LCONST_0] = "lconst_0";
		sLength[OPCODE_LCONST_0] = 1;

		sOpCode[OPCODE_LCONST_1] = "lconst_1";
		sLength[OPCODE_LCONST_1] = 1;

		sOpCode[OPCODE_FCONST_0] = "fconst_0";
		sLength[OPCODE_FCONST_0] = 1;

		sOpCode[OPCODE_FCONST_1] = "fconst_1";
		sLength[OPCODE_FCONST_1] = 1;

		sOpCode[OPCODE_FCONST_2] = "fconst_2";
		sLength[OPCODE_FCONST_2] = 1;

		sOpCode[OPCODE_DCONST_0] = "dconst_0";
		sLength[OPCODE_DCONST_0] = 1;

		sOpCode[OPCODE_DCONST_1] = "dconst_1";
		sLength[OPCODE_DCONST_1] = 1;
	}

	/**
	 * Assigns opcode information for opcodes 16-31
	 */
	private static void assign1() {

		sOpCode[OPCODE_BIPUSH] = "bipush";
		sLength[OPCODE_BIPUSH] = 2;

		sOpCode[OPCODE_SIPUSH] = "sipush";
		sLength[OPCODE_SIPUSH] = 3;

		sOpCode[OPCODE_LDC] = "ldc";
		sLength[OPCODE_LDC] = 2;

		sOpCode[OPCODE_LDC_W] = "ldc_w";
		sLength[OPCODE_LDC_W] = 3;

		sOpCode[OPCODE_LDC2_W] = "ldc2_w";
		sLength[OPCODE_LDC2_W] = 3;

		sOpCode[OPCODE_ILOAD] = "iload";
		sLength[OPCODE_ILOAD] = 2;

		sOpCode[OPCODE_LLOAD] = "lload";
		sLength[OPCODE_LLOAD] = 2;

		sOpCode[OPCODE_FLOAD] = "fload";
		sLength[OPCODE_FLOAD] = 2;

		sOpCode[OPCODE_DLOAD] = "dload";
		sLength[OPCODE_DLOAD] = 2;

		sOpCode[OPCODE_ALOAD] = "aload";
		sLength[OPCODE_ALOAD] = 2;

		sOpCode[OPCODE_ILOAD_0] = "iload_0";
		sLength[OPCODE_ILOAD_0] = 1;

		sOpCode[OPCODE_ILOAD_1] = "iload_1";
		sLength[OPCODE_ILOAD_1] = 1;

		sOpCode[OPCODE_ILOAD_2] = "iload_2";
		sLength[OPCODE_ILOAD_2] = 1;

		sOpCode[OPCODE_ILOAD_3] = "iload_3";
		sLength[OPCODE_ILOAD_3] = 1;

		sOpCode[OPCODE_LLOAD_0] = "lload_0";
		sLength[OPCODE_LLOAD_0] = 1;

		sOpCode[OPCODE_LLOAD_1] = "lload_1";
		sLength[OPCODE_LLOAD_1] = 1;
	}

	/**
	 * Assigns opcode information for opcodes 32-47
	 */
	private static void assign2() {

		sOpCode[OPCODE_LLOAD_2] = "lload_2";
		sLength[OPCODE_LLOAD_2] = 1;

		sOpCode[OPCODE_LLOAD_3] = "lload_3";
		sLength[OPCODE_LLOAD_3] = 1;

		sOpCode[OPCODE_FLOAD_0] = "fload_0";
		sLength[OPCODE_FLOAD_0] = 1;

		sOpCode[OPCODE_FLOAD_1] = "fload_1";
		sLength[OPCODE_FLOAD_1] = 1;

		sOpCode[OPCODE_FLOAD_2] = "fload_2";
		sLength[OPCODE_FLOAD_2] = 1;

		sOpCode[OPCODE_FLOAD_3] = "fload_3";
		sLength[OPCODE_FLOAD_3] = 1;

		sOpCode[OPCODE_DLOAD_0] = "dload_0";
		sLength[OPCODE_DLOAD_0] = 1;

		sOpCode[OPCODE_DLOAD_1] = "dload_1";
		sLength[OPCODE_DLOAD_1] = 1;

		sOpCode[OPCODE_DLOAD_2] = "dload_2";
		sLength[OPCODE_DLOAD_2] = 1;

		sOpCode[OPCODE_DLOAD_3] = "dload_3";
		sLength[OPCODE_DLOAD_3] = 1;

		sOpCode[OPCODE_ALOAD_0] = "aload_0";
		sLength[OPCODE_ALOAD_0] = 1;

		sOpCode[OPCODE_ALOAD_1] = "aload_1";
		sLength[OPCODE_ALOAD_1] = 1;

		sOpCode[OPCODE_ALOAD_2] = "aload_2";
		sLength[OPCODE_ALOAD_2] = 1;

		sOpCode[OPCODE_ALOAD_3] = "aload_3";
		sLength[OPCODE_ALOAD_3] = 1;

		sOpCode[OPCODE_IALOAD] = "iaload";
		sLength[OPCODE_IALOAD] = 1;

		sOpCode[OPCODE_LALOAD] = "laload";
		sLength[OPCODE_LALOAD] = 1;
	}

	/**
	 * Assigns opcode information for opcodes 48-63
	 */
	private static void assign3() {

		sOpCode[OPCODE_FALOAD] = "faload";
		sLength[OPCODE_FALOAD] = 1;

		// Load from array
		sOpCode[OPCODE_DALOAD] = "daload";
		sLength[OPCODE_DALOAD] = 1;

		sOpCode[OPCODE_AALOAD] = "aaload";
		sLength[OPCODE_AALOAD] = 1;

		sOpCode[OPCODE_BALOAD] = "baload";
		sLength[OPCODE_BALOAD] = 1;

		sOpCode[OPCODE_CALOAD] = "caload";
		sLength[OPCODE_CALOAD] = 1;

		sOpCode[OPCODE_SALOAD] = "saload";
		sLength[OPCODE_SALOAD] = 1;

		// Store int to a local variable
		sOpCode[OPCODE_ISTORE] = "istore";
		sLength[OPCODE_ISTORE] = 2;

		sOpCode[OPCODE_LSTORE] = "lstore";
		sLength[OPCODE_LSTORE] = 2;

		sOpCode[OPCODE_FSTORE] = "fstore";
		sLength[OPCODE_FSTORE] = 2;

		sOpCode[OPCODE_DSTORE] = "dstore";
		sLength[OPCODE_DSTORE] = 2;

		sOpCode[OPCODE_ASTORE] = "astore";
		sLength[OPCODE_ASTORE] = 2;

		sOpCode[OPCODE_ISTORE_0] = "istore_0";
		sLength[OPCODE_ISTORE_0] = 1;

		sOpCode[OPCODE_ISTORE_1] = "istore_1";
		sLength[OPCODE_ISTORE_1] = 1;

		sOpCode[OPCODE_ISTORE_2] = "istore_2";
		sLength[OPCODE_ISTORE_2] = 1;

		sOpCode[OPCODE_ISTORE_3] = "istore_3";
		sLength[OPCODE_ISTORE_3] = 1;

		sOpCode[OPCODE_LSTORE_0] = "lstore_0";
		sLength[OPCODE_LSTORE_0] = 1;
	}

	/**
	 * Assigns opcode information for opcodes 64-79
	 */
	private static void assign4() {
		int l = 64;

		sOpCode[l] = "lstore_1";
		sLength[l] = 1;

		// Load from array
		sOpCode[l + 1] = "lstore_2";
		sLength[l + 1] = 1;

		sOpCode[l + 2] = "lstore_3";
		sLength[l + 2] = 1;

		sOpCode[l + 3] = "fstore_0";
		sLength[l + 3] = 1;

		sOpCode[l + 4] = "fstore_1";
		sLength[l + 4] = 1;

		sOpCode[l + 5] = "fstore_2";
		sLength[l + 5] = 1;

		// Store int to a local variable
		sOpCode[l + 6] = "fstore_3";
		sLength[l + 6] = 1;

		sOpCode[l + 7] = "dstore_0";
		sLength[l + 7] = 1;

		sOpCode[l + 8] = "dstore_1";
		sLength[l + 8] = 1;

		sOpCode[l + 9] = "dstore_2";
		sLength[l + 9] = 1;

		sOpCode[l + 10] = "dstore_3";
		sLength[l + 10] = 1;

		sOpCode[l + 11] = "astore_0";
		sLength[l + 11] = 1;

		sOpCode[l + 12] = "astore_1";
		sLength[l + 12] = 1;

		sOpCode[l + 13] = "astore_2";
		sLength[l + 13] = 1;

		sOpCode[l + 14] = "astore_3";
		sLength[l + 14] = 1;

		// Store into array
		sOpCode[l + 15] = "iastore";
		sLength[l + 15] = 1;
	}

	/**
	 * Assigns opcode information for opcodes 80-95
	 */
	private static void assign5() {
		int l = 80;

		sOpCode[l] = "lastore";
		sLength[l] = 1;

		// Store to array
		sOpCode[l + 1] = "fastore";
		sLength[l + 1] = 1;

		sOpCode[l + 2] = "dastore";
		sLength[l + 2] = 1;

		sOpCode[l + 3] = "aastore";
		sLength[l + 3] = 1;

		sOpCode[l + 4] = "bastore";
		sLength[l + 4] = 1;

		sOpCode[l + 5] = "castore";
		sLength[l + 5] = 1;

		sOpCode[l + 6] = "sastore";
		sLength[l + 6] = 1;

		sOpCode[l + 7] = "pop";
		sLength[l + 7] = 1;

		sOpCode[l + 8] = "pop2";
		sLength[l + 8] = 1;

		sOpCode[l + 9] = "dup";
		sLength[l + 9] = 1;

		sOpCode[l + 10] = "dup_x1";
		sLength[l + 10] = 1;

		sOpCode[l + 11] = "dup_x2";
		sLength[l + 11] = 1;

		sOpCode[l + 12] = "dup2";
		sLength[l + 12] = 1;

		sOpCode[l + 13] = "dup2_x1";
		sLength[l + 13] = 1;

		sOpCode[l + 14] = "dup2_x2";
		sLength[l + 14] = 1;

		sOpCode[l + 15] = "swap";
		sLength[l + 15] = 1;
	}

	/**
	 * Assigns opcode information for opcodes 96-111.
	 */
	private static void assign6() {
		int l = 96;

		sOpCode[l] = "iadd";
		sLength[l] = 1;

		sOpCode[l + 1] = "ladd";
		sLength[l + 1] = 1;

		sOpCode[l + 2] = "fadd";
		sLength[l + 2] = 1;

		sOpCode[l + 3] = "dadd";
		sLength[l + 3] = 1;

		sOpCode[l + 4] = "isub";
		sLength[l + 4] = 1;

		sOpCode[l + 5] = "lsub";
		sLength[l + 5] = 1;

		sOpCode[l + 6] = "fsub";
		sLength[l + 6] = 1;

		sOpCode[l + 7] = "dsub";
		sLength[l + 7] = 1;

		sOpCode[l + 8] = "imul";
		sLength[l + 8] = 1;

		sOpCode[l + 9] = "lmul";
		sLength[l + 9] = 1;

		sOpCode[l + 10] = "fmul";
		sLength[l + 10] = 1;

		sOpCode[l + 11] = "dmul";
		sLength[l + 11] = 1;

		sOpCode[l + 12] = "idiv";
		sLength[l + 12] = 1;

		sOpCode[l + 13] = "ldiv";
		sLength[l + 13] = 1;

		sOpCode[l + 14] = "fdiv";
		sLength[l + 14] = 1;

		sOpCode[l + 15] = "ddiv";
		sLength[l + 15] = 1;
	}

	/**
	 * Assigns opcode information for opcodes 112-127.
	 */
	private static void assign7() {
		int l = 112;

		// Integer remainder
		sOpCode[l] = "irem";
		sLength[l] = 1;

		sOpCode[l + 1] = "lrem";
		sLength[l + 1] = 1;

		sOpCode[l + 2] = "frem";
		sLength[l + 2] = 1;

		sOpCode[l + 3] = "drem";
		sLength[l + 3] = 1;

		// Negate int
		sOpCode[l + 4] = "ineg";
		sLength[l + 4] = 1;

		sOpCode[l + 5] = "lneg";
		sLength[l + 5] = 1;

		sOpCode[l + 6] = "fneg";
		sLength[l + 6] = 1;

		sOpCode[l + 7] = "dneg";
		sLength[l + 7] = 1;

		// Shift left
		sOpCode[l + 8] = "ishl";
		sLength[l + 8] = 1;

		sOpCode[l + 9] = "lshl";
		sLength[l + 9] = 1;

		// Shift right
		sOpCode[l + 10] = "ishr";
		sLength[l + 10] = 1;

		sOpCode[l + 11] = "lshr";
		sLength[l + 11] = 1;

		// Logical shift right
		sOpCode[l + 12] = "iushr";
		sLength[l + 12] = 1;

		sOpCode[l + 13] = "lushr";
		sLength[l + 13] = 1;

		// Boolean AND int
		sOpCode[l + 14] = "iand";
		sLength[l + 14] = 1;

		sOpCode[l + 15] = "land";
		sLength[l + 15] = 1;
	}

	/**
	 * Assigns opcode information for opcodes 128-143.
	 */
	private static void assign8() {
		int l = 128;

		// OR int
		sOpCode[l] = "ior";
		sLength[l] = 1;

		sOpCode[l + 1] = "lor";
		sLength[l + 1] = 1;

		// XOR op.
		sOpCode[l + 2] = "ixor";
		sLength[l + 2] = 1;

		sOpCode[l + 3] = "lxor";
		sLength[l + 3] = 1;

		// Increment local variable
		sOpCode[l + 4] = "iinc";
		sLength[l + 4] = 3;

		// Conversions
		sOpCode[l + 5] = "i2l";
		sLength[l + 5] = 1;

		sOpCode[l + 6] = "i2f";
		sLength[l + 6] = 1;

		sOpCode[l + 7] = "i2d";
		sLength[l + 7] = 1;

		sOpCode[l + 8] = "l2i";
		sLength[l + 8] = 1;

		sOpCode[l + 9] = "l2f";
		sLength[l + 9] = 1;

		sOpCode[l + 10] = "l2d";
		sLength[l + 10] = 1;

		sOpCode[l + 11] = "f2i";
		sLength[l + 11] = 1;

		sOpCode[l + 12] = "f2l";
		sLength[l + 12] = 1;

		sOpCode[l + 13] = "f2d";
		sLength[l + 13] = 1;

		sOpCode[l + 14] = "d2i";
		sLength[l + 14] = 1;

		sOpCode[l + 15] = "d2l";
		sLength[l + 15] = 1;
	}

	/**
	 * Assigns opcode information for opcodes 144-159.
	 */
	private static void assign9() {
		int l = 144;

		// Conversions
		sOpCode[l] = "d2f";
		sLength[l] = 1;

		sOpCode[l + 1] = "i2b";
		sLength[l + 1] = 1;

		sOpCode[l + 2] = "i2c";
		sLength[l + 2] = 1;

		sOpCode[l + 3] = "i2s";
		sLength[l + 3] = 1;

		// Compare
		sOpCode[l + 4] = "lcmp";
		sLength[l + 4] = 1;

		sOpCode[l + 5] = "fcmpl";
		sLength[l + 5] = 1;

		sOpCode[l + 6] = "fcmpg";
		sLength[l + 6] = 1;

		sOpCode[l + 7] = "dcmpl";
		sLength[l + 7] = 1;

		sOpCode[l + 8] = "dcmpg";
		sLength[l + 8] = 1;

		sOpCode[l + 9] = "ifeq";
		sLength[l + 9] = 3;

		sOpCode[l + 10] = "ifne";
		sLength[l + 10] = 3;

		sOpCode[l + 11] = "iflt";
		sLength[l + 11] = 3;

		sOpCode[l + 12] = "ifge";
		sLength[l + 12] = 3;

		sOpCode[l + 13] = "ifgt";
		sLength[l + 13] = 3;

		sOpCode[l + 14] = "ifle";
		sLength[l + 14] = 3;

		sOpCode[l + 15] = "if_icmpeq";
		sLength[l + 15] = 3;
	}

	/**
	 * Assigns opcode information for opcodes 160-175
	 */
	private static void assign10() {
		int l = 160;

		sOpCode[l] = "if_icmpne";
		sLength[l] = 3;

		sOpCode[l + 1] = "if_icmplt";
		sLength[l + 1] = 3;

		sOpCode[l + 2] = "if_icmpge";
		sLength[l + 2] = 3;

		sOpCode[l + 3] = "if_cmpgt";
		sLength[l + 3] = 3;

		sOpCode[l + 4] = "if_icmple";
		sLength[l + 4] = 3;

		sOpCode[l + 5] = "if_acmpeq";
		sLength[l + 5] = 3;

		sOpCode[l + 6] = "if_acmpne";
		sLength[l + 6] = 3;

		sOpCode[l + 7] = "goto";
		sLength[l + 7] = 3;

		sOpCode[l + 8] = "jsr";
		sLength[l + 8] = 3;

		sOpCode[l + 9] = "ret";
		sLength[l + 9] = 2;

		sOpCode[l + 10] = "tableswitch";
		sLength[l + 10] = TABLE_LEN;
		// Variable size instruction - 170

		sOpCode[l + 11] = "lookupswitch";
		sLength[l + 11] = LOOKUP_LEN;
		// Variable size instruction - 171

		sOpCode[l + 12] = "ireturn";
		sLength[l + 12] = 1;

		sOpCode[l + 13] = "lreturn";
		sLength[l + 13] = 1;

		sOpCode[l + 14] = "freturn";
		sLength[l + 14] = 1;

		sOpCode[l + 15] = "dreturn";
		sLength[l + 15] = 1;
	}

	/**
	 * Assigns opcode information for opcodes 176-191
	 */
	private static void assign11() {
		int l = 176;

		sOpCode[l] = "areturn";
		sLength[l] = 1;

		sOpCode[l + 1] = "return";
		sLength[l + 1] = 1;

		sOpCode[l + 2] = "getstatic";
		sLength[l + 2] = 3;

		sOpCode[l + 3] = "putstatic";
		sLength[l + 3] = 3;

		sOpCode[l + 4] = "getfield";
		sLength[l + 4] = 3;

		sOpCode[l + 5] = "putfield";
		sLength[l + 5] = 3;

		sOpCode[l + 6] = "invokevirtual";
		sLength[l + 6] = 3;

		sOpCode[l + 7] = "invokespecial";
		sLength[l + 7] = 3;

		sOpCode[l + 8] = "invokestatic";
		sLength[l + 8] = 3;

		sOpCode[l + 9] = "invokeinterface";
		sLength[l + 9] = 5;

		sOpCode[l + 10] = "xxxunusedxxx";
		sLength[l + 10] = 1;

		sOpCode[l + 11] = "new";
		sLength[l + 11] = 3;

		sOpCode[l + 12] = "newarray";
		sLength[l + 12] = 2;

		sOpCode[l + 13] = "anewarray";
		sLength[l + 13] = 3;

		// Get length
		sOpCode[l + 14] = "arraylength";
		sLength[l + 14] = 1;

		// Throw exception or error
		sOpCode[l + 15] = "athrow";
		sLength[l + 15] = 1;
	}

	/**
	 * Assigns opcode information for opcodes 192-207
	 */
	private static void assign12() {
		int l = 192;

		sOpCode[l] = "checkcast";
		sLength[l] = 3;

		sOpCode[l + 1] = "instanceof";
		sLength[l + 1] = 3;

		sOpCode[l + 2] = "monitorenter";
		sLength[l + 2] = 1;

		sOpCode[l + 3] = "monitorexit";
		sLength[l + 3] = 1;

		sOpCode[l + 4] = "wide";
		sLength[l + 4] = 1;

		sOpCode[l + 5] = "multianewarray";
		sLength[l + 5] = 4;

		sOpCode[l + 6] = "ifnull";
		sLength[l + 6] = 3;

		sOpCode[l + 7] = "ifnonnull";
		sLength[l + 7] = 3;

		sOpCode[l + 8] = "goto_w";
		sLength[l + 8] = 5;

		sOpCode[l + 9] = "jsr_w";
		sLength[l + 9] = 5;

		sOpCode[l + 10] = "_quick";
		sLength[l + 10] = 1;

		// Quick instructions
		sOpCode[l + 11] = "ldc_quick";
		sLength[l + 11] = 2;

		sOpCode[l + 12] = "ldc_w_quick";
		sLength[l + 12] = 3;

		sOpCode[l + 13] = "ldc2_w_quick";
		sLength[l + 13] = 3;

		// Get length
		sOpCode[l + 14] = "getfield_quick";
		sLength[l + 14] = 3;

		sOpCode[l + 15] = "putfield_quick";
		sLength[l + 15] = 3;
	}

	/**
	 * Assigns opcode information for opcodes 208-223
	 */
	private static void assign13() {
		int l = 208;

		sOpCode[l] = "getfield2_quick";
		sLength[l] = 3;

		sOpCode[l + 1] = "putfield2_quick";
		sLength[l + 1] = 3;

		sOpCode[l + 2] = "getstatic_quick";
		sLength[l + 2] = 1;

		sOpCode[l + 3] = "putstatic_quick";
		sLength[l + 3] = 1;

		sOpCode[l + 4] = "getstatic2_quick";
		sLength[l + 4] = 3;

		sOpCode[l + 5] = "putstatic2_quick";
		sLength[l + 5] = 4;

		sOpCode[l + 6] = "invokevirtual_quick";
		sLength[l + 6] = 3;

		sOpCode[l + 7] = "invokenonvirtual_quick";
		sLength[l + 7] = 3;

		sOpCode[l + 8] = "invokesuper_quick";
		sLength[l + 8] = 3;

		sOpCode[l + 9] = "invokestatic_quick";
		sLength[l + 9] = 3;

		sOpCode[l + 10] = "invokeinterface_quick";
		sLength[l + 10] = 5;

		sOpCode[l + 11] = "invokevirtualobject_quick";
		sLength[l + 11] = 3;

		sOpCode[l + 12] = "220-Undefined";
		sLength[l + 12] = 1;
		// cafe babe

		sOpCode[l + 13] = "new_quick";
		sLength[l + 13] = 3;

		sOpCode[l + 14] = "anewarray_quick";
		sLength[l + 14] = 3;

		sOpCode[l + 15] = "multianewarray_quick";
		sLength[l + 15] = 4;
	}

	/**
	 * Assigns opcode information for opcodes 224-255.
	 */
	private static void assign14() {
		int l = 224;

		sOpCode[l] = "checkcast_quick";
		sLength[l] = 3;

		sOpCode[l + 1] = "instanceof_quick";
		sLength[l + 1] = 3;

		sOpCode[l + 2] = "invokevirtual_quick_w";
		sLength[l + 2] = 3;

		sOpCode[l + 3] = "getfield_quick_w";
		sLength[l + 3] = 3;

		sOpCode[l + 4] = "putfield_quick_w";
		sLength[l + 4] = 3;

		// Reserved Op-codes
		sOpCode[202] = "breakpoint";
		sLength[202] = 1;

		sOpCode[254] = "impdep1";
		sLength[254] = 1;

		sOpCode[255] = "impdep2";
		sLength[255] = 1;
	}

	/**
	 * Length of the variable length opcode tableswitch
	 */
	public static final int TABLE_LEN = -2;

	/**
	 * Length of the variable length opcode lookupswitch
	 */
	public static final int LOOKUP_LEN = -3;

	/**
	 * sOpCode array contains the sOpCode string for each opcode.
	 */
	private static String[] sOpCode;

	/**
	 * sLength array contains the length of each opcode.
	 */
	private static int[] sLength;

	/**
	 * Private constructor to prevent creation of instance.
	 */
	private JVMInstructionSet() {
	}

	// Not to be instantiated.

	public static boolean isEndOfCatch(int opcode) {
		return opcode == OPCODE_ARETURN || opcode == OPCODE_IRETURN
				|| opcode == OPCODE_LRETURN || opcode == OPCODE_FRETURN
				|| opcode == OPCODE_DRETURN
				|| opcode == OPCODE_RETURN
				|| opcode == OPCODE_POP
				||
				// opcode == OPCODE_GOTO ||
				// opcode == OPCODE_GOTOW ||
				opcode == OPCODE_ATHROW || opcode == OPCODE_JSR
				|| opcode == OPCODE_JSRW;
	}

	/**
	 * Denotes if this instruction invokes some other method or interface or a
	 * constructor.
	 * 
	 * @return Returns true, if this invokes any one mentioned above, false,
	 *         otherwise
	 */
	public static boolean isInvokeIns(int opcode) {
		return opcode == OPCODE_INVOKESPECIAL || opcode == OPCODE_INVOKEVIRTUAL
				|| opcode == OPCODE_INVOKESTATIC
				|| opcode == OPCODE_INVOKEINTERFACE;
	}

	/**
	 * To check if this instruction denotes the corresponding end-of-line in the
	 * source code.
	 * 
	 * @return Returns true, if this denoted end-of-line. false, otherwise.
	 */
	public static boolean isEndOfLine(int opcode) {
		return (opcode >= OPCODE_ISTORE && opcode <= OPCODE_SASTORE)
				|| opcode == OPCODE_IINC || opcode == OPCODE_RETURN
				|| opcode == OPCODE_IRETURN || opcode == OPCODE_LRETURN
				|| opcode == OPCODE_FRETURN || opcode == OPCODE_DRETURN
				|| opcode == OPCODE_ARETURN || opcode == OPCODE_ATHROW
				|| opcode == OPCODE_PUTSTATIC || opcode == OPCODE_PUTFIELD
				|| opcode == OPCODE_POP || opcode == OPCODE_POP2;
	}

	/**
	 * Returns if this instruction is an if instruction or not.
	 * 
	 * @return true, if this is an 'if' instruction. false, otherwise.
	 */
	public static boolean isAnIfIns(int opcode) {
		return (opcode >= OPCODE_IFEQ && opcode <= OPCODE_IF_ACMPNE)
				|| opcode == OPCODE_IFNULL || opcode == OPCODE_IFNONNULL;
	}

	/**
	 * Returns if this instruction is a switch instruction or not.
	 * 
	 * @return true, if switch instruction. false. otherwise
	 */
	public static boolean isASwitchIns(int opcode) {
		return opcode == OPCODE_TABLESWITCH || opcode == OPCODE_LOOKUPSWITCH;
	}

	/**
	 * In case this instruction is a branch instruction on some condition then
	 * the operator corresponding to the operator is returned. For eg, for
	 * OPCODE_IFEQ , = is returned.
	 * 
	 * @return Returns conditional operator for the condition mentioned in the
	 *         opcode. Empty string, if the opcode is not of branch-on-condition
	 *         type.
	 */
	public static String getConditionalOperator(int opcode) {
		switch (opcode) {
		case OPCODE_IFEQ:
		case OPCODE_IF_ICMPEQ:
		case OPCODE_IF_ACMPEQ:
		case OPCODE_IFNULL:
			return JLSConstants.OPR_EQ;
		case OPCODE_IFNE:
		case OPCODE_IF_ICMPNE:
		case OPCODE_IF_ACMPNE:
		case OPCODE_IFNONNULL:
			return JLSConstants.OPR_NE;
		case OPCODE_IFLT:
		case OPCODE_IF_ICMPLT:
			return JLSConstants.OPR_LT;
		case OPCODE_IFGE:
		case OPCODE_IF_ICMPGE:
			return JLSConstants.OPR_GE;
		case OPCODE_IFGT:
		case OPCODE_IF_ICMPGT:
			return JLSConstants.OPR_GT;
		case OPCODE_IFLE:
		case OPCODE_IF_ICMPLE:
			return JLSConstants.OPR_LE;
		default:
			throw new UnsupportedOperationException("Opcode " + opcode
					+ " not supported for conditional operators");
		}
	}

	/**
	 * In case this instruction is a load instruction, ( that is loading a
	 * datatype onto the JVM stack ) , then that variable index is returned. If
	 * no variable is referenced then INVALID_VAR_INDEX is returned.
	 * 
	 * @return index of the variable referred to. INVALID_VAR_INDEX, otherwise.
	 */
	public int referredVariable(Instruction ins) {
		switch (ins.opcode) {
		case OPCODE_ILOAD_0:
		case OPCODE_FLOAD_0:
		case OPCODE_LLOAD_0:
		case OPCODE_DLOAD_0:
		case OPCODE_ALOAD_0:
			return 0;
		case OPCODE_ILOAD_1:
		case OPCODE_FLOAD_1:
		case OPCODE_LLOAD_1:
		case OPCODE_DLOAD_1:
		case OPCODE_ALOAD_1:
			return 1;
		case OPCODE_ILOAD_2:
		case OPCODE_FLOAD_2:
		case OPCODE_LLOAD_2:
		case OPCODE_DLOAD_2:
		case OPCODE_ALOAD_2:
			return 2;
		case OPCODE_ILOAD_3:
		case OPCODE_FLOAD_3:
		case OPCODE_LLOAD_3:
		case OPCODE_DLOAD_3:
		case OPCODE_ALOAD_3:
			return 3;
		case OPCODE_ILOAD:
		case OPCODE_FLOAD:
		case OPCODE_LLOAD:
		case OPCODE_DLOAD:
		case OPCODE_ALOAD:
			return ins.getArgUnsignedWide();
		default:
			return Instruction.INVALID_VAR_INDEX;
		}
	}

	/**
	 * In case this instruction is a store instruction, ( that is popping a
	 * local variable from the JVM stack ) , then that variable index is
	 * returned. If no variable is referenced then INVALID_VAR_INDEX is
	 * returned.
	 * 
	 * @return index of the variable referred to. INVALID_VAR_INDEX, otherwise.
	 */
	public static int isStoreInstruction(Instruction ins) {
		switch (ins.opcode) {
		case OPCODE_ISTORE_0:
		case OPCODE_FSTORE_0:
		case OPCODE_LSTORE_0:
		case OPCODE_DSTORE_0:
		case OPCODE_ASTORE_0:
			return 0;
		case OPCODE_ISTORE_1:
		case OPCODE_FSTORE_1:
		case OPCODE_LSTORE_1:
		case OPCODE_DSTORE_1:
		case OPCODE_ASTORE_1:
			return 1;
		case OPCODE_ISTORE_2:
		case OPCODE_FSTORE_2:
		case OPCODE_LSTORE_2:
		case OPCODE_DSTORE_2:
		case OPCODE_ASTORE_2:
			return 2;
		case OPCODE_ISTORE_3:
		case OPCODE_FSTORE_3:
		case OPCODE_LSTORE_3:
		case OPCODE_DSTORE_3:
		case OPCODE_ASTORE_3:
			return 3;
		case OPCODE_ISTORE:
		case OPCODE_FSTORE:
		case OPCODE_LSTORE:
		case OPCODE_DSTORE:
		case OPCODE_ASTORE:
			return ins.getArgUnsignedWide();
		default:
			return Instruction.INVALID_VAR_INDEX;
		}
	}
}
