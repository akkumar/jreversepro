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
package org.jreversepro.gui;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;

@SuppressWarnings("serial")
public class DlgError extends JDialog implements ActionListener {

  JButton BtnDetails;
  JButton BtnOk;
  JScrollPane ScrTrace;
  JScrollPane ScrError;
  boolean details;

  static final int WIDTH = 450;
  static final int HEIGHT = 200;

  public DlgError(JFrame parent, String FileName, Exception _ex) {
    super(parent, "Error : " + FileName, true);
    details = false;

    BtnDetails = new JButton("Details >>");
    BtnOk = new JButton("     Ok    ");
    ScrError = new JScrollPane(
        new JLabel(_ex.toString(), SwingConstants.CENTER));
    JTextArea trace = new JTextArea(getStackTrace(_ex));
    trace.setEditable(false);
    ScrTrace = new JScrollPane(trace);

    BtnDetails.addActionListener(this);
    BtnOk.addActionListener(this);

    setLocation(200, 200);
    setResizable(false);
    setSize(WIDTH, (int) (0.5 * HEIGHT));
    addComponents();
  }

  private void addComponents() {
    getContentPane().setLayout(null);

    ScrError.setBounds(0, 0, WIDTH, (int) (0.25 * HEIGHT));
    BtnOk.setBounds(0, (int) (0.25 * HEIGHT), (int) (0.3 * WIDTH),
        (int) (0.1 * HEIGHT));
    BtnDetails.setBounds((int) (0.6 * WIDTH), (int) (0.25 * HEIGHT),
        (int) (0.3 * WIDTH), (int) (0.1 * HEIGHT));
    ScrTrace.setBounds(0, (int) (0.4 * HEIGHT), WIDTH, (int) (0.45 * HEIGHT));
    getContentPane().add(ScrError);
    getContentPane().add(BtnOk);
    getContentPane().add(BtnDetails);
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == BtnOk) {
      setVisible(false);
    } else if (e.getSource() == BtnDetails) {
      // Make the details available.
      if (details) {
        setSize(WIDTH, (int) (0.5 * HEIGHT));
        getContentPane().remove(ScrTrace);
        details = false;
        BtnDetails.setText("Details >>");
      } else {
        setSize(WIDTH, HEIGHT);
        getContentPane().add(ScrTrace);
        details = true;
        BtnDetails.setText("<< Details");
      }
      validate();
      repaint();
    }
  }

  private String getStackTrace(Exception _ex) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    _ex.printStackTrace(new PrintStream(baos));
    return baos.toString().trim();
  }
}
