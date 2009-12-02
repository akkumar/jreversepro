/*
 * @(#)BranchConstants.java
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
 */
package net.sf.jrevpro.decompile.simulate;

/**
 * This interface contains the constants used by branch types.
 * 
 * @author Karthik Kumar
 */
public interface BranchConstants {

  /**
   * Invalid Type.
   */
  int TYPE_INVALID = 0;

  /**
   * A goto branch.
   */
  int TYPE_GOTO = 1;

  /**
   * A conditinal branch.
   */
  int TYPE_BRANCH = 2;

  /**
   * Branch of Jump Sub Routine type.
   */
  int TYPE_JSR = 3;

  /**
   * Branch signifying return type.
   */
  int TYPE_RET = 4;

  /**
   * If branch.
   */
  int TYPE_IF = 10;

  /**
   * Else branch.
   */
  int TYPE_ELSE = 11;

  /**
   * Else..If branch.
   */
  int TYPE_ELSE_IF = 12;

  /**
   * while branch.
   */
  int TYPE_WHILE = 13;

  /**
   * Do..while branch
   */
  int TYPE_DO_WHILE = 14;

  /**
   * try branch.
   */
  int TYPE_TRY = 15;

  /**
   * try branch that contains one implicit catch any block. for synchronized and
   * finally these kind of branches appear.
   */
  int TYPE_TRY_ANY = 16;

  /**
   * Branch signifying catch block
   */
  int TYPE_CATCH = 17;

  /**
   * Branch signifying catch block whose catch datatype / handler type is 'ANY'.
   */
  int TYPE_CATCH_ANY = 18;

  /**
   * Branch signifying 'synchronized' block.
   */
  int TYPE_SYNC = 19;

  /**
   * Branch signifiying switch block.
   */
  int TYPE_SWITCH = 20;

  /**
   * Branch signifiying case block
   */
  int TYPE_CASE = 21;
}
