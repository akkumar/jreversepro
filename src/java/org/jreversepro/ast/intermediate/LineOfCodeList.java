/**
 *  @(#) StatementList.java
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
 **/
package org.jreversepro.ast.intermediate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akkumar
 * 
 */
public class LineOfCodeList {

  public LineOfCodeList() {
    statements = new ArrayList<AbstractLineOfCode>();
  }

  public void append(AbstractLineOfCode ref) {
    statements.add(ref);
  }

  public List<AbstractLineOfCode> getAsList() {
    return statements;
  }

  private List<AbstractLineOfCode> statements;
}
