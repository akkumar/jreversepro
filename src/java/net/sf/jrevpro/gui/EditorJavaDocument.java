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

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;

/**
 * JJavaDocumentEdit
 */
@SuppressWarnings("serial")
public class EditorJavaDocument extends JTextArea implements DocumentListener {

  public EditorJavaDocument() {
    super();
    setFont(new Font("SansSerif", Font.PLAIN, DlgFont.OPTIMUM_SIZE));
    getDocument().addDocumentListener(this);
  }

  // Document Listeners
  public void insertUpdate(DocumentEvent e) {
  }

  public void removeUpdate(DocumentEvent e) {
  }

  public void changedUpdate(DocumentEvent e) {
  }
}