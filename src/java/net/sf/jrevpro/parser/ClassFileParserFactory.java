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
 * limitations under the License. * 
 */
package net.sf.jrevpro.parser;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Logger;

import net.sf.jrevpro.CustomLoggerFactory;
import net.sf.jrevpro.jvm.JVMConstants;
import net.sf.jrevpro.parser.common.DefaultClassFileParser;

public class ClassFileParserFactory {

  public static ClassFileParser getClassFileParser(DataInputStream dis)
      throws ClassParserException, IOException {
    assertJVMMagic(dis);
    JVMVersion jvmVersion = readVersion(dis);
    // TODO: Depending on the supported JVM Versions - select one
    // appopriately
    return new DefaultClassFileParser();
  }

  /**
   * Reads the Magic number.
   * 
   * @throws ClassParserException
   *           Thrown if class file not in desired format.
   * @throws IOException
   *           Thrown if error in stream of bytes containing the class file.
   */
  private static void assertJVMMagic(DataInputStream dis)
      throws ClassParserException, IOException {
    int magic = dis.readInt();
    if (magic != JVMConstants.MAGIC) {
      throw new ClassParserException("Invalid Magic Number" + magic);
    }
  }

  /**
   * Reads the Version of the class file.
   * 
   * @return
   * 
   * @throws ClassParserException
   *           Thrown if class file not in desired format.
   * @throws IOException
   *           Thrown if error in stream of bytes containing the class file.
   */
  private static JVMVersion readVersion(DataInputStream dis) throws IOException {
    JVMVersion jvmVersion = new JVMVersion();

    jvmVersion.minor = dis.readShort();
    jvmVersion.major = dis.readShort();

    return jvmVersion;
  }

  /**
   * @param major
   *          Major version of the JVM.
   * @param minor
   *          Minor version of the JVM. test whether or not the supplied
   *          major/minor class versions are acceptable. (NOTE: we should
   *          probably have a runtime switch to accept all versions since even
   *          though the versions may be incremented, there are typically no
   *          changes to the form of the class files)
   * 
   *          45.3+ is java 1.1 46.0 is java 1.2 47.0 is java 1.3 48.0 is java
   *          1.4
   * 
   * @return true if the major/minor versions are acceptable
   */
  private static boolean supportedMajorMinor(JVMVersion _version) {
    if (_version.major == 45) {
      return (_version.minor >= 3);
    }

    if (_version.major >= 46 && _version.major <= 48) {
      return true;
    }
    logger.warning("Major: " + _version.major + " ,Minor: " + _version.minor
        + " not supported ");
    return false;
  }

  private ClassFileParserFactory() {
  }

  static class JVMVersion {
    short minor;
    short major;
  }

  private static Logger logger = CustomLoggerFactory.createLogger();

}
