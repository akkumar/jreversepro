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
 **/
package net.sf.jrevpro.gui;

import javax.swing.plaf.metal.*;
import javax.swing.plaf.*;

/**
 * My Favourite Theme
 */
public class MyFavTheme extends DefaultMetalTheme {
  // Green Color

  private final ColorUIResource primary1 = new ColorUIResource(51, 102, 51);
  private final ColorUIResource primary2 = new ColorUIResource(102, 153, 102);
  private final ColorUIResource primary3 = new ColorUIResource(153, 204, 153);

  protected ColorUIResource getPrimary1() {
    return primary1;
  }

  protected ColorUIResource getPrimary2() {
    return primary2;
  }

  protected ColorUIResource getPrimary3() {
    return primary3;
  }
}