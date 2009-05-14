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


/**
 *
 * @author Karthik Kumar
 **/
interface GuiConstants {

    /**
     * Name of property file.
     **/
    String PROP_FILE = "jrev.ini";


    /**
     * Heading of property file.
     **/
    String PROP_HEADING = "JReversePro - Java Decompiler / Disassembler";

    /**
     * DecompileFlag Property.
     **/
    String DECOMPILE_FLAG = "Decompile";

    /**
     * XPosition of GUI window.
     **/
    String XPOS = "XPos";

    /**
     * YPosition of GUI window.
     **/
    String YPOS = "YPos";

    /**
     * Width of GUI window.
     **/
    String XSIZE = "Width";

    /**
     * Height of GUI window.
     **/
    String YSIZE = "Height";

    /**
     * Look And Feel of Window.
     **/
    String L_AND_F = "LookAndFeel";

    /**
     * Font of GUI window.
     **/
    String FONT  = "Font";

    /**
     * Title of GUI window.
     **/
    String TITLE = "JReversePro - Java Decompiler / Disassembler";



    /**
     * MAGIC corresponds to the Magic number appearing in
     * the beginning of class files.
     **/
    int MAGIC = 0xCAFEBABE;
}
