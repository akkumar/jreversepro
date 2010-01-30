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
package org.jreversepro.decompile;

import java.util.logging.Logger;

import org.jreversepro.CustomLoggerFactory;
import org.jreversepro.parser.instruction.InstructionListParserException;
import org.jreversepro.parser.instruction.InstructionListParserFactory;
import org.jreversepro.reflect.ConstantPool;
import org.jreversepro.reflect.Method;
import org.jreversepro.reflect.instruction.InstructionList;


public class DecompilationContext {

  Method method;

  ConstantPool constantPool;

  InstructionList list;

  private static final Logger logger = CustomLoggerFactory.createLogger();
  
  public DecompilationContext(Method _method, ConstantPool _constantPool) {
    method = _method;
    constantPool = _constantPool;

    try {
      list = InstructionListParserFactory.createInstructionListParser()
          .parseBytes(method.getBytes());
    } catch (InstructionListParserException e) {
      logger.severe(e.toString());
    }

  }



}
