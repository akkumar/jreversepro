/**
 * @(#)JCustomFileChooser.java
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

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import java.io.File;

/**
 * FileChooser to choose  /view a group of files.
 * @author Karthik Kumar
 * @version 1.3
 **/
@SuppressWarnings("serial")
public class CustomFileChooser extends JFileChooser
 {
    /**
     * mFileExtension Extension of the files to be viewed.
     */
    private String mFileExtension;

    /**
     * Description of the files to be viewed.
     **/
    private String mFileDescription;

    /**
     * @param aDir Default Directory of the File Chooser.
     * @param aDescription Description of the extension aExtension
     * @param aExtension Extension of the file
     * @param aToolTipText Tooltip text used when viewing file.
     **/
    public CustomFileChooser(String aDir,
                    String aDescription ,
                    String aExtension ,
                    String aToolTipText) {
            super(aDir);

            mFileExtension = aExtension;
            mFileDescription = aDescription;

            setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
            setFileFilter( new CustomFileFilter() ) ;
            setApproveButtonToolTipText( aToolTipText );
    }

    /**
     * Makes the file chooser visible.
     * @param aParent Parent Frame
     * @param aSelectButton Name of the button to be selected.
     * @return the status returned by the file chooser.
     **/
    public int showChooser(JFrame aParent , String aSelectButton ) {
        return showDialog(aParent, aSelectButton );
    }

    /**
     * File Filter.
     * @author Karthik Kumar
     **/
    class CustomFileFilter
           extends javax.swing.filechooser.FileFilter {

            /**
             * @param aFilterFile File to be filtered.
             * @return true, if this file is passed by the filter.
             * false, otherwise.
             **/
            public boolean accept( File aFilterFile ) {
                return ( aFilterFile.isDirectory()
                    || aFilterFile.getName().endsWith(mFileExtension) );
            }

            /**
             * @return Description of the Filter involved.
             **/
            public String getDescription() {
                return mFileDescription + "(" + mFileExtension + ")";
            }
    }

}