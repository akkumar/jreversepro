/**
 * @(#)CustomListPanel.java
 *
 * JReversePro - Java Decompiler / Disassembler.
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
package net.sf.jrevpro.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

/**
 * Provides a Custom List Panel.
 * 
 * @author Karthik Kumar
 **/
@SuppressWarnings("serial")
public class CustomListPanel extends JPanel {
  /**
   * Corresponds to the Label 'Constant PoolTable'
   **/
  private JLabel mLblList;

  /**
   * Corresponds to the Label 'Goto Index'.
   **/
  private JLabel mLblGoto;

  /**
   * Gets Index of the ConstantPool as input from the user.
   **/
  public JTextField mTxtIndex;

  /**
   * Goto button.
   **/
  public JButton mBtnGoto;

  /**
   * Text to search to in the ConstantPool.
   **/
  public JTextField mTxtSearch;

  /**
   * List of categories available for searching.
   **/
  public JComboBox mChooseType;

  /**
   * Find button.
   **/
  public JButton mBtnFind;

  /**
   * @param aMaxEntries
   *          Maximum Entries of the ConstantPool Table.
   **/
  public CustomListPanel(int aMaxEntries) {
    mLblList = new JLabel("Constant Pool Table:   " + "Total Entries "
        + String.valueOf(aMaxEntries), SwingConstants.CENTER);

    mLblGoto = new JLabel("Goto Index ", SwingConstants.CENTER);

    mTxtIndex = new JTextField(10);
    mBtnGoto = new JButton("Goto");

    Object[] items = { "ConstantPool Values", "Pointer I", "Pointer II" };

    mChooseType = new JComboBox(items);
    mTxtSearch = new JTextField(10);
    mBtnFind = new JButton("Find Next");

    setLayout(new GridBagLayout());
    setSize(100, 75);
    addComponents();
  }

  /**
   * Adds the components.
   **/
  private void addComponents() {
    // GridBagLayout grid = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();

    c.gridwidth = GridBagConstraints.REMAINDER;
    add(mLblList, c);

    c.gridwidth = 1;
    add(mLblGoto, c);
    add(mTxtIndex, c);

    c.gridwidth = GridBagConstraints.REMAINDER;
    add(mBtnGoto, c);

    c.gridwidth = 1;
    add(mTxtSearch, c);
    add(mChooseType, c);

    c.gridwidth = GridBagConstraints.REMAINDER;
    add(mBtnFind, c);

  }
}
