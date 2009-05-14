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
package net.sf.jrevpro.reflect.variabletable;

import net.sf.jrevpro.jvm.Opcodes;

public class SymbolTableLoader implements Opcodes {

	/*
	 * public SymbolTable loadSymbolTable(RuntimeFrame runtimeFrame,
	 * SymbolTableContext symContext, InstructionList list) { SymbolTable
	 * symTable = new SymbolTable(symContext); logger.fine("Loading Symbol Table
	 * **********");
	 *  // RuntimeFrame rtf = new RuntimeFrame(pool, symTable, curMethod
	 * //.getReturnType());
	 * 
	 * OperandStack opStack = new OperandStack();
	 * 
	 * for (Instruction ins : list.getAllInstructions()) { int varIndex =
	 * ins.isStoreInstruction(); String excDataType =
	 * symContext.mapMethodExceptions .get(new Integer(ins.index)); if
	 * (excDataType != null) { symTable.assignDataType(varIndex, excDataType,
	 * ins.index, true);
	 * 
	 * opStack.push(JVMConstants.FOREIGN_OBJ, JVMConstants.FOREIGN_CLASS,
	 * VALUE);
	 *  } else { if (branches.isJSRTarget(ins.index)) {
	 * opStack.push(JVMConstants.FOREIGN_OBJ, JVMConstants.FOREIGN_CLASS,
	 * VALUE); } if (varIndex != Instruction.INVALID_VAR_INDEX) { // Save the
	 * local variable index. symTable.assignDataType(varIndex,
	 * opStack.topDatatype(), ins.index, false); } }
	 * runtimeFrame.operateStack(ins, opStack);
	 *  // Add a referenced count of line numbers int referredVar =
	 * ins.referredVariable(); if (referredVar != Instruction.INVALID_VAR_INDEX) {
	 * logger.fine(ins.toString() + " " + ins.referredVariable());
	 * symTable.addReference(referredVar, opStack.topDatatype(), ins.index); }
	 * 
	 * if (ins.opcode == OPCODE_INVOKEINTERFACE) {
	 * symTable.touchVariable(runtimeFrame.getInvokedObject() .getValue(),
	 * rtf.getInvokedObject().getDatatype()); } else if (ins.opcode ==
	 * OPCODE_GOTO) { branches.addGotoEntry(ins.index, ins.getTargetPc()); }
	 * else if (ins.opcode == OPCODE_JSR) {
	 * branches.addJSRPc(ins.getTargetPc()); } else if (ins.opcode ==
	 * OPCODE_RET) { branches.addRetPc(ins.getNextIndex()); } else if
	 * (ins.opcode == OPCODE_GOTOW) { branches.addGotoEntry(ins.index,
	 * ins.getTargetPcWide()); } else if (ins.opcode == OPCODE_JSRW) {
	 * branches.addJSRPc(ins.getTargetPcWide()); } } opStack = null; //
	 * Helper.log("Loaded Symbol Table **********"); //
	 * Helper.log(symTable.toString());
	 *  }
	 * 
	 * private Logger logger = Logger.getLogger(getClass().getName());
	 * 
	 * 
	 */
}