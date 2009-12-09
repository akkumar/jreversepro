/**
 * @(#)ConfirmCloseDialog.java
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
/*
 *  ConfirmCloseDialog : Appears if the user wants to close the dialog.
 *
 */
package org.jreversepro.gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * ConfirmCloseDialog is the dialog that appears at the end of application when
 * the user prompts to close the application.
 * 
 * @author Karthik Kumar
 * @version 1.3
 **/
public final class ConfirmCloseDialog {

  /**
   * Prompts the user if (s)he would exit the application.
   * 
   * @param aAppFrame
   *          Application Frame.
   * @return true, if the user chooses to close the app. false, otherwise.
   **/
  public static boolean confirmExit(JFrame aAppFrame) {
    int selectedOption = JOptionPane.showConfirmDialog(aAppFrame,
        "Are you sure you want to exit ?", "Confirm Exit",
        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    return (selectedOption == JOptionPane.YES_OPTION) ;
  }
}