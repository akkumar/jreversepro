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
package net.sf.jrevpro;

public class VersionChecker {

  /**
   * Checks for the version compatibility between the system JRE and the JRE for
   * which the application is written for.
   * 
   * @return true , if System JRE is >= {@value VersionChecker#DEFAULT_VERSION}<br>
   *         false , otherwise.
   */
  public static boolean versionCheck() {
    String version = System.getProperty("java.version");
    for (int i = 0; i <= 5; i += 2) {
      int versionVal = (version.charAt(i));
      int workingVal = (DEFAULT_VERSION.charAt(i));
      if (versionVal > workingVal) {
        return true;
      } else if (versionVal < workingVal) {
        System.err.println("This Software is designed to run under "
            + DEFAULT_VERSION);
        System.err.println("Please upgrade your JRE"
            + " from http://java.sun.com/j2se" + " for your operating system");
        return false;
      }
    }
    return true;
  }

  /**
   * Working Version Could be compromised
   */
  static final String DEFAULT_VERSION = "1.5.0";

}
