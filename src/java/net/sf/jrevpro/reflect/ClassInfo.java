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
 **/
package net.sf.jrevpro.reflect;

import java.util.List;

import net.sf.jrevpro.jvm.JVMConstants;

/**
 * <b>JClassInfo</b> is the abstract representation of the Class File. The names
 * of the methods are self explanatory.
 * 
 * @author Karthik Kumar
 */
public class ClassInfo {

  /**
   * Empty constructor
   */
  public ClassInfo(ConstantPool _cpInfo) {

    cpInfo = _cpInfo;
  }

  public void setInterfaces(List<String> _interfaces) {
    interfaces = _interfaces;
  }

  /**
   * Adds a new field present in the class.
   * 
   * @param rhsField
   *          contains the field-related information.
   */
  public void setFields(List<Field> _fields) {
    memFields = _fields;
  }

  /**
   * Adds a new method present in the class.
   * 
   * @param rhsMethod
   *          contains the method-related information.
   */
  public void setMethods(List<Method> _methods) {
    memMethods = _methods;
  }

  /**
   * Sets the pathname of this class.
   * 
   * @param classPath
   *          Path to this class.
   */
  public void setPathName(String classPath) {
    absPath = classPath;
  }

  /**
   * Returns the constantpool reference
   * 
   * @return Returns the ConstantPool reference.
   */
  public ConstantPool getConstantPool() {
    return this.cpInfo;
  }

  /**
   * Sets the major and minor number of the JVM for which this class file is
   * compiled for.
   * 
   * @param rhsMajor
   *          Major number
   * @param rhsMinor
   *          Minor number
   */
  public void setMajorMinor(short rhsMajor, short rhsMinor) {
    majorNumber = rhsMajor;
    minorNumber = rhsMinor;
  }

  public int getAccess() {
    return accessFlag;
  }

  /**
   * Sets the access flag of the class.
   * 
   * @param rhsAccess
   *          Access flag of the class.
   */
  public void setAccess(int rhsAccess) {
    accessFlag = rhsAccess;
  }

  /**
   * Sets the name of the current class.
   * 
   * @param rhsName
   *          Name of this class.
   */
  public void setThisClass(String rhsName) {
    thisClass = rhsName;
  }

  /**
   * Sets the name of the current class' superclass.
   * 
   * @param rhsName
   *          Name of this class; superclass.
   */
  public void setSuperClass(String rhsName) {
    superClass = rhsName;
  }

  /**
   * Sets the package to which this class belongs to.
   * 
   * @param packageName
   *          name of the package to be set.
   */
  public void setPackageName(String packageName) {
    this.packageName = packageName;
  }

  /**
   * Sets the name of the source file to which this was contained in.
   * 
   * @param rhsSrcFile
   *          Name of the source file.
   */
  public void setSourceFile(String rhsSrcFile) {
    srcFile = rhsSrcFile;
  }

  /**
   * Returns the path name of this class.
   * 
   * @return Absolute path of this class.
   */
  public String getPathName() {
    return absPath;
  }

  /**
   * Returns the major number of the JVM.
   * 
   * @return JVM
   */
  public int getMajor() {
    return majorNumber;
  }

  /**
   * Returns the minor number of the JVM.
   * 
   * @return JVM minor version
   */
  public int getMinor() {
    return minorNumber;
  }

  /**
   * Returns the class name of this class.
   * 
   * @return name of the current class.
   */
  public String getThisClass() {
    return thisClass;
  }

  /**
   * Returns the class name of this class' super class.
   * 
   * @return name of the current class' super-class.
   */
  public String getSuperClass() {
    return superClass;
  }

  /**
   * Returns the source file of the current class.
   * 
   * @return source file of the current class.
   */
  public String getSourceFile() {
    return srcFile;
  }

  /**
   * Returns the List of interfaces of the current class.
   * 
   * @return interfaces of the current class.
   */
  public List<String> getInterfaces() {
    return interfaces;
  }

  /**
   * Returns the fields present in the class.
   * 
   * @return Returns a List of JField
   */
  public List<Field> getFields() {
    return memFields;
  }

  /**
   * Returns the methods of this class.
   * 
   * @return Returns a list of JMethods
   */
  public List<Method> getMethods() {
    return memMethods;
  }

  /**
   * Returns if this is a class or an interface
   * 
   * @return Returns true if this is a class, false, if this is an interface.
   */
  public boolean isClass() {
    return ((accessFlag & JVMConstants.ACC_INTERFACE) == 0);
  }

  // Generic Info about a class File.
  /**
   * Absolute path where the class' source file was located.
   */
  private String absPath;

  /**
   * Major number of the JVM version that this class file is compiled for.
   */
  private short majorNumber;

  /**
   * Minor number of the JVM version that this class file is compiled for.
   */
  private short minorNumber;

  /**
   * Name of the current class in the JVM format. That is, if the class is
   * String then the name would be java/lang/String.
   */
  private String thisClass;

  /**
   * Name of the current class' superclass in the JVM format. That is, if the
   * class is String then the name would be java/lang/String.
   */
  private String superClass;

  /**
   * Name of the package of the current class in the JVM format. That is the
   * fully qualified name of the class is java.lang.String. then the package
   * name would contain java/lang.
   */
  private String packageName;

  /**
   * Name of the source file in which this class files' code is present.
   */
  private String srcFile;

  /**
   * ConstantPool information contained in the class.
   */
  private ConstantPool cpInfo;

  /**
   * List of fields present in the class. All the members in the list are
   * JField.
   */
  private List<Field> memFields;

  /**
   * List of methods present in the class. All the members in the list are
   * JMethod.
   */
  private List<Method> memMethods;

  /**
   * List of interfaces present in the class. All the members in the list are
   * String. For example if the class implements java.awt.event.ActionListener
   * then the list would contain java/awt/event/ActionListener as its member.
   * The class file name would be in the JVM format as mentioned above.
   */
  private List<String> interfaces;

  /**
   * An integer referring to the access permission of the class. Like if a class
   * is public static void main () then the accessflag would have appropriate
   * bits set to say if it public static.
   */
  private int accessFlag;

}
