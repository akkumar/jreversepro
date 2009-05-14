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


import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

@SuppressWarnings("serial")
public class MainMenu extends JMenuBar
{
    static final int MAX_THEMES = 3 ;

    JMenu OnFile;
    JMenu OnEdit;
    JMenu   OnView;
    JMenu   OnOptions;
    public MenuLooknFeel OnLookFeel;
    JMenu OnHelp;


    public JMenuItem OnFileOpen;
    public JMenuItem OnFileSave;
    public JMenuItem OnFileExit;

    public JMenuItem OnEditCut;
    public JMenuItem OnEditCopy;

    public JMenuItem OnViewCPool;

    public JMenuItem    OnOptFont;

    public JRadioButtonMenuItem OnDisAssembler;
    public JRadioButtonMenuItem OnDecompiler;

    public JMenuItem OnHelpAbout;

    ButtonGroup group;
    JFrame parent;

    public MainMenu(JFrame owner ) {

        Font FontObj = new Font("Serif", Font.PLAIN , 10);


        parent = owner;

        OnFile = new JMenu("File");
        OnEdit = new JMenu("Edit");
        OnView = new JMenu("View");
        OnOptions = new JMenu("Options");
        OnHelp = new JMenu("Help");


        OnLookFeel = new MenuLooknFeel("Look And Feel" , parent );

        initMenuItems();

        OnFile.add(OnFileOpen);
        OnFile.add(OnFileSave);
        OnFile.add(OnFileExit);

        OnEdit.add(OnEditCut);
        OnEdit.add(OnEditCopy);

        OnView.add(OnViewCPool);

        OnOptions.add(OnOptFont);
        OnOptions.addSeparator();
        OnOptions.add(OnDisAssembler);
        OnOptions.add(OnDecompiler);

        OnHelp.add(OnHelpAbout);

        add(OnFile);
        add(OnEdit);
        add(OnView);
        add(OnOptions);
        add(OnLookFeel);
        add(OnHelp);

        setFont(FontObj);
    }

    public void setFlag( String Rhs ) {
        boolean value = false;
        if( Rhs.compareTo("true") == 0 ) value = true;
        else value = false;
        OnDecompiler.setSelected(value);
        OnDisAssembler.setSelected(!value);
    }

    private void initMenuItems(){
        OnFileOpen = new JMenuItem("Open");
        OnFileSave = new JMenuItem("Save");
        OnFileExit = new JMenuItem("Exit");

        OnEditCut = new JMenuItem("Cut");
        OnEditCopy = new JMenuItem("Copy");

        OnViewCPool = new JMenuItem("ConstantPool");

        OnOptFont = new JMenuItem("Set Font ");

        OnDisAssembler = new JRadioButtonMenuItem("DisAssemble",true);
        OnDecompiler  = new JRadioButtonMenuItem("Decompile");

        group = new ButtonGroup();
        group.add( OnDisAssembler );
        group.add( OnDecompiler );

        OnHelpAbout = new JMenuItem("About");
    }
}
