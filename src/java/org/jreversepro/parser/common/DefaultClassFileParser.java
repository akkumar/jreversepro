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
package org.jreversepro.parser.common;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;

import org.jreversepro.parser.ClassFileParser;
import org.jreversepro.parser.ClassParserException;
import org.jreversepro.reflect.ClassInfo;
import org.jreversepro.reflect.ConstantPool;
import org.jreversepro.reflect.Field;
import org.jreversepro.reflect.Method;


public class DefaultClassFileParser implements ClassFileParser {

  /**
   * Parses the given file and creates the ClassInfo and ConstantPool objects.
   * 
   * @param dis
   *          InputStream containing the bytes.
   * @param pathToClass
   *          path to the class.
   * @throws ClassParserException
   *           Thrown if class file not in desired format.
   * @throws IOException
   *           Thrown if error in stream of bytes containing the class file.
   */
  public ClassInfo parseInputStream(DataInputStream dis, String pathToClass)
      throws IOException, ClassParserException {

    ConstantPool cp = AbstractClassFileParser.readConstantPool(dis);
    ClassInfo clazz = new ClassInfo(cp);
    short access = AbstractClassFileParser.readAccess(dis);

    clazz.setAccess(access);

    // <p>
    // For Example , a class by name <code>JClassParser</code> in the
    // package
    // <code>Heart</code> would be saved as: <code>Heart/JClassParser</code>
    // </p>.
    short thisClassIndex = AbstractClassFileParser.readThisClassIndex(dis);
    clazz.setThisClass(cp.getClassName(thisClassIndex));

    short superClassIndex = AbstractClassFileParser.readSuperClassIndex(dis);
    clazz.setSuperClass(cp.getClassName(superClassIndex));

    List<String> interfaceNames = AbstractClassFileParser.readInterfaces(dis,
        cp);
    clazz.setInterfaces(interfaceNames);

    List<Field> fields = AbstractClassFileParser.readFields(dis, cp);
    clazz.setFields(fields);

    List<Method> methods = AbstractClassFileParser.readMethods(dis, cp);
    clazz.setMethods(methods);

    AbstractClassFileParser.readClassAttributes(dis, cp, clazz);

    return clazz;
  }

}
