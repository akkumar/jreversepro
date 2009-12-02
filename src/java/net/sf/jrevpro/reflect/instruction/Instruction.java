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
package net.sf.jrevpro.reflect.instruction;

import net.sf.jrevpro.jvm.JVMInstructionSet;

/**
 * Abstraction of a JVM Opcode instruction.
 * 
 * @author Karthik Kumar
 */
public class Instruction {

  /**
   * @param rhsIndex
   *          Index of the instruction into the method bytecode array.
   * @param rhsOpcode
   *          Opcode of the JVM instruction.
   * @param rhsArgs
   *          Arguments to the JVM opcodes.
   * @param rhsNext
   *          Index of the nextPc.
   * @param rhsWide
   *          If the previous instruction was a wide instruction.
   * @param listPosition
   *          Position of this instruction in the bytecode array list.
   */
  public Instruction(int rhsIndex, int rhsOpcode, byte[] rhsArgs, int rhsNext,
      boolean rhsWide) {

    currentPc = rhsIndex;
    opcode = rhsOpcode;
    args = rhsArgs;
    nextPc = rhsNext;

    length = 1 + ((rhsArgs == null) ? 0 : rhsArgs.length);
    wide = rhsWide;
  }

  /**
   * 
   * @return Returns the length of the instruction.
   */
  public int getLength() {
    return length;
  }

  /**
   * @return Returns the next instruction index.
   */
  public int getNextIndex() {
    return currentPc + length;
  }

  /**
   * In case this instruction is a jump/branch instruction, this instruction
   * returns the offset mentioned in the two bytes in the argument array. Works
   * whether wide target or not.
   * 
   * @return Returns the offset.
   */
  public int getTargetPc2() {
    if (!wide)
      return getTargetPc();
    else
      return getTargetPcWide();
  }

  /**
   * In case this instruction is a jump/branch instruction, this instruction
   * returns the offset + index mentioned in the two bytes in the argument
   * array.
   * 
   * @return Returns the offset + index.
   */
  public int getTargetPc() {
    return (getOffset() + currentPc);
  }

  /**
   * In case this instruction is a jump/branch instruction, this instruction
   * returns the offset mentioned in the two bytes in the argument array.
   * 
   * @return Returns the offset.
   */
  public int getOffset() {
    return getArgShort() & 0xffff;
  }

  /**
   * In case this instruction is a jump/branch instruction, this instruction
   * returns the offset mentioned in the two bytes in the argument array. Also
   * important to note is the fact this is a 'wide' variant of the conventional
   * jump instructions.
   * 
   * @return Returns the offset.
   */
  public int getTargetPcWide() {
    int result = (getArgInt() + currentPc) & 0xffff;
    return result;
  }

  /**
   * @return unsigned Arg Wide.
   */
  public int getArgUnsignedWide() {
    return getArgUnsignedWide(0);
  }

  /**
   * @param pos
   *          Position from which bytes are to be taken from stream and the
   *          value to be written.
   * @return unsigned arg wide.
   */
  public int getArgUnsignedWide(int pos) {
    if (wide) {
      if (pos != 0) {
        return getArgUnsignedShort(pos * 2);
      } else {
        return getArgUnsignedShort(0);
      }
    } else {
      return getArgUnsignedByte(pos);
    }
  }

  /**
   * @return unsigned byte.
   */
  public int getArgUnsignedByte() {
    return getArgUnsignedByte(0);
  }

  /**
   * @param pos
   *          Position from which bytes are to be taken from stream and the
   *          value to be written.
   * @return unsigned byte.
   */
  public int getArgUnsignedByte(int pos) {
    return Instruction.signedToUnsigned(args[pos]);
  }

  public int getArgUnsignedShort() {
    return getArgUnsignedShort(0);
  }

  public final int getArgUnsignedShort(int pos) {
    int byte1 = Instruction.signedToUnsigned(args[pos]);
    int byte2 = Instruction.signedToUnsigned(args[pos + 1]);
    int result = (byte1 << 8) | byte2;
    return result;
  }

  public int getArgUnsignedInt() {
    return getArgUnsignedInt(0);
  }

  public int getArgUnsignedInt(int pos) {
    int byte1 = Instruction.signedToUnsigned(args[pos]);
    int byte2 = Instruction.signedToUnsigned(args[pos + 1]);
    int byte3 = Instruction.signedToUnsigned(args[pos + 2]);
    int byte4 = Instruction.signedToUnsigned(args[pos + 3]);
    int result = (byte1 << 24) | (byte2 << 16) | (byte3 << 8) | byte4;
    return result;
  }

  public int getArgWide() {
    return getArgWide(0);
  }

  public int getArgWide(int pos) {
    if (wide) {
      if (pos != 0) {
        return getArgShort(pos * 2);
      } else {
        return getArgShort(0);
      }
    } else {
      return getArgByte(pos);
    }
  }

  public int getArgByte() {
    return getArgByte(0);
  }

  public int getArgByte(int pos) {
    return args[pos];
  }

  public int getArgShort() {
    return getArgShort(0);
  }

  public int getArgShort(int pos) {
    int byte1 = args[pos];
    int byte2 = Instruction.signedToUnsigned(args[pos + 1]);
    int result = (byte1 << 8) | byte2;
    return result;
  }

  public int getArgInt() {
    return getArgInt(0);
  }

  public int getArgInt(int pos) {
    int byte1 = args[pos];
    int byte2 = Instruction.signedToUnsigned(args[pos + 1]);
    int byte3 = Instruction.signedToUnsigned(args[pos + 2]);
    int byte4 = Instruction.signedToUnsigned(args[pos + 3]);
    int result = (byte1 << 24) | (byte2 << 16) | (byte3 << 8) | byte4;
    return result;
  }

  /**
   * Stringified form of Instruction
   * 
   * @return String representation of Instruction.
   */
  public String toString() {
    StringBuilder sb = new StringBuilder("");
    sb.append(" " + currentPc + ": ");
    sb.append(JVMInstructionSet.getOpcodeString(opcode));
    if (args == null) {
      return sb.toString();
    }
    sb.append("[");
    for (int i = 0; i < args.length; i++) {
      sb.append(" " + getArgUnsignedByte(i));
    }
    sb.append("]");
    return sb.toString();
  }

  /**
   * Converts a signed 'byte' to an unsigned integer.
   * <p>
   * 
   * @param aByteVal
   *          a Byte Value.
   * @return unsigned integer equivalent of aByteVal.
   */
  public static int signedToUnsigned(int aByteVal) {
    return (aByteVal < 0) ? (aByteVal += 256) : aByteVal;
  }

  /**
   * Index of this instruction onto the byte array of the method to be
   * decompiled. zero-based index.
   */
  public int currentPc;

  /**
   * opcode of the JVM instruction.
   */
  public int opcode;

  /**
   * Program Counter of the next instruction to be on the byte array queue.
   */
  public int nextPc;

  /**
   * Arguments to the current instruction, excluding opcode
   */
  public byte[] args;

  /**
   * Length of the instruction in bytes.
   */
  public int length;

  /**
   * If this is a wide instruction.
   */
  public boolean wide;

  /**
   * Invalid variable index.
   */
  public static final int INVALID_VAR_INDEX = -1;

}
