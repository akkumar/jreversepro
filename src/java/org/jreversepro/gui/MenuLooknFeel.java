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
import java.util.HashMap;
import java.util.Map;

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

  /**
   * Current LnF of the app
   */
  String App_LF;

  static final String WINDOWS = "Win";
  static final String MOTIF = "Motif";
  static final String MAC   = "Mac";
  static final String SYNTH = "Synth";
  
  static final Map<String, String> LnFClasses;

  static {
    LnFClasses = new HashMap<String,String>();
    LnFClasses.put(WINDOWS, WindowsLookAndFeel.class.getName());
    LnFClasses.put(MOTIF, MotifLookAndFeel.class.getName());
    LnFClasses.put(MAC, AquaLookAndFeel.class.getName());
    LnFClasses.put(SYNTH, SynthLookAndFeel.class.getName());
  }

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

  public void setAppLookAndFeel(String rhs) {
    String tmpLF = SYNTH;
    if (rhs == null) {
      if (System.getProperty("os.name").startsWith("Mac")) {
        tmpLF = MAC;
      } else {
        tmpLF = SYNTH;
      }
    }
    else if (rhs.compareTo(WINDOWS) == 0 || rhs.compareTo(MOTIF) == 0 || rhs.compareTo(MAC) == 0) 
      tmpLF = rhs;
    else
      tmpLF = SYNTH;
    updateLF(tmpLF);
  }

  public void setDefaultLookAndFeel() {
    this.setAppLookAndFeel(null);
  }

  private void updateLF(final String lnf) {
    try {
      UIManager.setLookAndFeel(LnFClasses.get(lnf));
      SwingUtilities.updateComponentTreeUI(AppFrame);
      App_LF = lnf;
    } catch (Exception _ex) {
    }
  }

  private void addLookAndFeelListeners() {
    MetalLookAndFeel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setAppLookAndFeel(SYNTH);
      }
    });

    MotifLookAndFeel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setAppLookAndFeel(MOTIF);
      }
    });

    WinLookAndFeel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setAppLookAndFeel(WINDOWS);
      }
    });

  }

}