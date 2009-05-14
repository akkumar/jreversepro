/**
 * @(#)DlgAbout.java
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

import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.sf.jrevpro.JReverseProContext;


/**
 * This contains the definition of the About Dialog Box
 * @author Karthik Kumar
 * @version 1.3
 **/
@SuppressWarnings("serial")
public class DlgAbout extends JDialog implements GuiConstants {

    /**
     * @param aParent Parent Frame
     * @param aTitle Title of the Dialog box.
     **/
    public DlgAbout( JFrame aParent , String aTitle ) {
        super( aParent, aTitle, true );


        JTextArea txtAbout = new JTextArea( JReverseProContext.GPL_INFO );
        JScrollPane ScrAbout = new JScrollPane(txtAbout);



        txtAbout.setEditable(false);


        getContentPane().setLayout( new GridLayout(1,1) );
        getContentPane().add(ScrAbout);

        setSize( 400 ,170);
        setLocation(150,50);
        setResizable(false);
        setVisible(true);
        addWindowListener( new DlgClose() );
    }

}

