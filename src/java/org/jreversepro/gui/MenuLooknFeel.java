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

@SuppressWarnings("serial")
public class MenuLooknFeel extends JMenu {

  JRadioButtonMenuItem MetalLookAndFeel;
  JRadioButtonMenuItem MotifLookAndFeel;
  JRadioButtonMenuItem WinLookAndFeel;

  ButtonGroup Btngroup;

  JFrame AppFrame;

  public static final String MOTIF_LF = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
  public static final String WINDOWS_LF = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
  public static final String METAL_LF = "javax.swing.plaf.metal.MetalLookAndFeel";

  String App_LF;
  static final String WINDOWS = "Win";
  static final String MOTIF = "Motif";
  static final String METAL = "Metal";

  public MenuLooknFeel(String title, JFrame thisFrame) {
    super(title);

    AppFrame = thisFrame;

    ButtonGroup group = new ButtonGroup();

    MetalLookAndFeel = new JRadioButtonMenuItem("Metal", true);
    MotifLookAndFeel = new JRadioButtonMenuItem("Motif");
    WinLookAndFeel = new JRadioButtonMenuItem("Windows");

    group.add(MetalLookAndFeel);
    group.add(MotifLookAndFeel);
    group.add(WinLookAndFeel);

    add(MetalLookAndFeel);
    add(MotifLookAndFeel);
    add(WinLookAndFeel);

    addLookAndFeelListeners();
  }

  public MenuLooknFeel(JFrame thisFrame) {
    this("Look And Feel", thisFrame);
  }

  public final String getAppLookAndFeel() {
    return App_LF;
  }

  public void setAppLookAndFeel(String Rhs) {
    if (Rhs == null)
      setMetal_LF();
    else if (Rhs.compareTo(WINDOWS) == 0)
      setWin_LF();
    else if (Rhs.compareTo(MOTIF) == 0)
      setMotif_LF();
    else if (Rhs.compareTo(METAL) == 0)
      setMetal_LF();
    else
      setMetal_LF();
  }

  public void setDefaultLookAndFeel() {
    setMetal_LF();
  }

  private void setWin_LF() {
    try {
      UIManager.setLookAndFeel(WINDOWS_LF);
      SwingUtilities.updateComponentTreeUI(AppFrame);
      App_LF = WINDOWS;
    } catch (Exception _ex) {
    }
  }

  private void setMotif_LF() {
    try {
      UIManager.setLookAndFeel(MOTIF_LF);
      SwingUtilities.updateComponentTreeUI(AppFrame);
      App_LF = MOTIF;
    } catch (Exception _ex) {
    }
  }

  private void setMetal_LF() {
    try {
      UIManager.setLookAndFeel(METAL_LF);
      SwingUtilities.updateComponentTreeUI(AppFrame);
      App_LF = METAL;
    } catch (Exception _ex) {
    }
  }

  private void addLookAndFeelListeners() {
    MetalLookAndFeel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setMetal_LF();
      }
    });

    MotifLookAndFeel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setMotif_LF();
      }
    });

    WinLookAndFeel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setWin_LF();
      }
    });

  }
}