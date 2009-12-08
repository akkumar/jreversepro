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
 * 
 */
package net.sf.jrevpro.cmd;

import java.util.logging.Logger;

import net.sf.jrevpro.CustomLoggerFactory;
import net.sf.jrevpro.JReverseProContext.OutputType;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

/**
 * The command line interface of the application in place.
 * 
 * @author karthik.kumar
 *
 */
public class CommandLineInterface {

  public CommandLineInterface() {
    options = new Options();
    options.addOption(OPTION_INPUT, true, "input stream (file/URL)");
    options.addOption(OPTION_DISASSEMBLE, false, "disassemble class files");
    options.addOption(OPTION_DECOMPILE, false, "decompile class files");
    options.addOption(OPTION_GUI, false, "launch Swing UI");
    options.addOption(OPTION_VIEW_CONSTANT_POOL, false, "view constant pool");

    parser = new PosixParser();
    cmd = null;
    outputType = OutputType.NONE;
    guiEnabled = false;
  }

  public void parse(String[] args) {
    try {
      cmd = parser.parse(options, args);
      if (cmd.hasOption(OPTION_GUI)) {
        if (cmd.hasOption(OPTION_INPUT) || cmd.hasOption(OPTION_DECOMPILE)
            || cmd.hasOption(OPTION_DISASSEMBLE)) {
          LOGGER
              .severe(OPTION_GUI
                  + " needs to be specified alone and should not accompany other arguments");
          throw new IllegalArgumentException("Invalid argument specified");
        }
        guiEnabled = true;
        return;
      }

      if (!cmd.hasOption(OPTION_INPUT)) {
        LOGGER.severe("Option -" + OPTION_INPUT + " mandatory");
      }
      outputType = OutputType.NONE;
      if (cmd.hasOption(OPTION_DISASSEMBLE)) {
        outputType = OutputType.DISASSEMBLER;
      }
      if (cmd.hasOption(OPTION_DECOMPILE)) {
        outputType = OutputType.DECOMPILER;
      }
      if (cmd.hasOption(OPTION_GUI)) {
        if (cmd.hasOption(OPTION_INPUT) || cmd.hasOption(OPTION_DECOMPILE)) {
          LOGGER
              .severe(OPTION_GUI
                  + " needs to be specified alone and should not accompany other arguments");
          throw new UnsupportedOperationException("Invalid argument specified");
        }
        guiEnabled = true;
      }
      if (outputType == OutputType.NONE) {
        LOGGER.severe("Need to specify either " + OPTION_DISASSEMBLE + " or "
            + OPTION_DECOMPILE);

      }

      if (cmd.hasOption(DECOMPILE_VERSION)) {
        javaVersionToDecompile = cmd.getOptionValue(DECOMPILE_VERSION);
      }

    } catch (ParseException ex) {
      LOGGER.severe(ex.toString());
    }
  }

  public String getInputResource() {
    return cmd.getOptionValue(OPTION_INPUT);
  }

  public OutputType getOutputType() {
    return outputType;
  }

  public boolean isGuiEnabled() {
    return guiEnabled;
  }

  public String getJavaVersionToDecompile() {
    return javaVersionToDecompile;
  }

  private String javaVersionToDecompile = "1.4";

  private final Options options;

  private final CommandLineParser parser;

  private CommandLine cmd;

  private OutputType outputType;

  private boolean guiEnabled;

  private static final String OPTION_INPUT = "i";

  private static final String OPTION_DISASSEMBLE = "a";

  private static final String OPTION_DECOMPILE = "d";

  private static final String OPTION_VIEW_CONSTANT_POOL = "v";

  private static final String OPTION_GUI = "u";

  // t may mean target as v is already used
  private static final String DECOMPILE_VERSION = "t";

  private final Logger LOGGER = CustomLoggerFactory.createLogger();

}
