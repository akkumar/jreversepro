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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.synth.SynthLookAndFeel;

import com.apple.laf.AquaLookAndFeel;
import com.sun.java.swing.plaf.motif.MotifLookAndFeel;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

@SuppressWarnings("serial")
public class MenuLooknFeel extends JMenu {

  JRadioButtonMenuItem MetalLookAndFeel;
  JRadioButtonMenuItem MotifLookAndFeel;
  JRadioButtonMenuItem WinLookAndFeel;

  ButtonGroup Btngroup;

  JFrame AppFrame;

  private static final String MOTIF_LF = MotifLookAndFeel.class.getName();
  private static final String WINDOWS_LF = WindowsLookAndFeel.class.getName();
  private static final String DEFAULT_LF = SynthLookAndFeel.class.getName();
  private static final String AQUA_LF = AquaLookAndFeel.class.getName();

  String App_LF;
  static final String WINDOWS = "Win";
  static final String MOTIF = "Motif";
  static final String SYNTH = "Synth";

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
      setDefault_LF();
    else if (Rhs.compareTo(WINDOWS) == 0)
      setWin_LF();
    else if (Rhs.compareTo(MOTIF) == 0)
      setMotif_LF();
    else
      setDefault_LF();
  }

  public void setDefaultLookAndFeel() {
    setDefault_LF();
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

  private void setDefault_LF() {
    try {
      if (System.getProperty("os.name").startsWith("Mac")) {
        UIManager.setLookAndFeel(AQUA_LF);
      } else {
        UIManager.setLookAndFeel(DEFAULT_LF);
      }
      SwingUtilities.updateComponentTreeUI(AppFrame);
      App_LF = SYNTH;
    } catch (Exception ulfe) {

    } 
  }

  private void addLookAndFeelListeners() {
    MetalLookAndFeel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setDefault_LF();
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