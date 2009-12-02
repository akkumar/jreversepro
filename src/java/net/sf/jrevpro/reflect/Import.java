/**
 * @(#)JImport.java
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
package net.sf.jrevpro.reflect;

import java.util.Set;
import java.util.TreeSet;

import net.sf.jrevpro.jls.JLSConstants;
import net.sf.jrevpro.jvm.JVMConstants;
import net.sf.jrevpro.jvm.TypeInferrer;

/**
 * Describes the Set of import statements.
 * 
 * @author Karthik Kumar.
 */
public class Import {

  /**
   * no-arg constructor
   */
  public Import() {
    this.classes = new TreeSet<String>();
  }

  /**
   * Adds a new class to the list of classes referenced by the current class.
   * 
   * @param importClass
   *          name of new class.
   */
  public void addClass(String importClass) {
    classes.add(importClass);
  }

  public Set<String> getClasses() {
    return classes;
  }

  /**
   * Returns the Package name alone from a fully qualified name.
   * <p>
   * For Example , if <code>FullName = java/lang/StringBuilder,</code> <br>
   * then a call to <code>getPackageName(arg)</code> returns the value
   * <code>java.lang</code>.
   * <p>
   * 
   * @param aFullName
   *          A Fully Qualified Name.
   * @return the package name , alone with the dots separating the classes.
   */
  public static String getPackageName(String aFullName) {
    aFullName = TypeInferrer.getJLSType(aFullName, false);
    aFullName = aFullName.replace(JVMConstants.JVM_PACKAGE_DELIMITER,
        JLSConstants.JLS_PACKAGE_DELIMITER);
    int dotIndex = aFullName.lastIndexOf(JLSConstants.JLS_PACKAGE_DELIMITER);
    if (dotIndex != -1) {
      return aFullName.substring(0, dotIndex);
    } else {
      return "";
    }
  }

  /**
   * Returns the Class name alone from a fully qualified name.
   * <p>
   * For Example , if <code>FullName = java/lang/StringBuilder,</code> <br>
   * then a call to <code>getClassName(arg)</code> returns the value
   * <code>StringBuilder </code>.
   * <p>
   * 
   * @param fullQualifiedName
   *          A Fully Qualified Name.
   * @return the class name , alone.
   */
  public static String getClassName(String fullQualifiedName) {
    String aFullName = fullQualifiedName.replace(
        JVMConstants.JVM_PACKAGE_DELIMITER, JLSConstants.JLS_PACKAGE_DELIMITER);
    int dotIndex = aFullName.lastIndexOf(JLSConstants.JLS_PACKAGE_DELIMITER);
    if (dotIndex != -1) {
      return aFullName.substring(dotIndex + 1);
    } else {
      return aFullName;
    }
  }

  /**
   * List of classes that are referenced by this class. The elements of this
   * list are 'String'.
   */
  private Set<String> classes;

}