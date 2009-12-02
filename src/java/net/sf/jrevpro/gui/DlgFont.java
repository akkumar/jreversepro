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

package net.sf.jrevpro.gui;

import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

import java.awt.event.ItemListener;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionEvent;

import java.awt.Frame;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GraphicsEnvironment;

import java.util.TreeSet;
import java.util.Vector;

@SuppressWarnings("serial")
public class DlgFont extends JDialog implements ItemListener, ActionListener {

  public DlgFont(Frame owner, String title) {
    super(owner, title, true);

    faces = new JComboBox(getFontObjects());
    faces.addItemListener(this);

    BtnOk = new JButton("         Ok           ");
    BtnCancel = new JButton("     Cancel        ");

    LblTest = new JLabel("AaBbCc123", SwingConstants.LEFT);
    LblTest.setFont(new Font("SansSerif", Font.PLAIN, OPTIMUM_SIZE));

    GridBagConstraints c = new GridBagConstraints();

    getContentPane().setLayout(new GridBagLayout());

    c.gridheight = 2;
    c.weighty = 1.0;
    c.weightx = 3.0;
    getContentPane().add(faces, c);

    c.weighty = 0.0;
    c.gridwidth = GridBagConstraints.REMAINDER;
    c.gridheight = 1;
    c.weightx = 0.0;
    getContentPane().add(BtnOk, c);
    getContentPane().add(BtnCancel, c);

    c.gridwidth = 1; // reset to the default
    getContentPane().add(LblTest, c);

    setSize(500, 120);
    setLocation(100, 200);
    setResizable(false);

    BtnOk.addActionListener(this);
    BtnCancel.addActionListener(this);
  }

  public Font getChosenFont() {
    return CurFont;
  }

  public int showFontDialog() {
    setVisible(true);
    return Selection;
  }

  public void itemStateChanged(ItemEvent e) {
    Font PreviewFont = new Font((String) (faces.getSelectedItem()), Font.PLAIN,
        PREVIEW_SIZE);

    CurFont = new Font((String) (faces.getSelectedItem()), Font.PLAIN,
        OPTIMUM_SIZE);
    LblTest.setFont(PreviewFont);
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == BtnOk)
      Selection = SELECTED;
    else
      Selection = CANCELLED;
    setVisible(false);
  }

  // Get Font Objects
  private Vector<String> getFontObjects() {
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

    FontSet Fs = new FontSet(ge.getAllFonts());
    return Fs.getFonts();
  }

  // Static constants
  public static final int SELECTED = 1;
  public static final int CANCELLED = 2;

  public static final int PREVIEW_SIZE = 20;
  public static final int OPTIMUM_SIZE = 14;

  private JComboBox faces;

  private JButton BtnOk;
  private JButton BtnCancel;

  private JLabel LblTest;

  private int Selection;

  private Font CurFont;

}// End of class

/**
 * 
 */
@SuppressWarnings("serial")
class FontSet extends TreeSet<String> {

  public FontSet(Font[] FontList) {
    for (int i = 0; i < FontList.length; i++) {
      add(FontList[i].getFamily());
    }
  }

  /**
   * @return the fonts present in the collection.
   */
  public Vector<String> getFonts() {
    Vector<String> result = new Vector<String>();
    for (String s : this) {
      result.add(s);
    }
    return result;
  }
}