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
 ***/
package org.jreversepro.gui;

import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

@SuppressWarnings("serial")
public class MainMenu extends JMenuBar {
  static final int MAX_THEMES = 3;

  JMenu onFile;
  JMenu onEdit;
  JMenu onView;
  JMenu onOptions;
  public MenuLooknFeel onLookFeel;
  JMenu onHelp;

  public JMenuItem onFileOpen;
  public JMenuItem onFileSave;
  public JMenuItem onFileExit;

  public JMenuItem onEditCut;
  public JMenuItem onEditCopy;

  public JMenuItem onViewCPool;

  public JMenuItem onOptFont;

  public JRadioButtonMenuItem onDisAssembler;
  public JRadioButtonMenuItem onDecompiler;

  public JMenuItem onHelpAbout;

  ButtonGroup group;
  JFrame parent;

  public MainMenu(JFrame owner) {

    Font FontObj = new Font("Serif", Font.PLAIN, 10);

    parent = owner;

    onFile = new JMenu("File");
    onEdit = new JMenu("Edit");
    onView = new JMenu("View");
    onOptions = new JMenu("Options");
    onHelp = new JMenu("Help");

    onLookFeel = new MenuLooknFeel("Look And Feel", parent);

    initMenuItems();

    onFile.add(onFileOpen);
    onFile.add(onFileSave);
    onFile.add(onFileExit);

    onEdit.add(onEditCut);
    onEdit.add(onEditCopy);

    onView.add(onViewCPool);

    onOptions.add(onOptFont);
    onOptions.addSeparator();
    onOptions.add(onDisAssembler);
    onOptions.add(onDecompiler);

    onHelp.add(onHelpAbout);

    add(onFile);
    add(onEdit);
    add(onView);
    add(onOptions);
    add(onLookFeel);
    add(onHelp);

    setFont(FontObj);
  }

  public void setFlag(String Rhs) {
    boolean value = false;
    if (Rhs.compareTo("true") == 0)
      value = true;
    else
      value = false;
    onDecompiler.setSelected(value);
    onDisAssembler.setSelected(!value);
  }

  private void initMenuItems() {
    onFileOpen = new JMenuItem("Open");
    onFileSave = new JMenuItem("Save");
    onFileExit = new JMenuItem("Exit");

    onEditCut = new JMenuItem("Cut");
    onEditCopy = new JMenuItem("Copy");

    onViewCPool = new JMenuItem("ConstantPool");

    onOptFont = new JMenuItem("Set Font ");

    onDisAssembler = new JRadioButtonMenuItem("DisAssemble", true);
    onDecompiler = new JRadioButtonMenuItem("Decompile");

    group = new ButtonGroup();
    group.add(onDisAssembler);
    group.add(onDecompiler);

    onHelpAbout = new JMenuItem("About");
  }
}
