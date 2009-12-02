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
package net.sf.jrevpro.parser.instruction;

import net.sf.jrevpro.jvm.JVMInstructionSet;
import net.sf.jrevpro.jvm.Opcodes;
import net.sf.jrevpro.reflect.instruction.Instruction;
import net.sf.jrevpro.reflect.instruction.InstructionList;

public class DefaultInstructionListParser implements InstructionListParser {

  /**
   * 
   * This method is responsible for converting the bytecode stream of the method
   * into JVM opcodes. By default all JVM opcodes are of fixed length in all
   * contexts, except some. tableswitch and lookupswitch are basically variable
   * length opcodes and their length primarily depend on the number of switch
   * legs in the source code since they are one composite statement that
   * contains information about all the possible targets.
   */

  public InstructionList parseBytes(byte[] _bytecodes)
      throws InstructionListParserException {
    bytecodes = _bytecodes;
    if (bytecodes == null) {
      throw new InstructionListParserException("bytecodes are null");
    }
    InstructionList il = new InstructionList();

    int maxCode = bytecodes.length;
    int currentPc = 0;
    int nextPc = -1;
    int startPc = -1;
    boolean bWide = false;
    while (currentPc < maxCode) {
      int curOpcode = Instruction.signedToUnsigned(bytecodes[currentPc]);
      startPc = currentPc + 1;
      if (curOpcode == Opcodes.OPCODE_TABLESWITCH) {
        startPc = currentPc + 4 - (currentPc % 4);
        nextPc = parseTableSwitchInstruction(startPc);
      } else if (curOpcode == Opcodes.OPCODE_LOOKUPSWITCH) {
        startPc = currentPc + 4 - (currentPc % 4);
        nextPc = parseLookupSwitchInstruction(startPc);
      } else {
        nextPc = currentPc
            + JVMInstructionSet.getOpcodeLength(curOpcode, bWide);
      }
      Instruction ins = new Instruction(currentPc, curOpcode, getArgArray(
          startPc, nextPc), nextPc, bWide);
      il.add(ins);
      bWide = (curOpcode == Opcodes.OPCODE_WIDE);
      currentPc = nextPc;
    }
    return il;

  }

  /**
   * This method returns the length of the variable instruction tableswitch.
   * 
   * @param index
   *          beginning index of the tableswitch statement into the bytecode
   *          stream of the method. The format is as follows. The first 4 bytes
   *          are for defaultbyte. The next 4 for the lowest value in the case
   *          leg values. The next 4 for the highest value in the case high
   *          values. Then we will have ( high - low ) * 4 bytes - all
   *          containing the target index to be jumped into relative to the
   *          index of the current switch instruction.
   * @return Returns the integer that is Index + length of the instruction so
   *         that the next JVM opcode can proceed from there.
   */
  private int parseTableSwitchInstruction(int index) {
    // Read Default Byte
    index += 4;

    int lowByte = retrieveTargetPc(index);
    index += 4;

    int highByte = retrieveTargetPc(index);
    index += 4;

    index += (4 * (highByte - lowByte + 1));
    return index;
  }

  /**
   * This method returns the length of the variable instruction lookupswitch.
   * 
   * @param index
   *          beginning index of the lookupswitch statement into the bytecode
   *          stream of the method. The format is as follows. The first 4 bytes
   *          are for defaultbyte. The next 4 for the number of pairs of ( case
   *          leg value, target) that will appear as follows, say N. Then there
   *          will be (2 * N) * 4 bytes , ( N pairs of integers ), with each
   *          pair containing the case leg value first and the second integer
   *          letting us know the target of the case leg relative to the index
   *          of the method.
   * @return Returns the integer that is Index + length of the instruction so
   *         that the next JVM opcode can proceed from there.
   */
  private int parseLookupSwitchInstruction(int index) {
    // Read Default Byte
    index += 4;

    int nPairs = retrieveTargetPc(index);
    index += 4;

    index += (8 * nPairs);

    return index;
  }

  /**
   * This method is used to return the signed integer value of the next four
   * bytes, if given an bytecode index,
   * 
   * @param newIndex
   *          Index from which the integer array starts. This is in big-endian
   *          order.
   * @return Returns the integer value.
   */
  private int retrieveTargetPc(int newIndex) {
    int result = 0;
    for (int i = 0; i < 4; i++) {
      int thisByte = Instruction.signedToUnsigned(bytecodes[newIndex + i]);
      result += (int) (Math.pow(256, 3 - i)) * thisByte;
    }
    if (result < 0) {
      return result + 256;
    } else {
      return result;
    }
  }

  /**
   * Given the start index and the end index this method returns a byte array
   * that contain the byte values with the start array value included and end
   * array excluded.
   * 
   * @param start
   *          start index into the byte array.
   * @param end
   *          end index into the byte array.
   * @return Returns a byte array containing the bytes from start to end , end
   *         exclusive and start inclusive.
   */
  private byte[] getArgArray(int start, int end) {
    byte[] result = null;
    if (start < end) {
      result = new byte[end - start];
      for (int i = start; i < end; i++) {
        result[i - start] = bytecodes[i];
      }
    }
    return result;
  }

  private byte[] bytecodes;

}
