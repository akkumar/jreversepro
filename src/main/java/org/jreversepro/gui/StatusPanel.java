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
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.Calendar;

/**
 * Represents the Status Panel of the Decompiler
 * 
 */
@SuppressWarnings("serial")
public class StatusPanel extends JPanel implements ActionListener {

  private final JLabel LblTime;
  Timer SysTimer;
  Calendar Today;

  static final int INTERVAL = 1000;// milliseconds

  public StatusPanel() {
    LblTime = new JLabel("", SwingConstants.RIGHT);

    // Right Justify Time
    setLayout(new GridLayout(1, 1));
    add(LblTime);

    Today = Calendar.getInstance();

    SysTimer = new Timer(INTERVAL, this);
    SysTimer.start();
  }

  public void actionPerformed(final ActionEvent e) {
    if (e.getSource() == SysTimer) {
      LblTime.setText(new Date(System.currentTimeMillis()).toString());
    }
  }
}
