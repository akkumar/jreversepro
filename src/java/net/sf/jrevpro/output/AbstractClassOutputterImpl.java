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
package net.sf.jrevpro.output;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.sf.jrevpro.CustomLoggerFactory;
import net.sf.jrevpro.jls.JLSConstants;
import net.sf.jrevpro.jvm.JVMConstants;
import net.sf.jrevpro.jvm.TypeInferrer;
import net.sf.jrevpro.reflect.ClassInfo;
import net.sf.jrevpro.reflect.Field;
import net.sf.jrevpro.reflect.Import;
import net.sf.jrevpro.reflect.Method;
import net.sf.jrevpro.reflect.variabletable.VariableTable;

abstract class AbstractClassOutputterImpl implements JVMConstants {

  protected AbstractClassOutputterImpl(ClassInfo _clazz, CodeStyler _styler) {
    clazz = _clazz;
    sb = new StringBuilder();
    styler = _styler;

  }

  public void clearContents() {
    sb = null;
    sb = new StringBuilder();
  }

  public String getContents() {
    return sb.toString();
  }

  public abstract void process();

  /**
   *  Writes the header comments
   */
  protected void outputHeaderComments() {
    sb.append("\n// Source: " + clazz.getSourceFile());
  }

  /**
   *  Writes the package Imports
   */
  protected void outputPackageImports() {
    String packageName = Import.getPackageName(clazz.getThisClass());

    if (packageName.length() != 0) {
      sb.append("\n" + JLSConstants.PACKAGE + " " + packageName
          + JLSConstants.END_OF_STATEMENT);
    }

    sb.append("\n\n");
    Import imports = clazz.getConstantPool().getImportedClasses();
    this.outputImports(imports, packageName);
  }

  /**
   *  Outputs current and super classes
   */
  protected void outputThisSuperClasses() {
    sb.append("\n\n" + getTypeAsString() + " ");

    sb.append(Import.getClassName(clazz.getThisClass()));
    String superClass = clazz.getSuperClass();

    if (!superClass.equals(JVMConstants.CLASS_LANG_OBJECT)) {
      sb.append(" " + JLSConstants.EXTENDS + " ");
      sb.append(Import.getClassName(superClass) + "   ");
    }
  }

  /**
   *  outputs the interfaces implemented by this class.
   */
  protected void outputInterfaces() {
    List<String> interfaces = clazz.getInterfaces();
    if (interfaces.size() != 0) {
      sb.append("\n\t\t " + JLSConstants.IMPLEMENTS + " ");
      for (int i = 0; i < interfaces.size(); i++) {
        if (i != 0) {
          sb.append(JLSConstants.INTERFACE_DELIMITER);
        }
        sb.append(Import.getClassName(interfaces.get(i)));
      }
    }
  }

  /**
   *  output the Fields of the given class
   */
  protected void outputFields() {
    sb.append("\n");
    for (Field field : clazz.getFields()) {
      String datatype = Import.getClassName(TypeInferrer.getJLSType(field
          .getDatatype(), false));

      String access = this.getAccessQualifier(field.getQualifier(), true);

      sb.append("\n\t" + access);
      sb.append(datatype);
      sb.append(" " + field.getName());
      String val = field.getValue();
      if (field.isFinal() && val.length() != 0) {
        sb.append(JLSConstants.EQUALTO + val);
      }
      sb.append(JLSConstants.END_OF_STATEMENT);
    }
  }

  /**
   * Returns the access string of this class.
   * 
   * @return Returns the access string of this class.
   */
  private String getTypeAsString() {
    StringBuilder accString = new StringBuilder();
    accString.append(getAccessQualifier(clazz.getAccess(), false));

    if (clazz.isClass()) {
      accString.append(JLSConstants.CLASS);
    } else {
      accString.append(JLSConstants.INTERFACE);
    }
    return accString.toString();
  }

  /**
   * Returns a string that contains all the imported classes in the proper
   * format as written in code. For eg, if the list contains p1.class1 ,
   * p2.class2 , this generates a string with import statements for both of
   * them. classes belonging to the default package are excluded. Also there is
   * an option by which we can exclude the classes that belong to a given
   * package ( current package ).
   * 
   * @param packageName
   *          current packagename and name for which package name is to be
   *          excluded.
   * String containing the code mentioned.
   */
  private void outputImports(Import imports, String packageName) {
    List<String> restrictPackages = new ArrayList<String>(2);
    restrictPackages.add(packageName);
    restrictPackages.add(JLSConstants.DEFAULT_PACKAGE);

    logger.fine("Number of imports" + imports.getClasses().size());
    for (String currentClass : imports.getClasses()) {
      if (currentClass.indexOf(JVMConstants.JVM_PACKAGE_DELIMITER) != -1) {
        String currentPackage = Import.getPackageName(currentClass);
        if (!restrictPackages.contains(currentPackage)) {
          currentClass = currentClass.replace(
              JVMConstants.JVM_PACKAGE_DELIMITER,
              JLSConstants.JLS_PACKAGE_DELIMITER);
          sb.append(JLSConstants.IMPORT + " ");
          sb.append(currentClass + JLSConstants.END_OF_STATEMENT + "\n");
        }
      }
    }
  }

  /**
   * Returns the String Representation of the qualifier. Certain qualifiers like
   * volatile, transient, sync. are applicable only for methods and fields. and
   * not classes. To identify them separately, we also pass another parameter
   * called memberOnly. Only if this is set then those bits are checked for.
   * Else they are ignored, since for a class/interface they may not be
   * applicable.
   * 
   * @param rhsQualifier
   *          Qualifier byte with the bits set.
   * @param memberOnly
   *          Only if this is set then the bits relevant to fields and methods
   *          only are checked for. Else ignored.
   * @return String Containing the representation.
   */
  private String getAccessQualifier(int rhsQualifier, boolean memberOnly) {

    StringBuilder access = new StringBuilder("");
    if ((rhsQualifier & ACC_PUBLIC) != 0) {
      access.append(JLSConstants.ACCESS_PUBLIC);
      access.append(" ");
    } else if ((rhsQualifier & ACC_PRIVATE) != 0) {
      access.append(JLSConstants.ACCESS_PRIVATE);
      access.append(" ");
    } else if ((rhsQualifier & ACC_PROTECTED) != 0) {
      access.append(JLSConstants.ACCESS_PROTECTED);
      access.append(" ");
    }

    if ((rhsQualifier & ACC_STATIC) != 0) {
      access.append(JLSConstants.ACCESS_STATIC);
      access.append(" ");
    }
    if ((rhsQualifier & ACC_FINAL) != 0) {
      access.append(JLSConstants.ACCESS_FINAL);
      access.append(" ");
    }
    if ((rhsQualifier & ACC_ABSTRACT) != 0) {
      access.append(JLSConstants.ACCESS_ABSTRACT);
      access.append(" ");
    }

    if (memberOnly) {

      // Fields only
      if ((rhsQualifier & ACC_VOLATILE) != 0) {
        access.append(JLSConstants.ACCESS_VOLATILE);
        access.append(" ");
      }
      if ((rhsQualifier & ACC_TRANSIENT) != 0) {
        access.append(JLSConstants.ACCESS_TRANSIENT);
        access.append(" ");
      }

      // Methods only
      if ((rhsQualifier & ACC_SYNCHRONIZED) != 0) {
        access.append(JLSConstants.ACCESS_SYNCHRONIZED);
        access.append(" ");
      }
      if ((rhsQualifier & ACC_NATIVE) != 0) {
        access.append(JLSConstants.ACCESS_NATIVE);
        access.append(" ");
      }
      if ((rhsQualifier & ACC_STRICT) != 0) {
        access.append(JLSConstants.ACCESS_STRICTFP);
        access.append(" ");
      }
    }
    return access.toString();
  }

  /**
   * Returns the headers for the method.
   * 
   * @param method
   *          Method information for which method header needs to be outputted.
   * @return Returns the method header information.
   */
  protected void outputMethodHeader(Method method) {

    String returnType = Import.getClassName(TypeInferrer.getJLSType(method
        .getReturnType(), false));

    String name = method.getName();
    sb.append("\n\n    ");

    if (name.compareTo(CLINIT) == 0) {
      sb.append(JLSConstants.STATIC);
    } else if (name.compareTo(INIT) == 0) {
      sb.append(getAccessQualifier(method.getQualifier(), true));
      sb.append(extractClassOnly(clazz.getThisClass()));
    } else {
      sb.append(getAccessQualifier(method.getQualifier(), true));
      sb.append(returnType);
      sb.append(" " + method.getName());
    }

    List<String> args = method.getArgList();

    if (method.getName().compareTo(CLINIT) != 0) {
      sb.append("(");
      int baseVariableIndex = method.isStatic() ? 0 : 1;
      for (int i = 0; i < args.size(); i++) {
        if (i != 0) {
          sb.append(" ,");
        }
        String jvmArgType = args.get(i);
        String argType = Import.getClassName(TypeInferrer.getJLSType(
            jvmArgType, false));

        sb.append(argType);
        // TODO Later move this code to MethodEmitter
        sb.append(" ");
        // 0 is ok here - since the method arguments are
        // going to be in the full scope of the method.
        sb.append(method.getVariableTable().getName(baseVariableIndex++,
            VariableTable.FULL_SCOPE_INSTRUCTION_INDEX));

        if (TypeInferrer.doesTypeOccupy2EntriesInVariableTable(jvmArgType)) {
          baseVariableIndex++; // Ignore this.
        }

      }
      sb.append(")");
    }
    outputThrowsClause(method.getThrowsClasses(), clazz.getConstantPool()
        .getImportedClasses());
  }

  /**
   * 
   * returns a throws clause for the method
   * 
   * @param importInfo
   *          containing the import information.
   * @return Returns a string that contains the code representation.
   */
  private void outputThrowsClause(List<String> throwsClasses, Import importInfo) {
    int size = throwsClasses.size();
    if (size != 0) {
      sb.append("\n\t\t" + JLSConstants.THROWS + " ");
      for (int i = 0; i < size; i++) {
        String thrownClass = throwsClasses.get(i);
        if (i > 0) {
          sb.append(" ,");
        }
        sb.append(Import.getClassName(thrownClass));
      }
    }
  }

  private static String extractClassOnly(String jvmType) {
    int lastIndex = jvmType.lastIndexOf(JVMConstants.JVM_PACKAGE_DELIMITER);
    if (lastIndex != -1) {
      return jvmType.substring(lastIndex + 1);
    } else {
      return jvmType;
    }

  }

  protected void openBlock() {
    sb.append(styler.openBlock());
  }

  protected void closeBlock() {
    sb.append(styler.closeBlock());
  }

  protected void outputString(String str) {
    sb.append(str);
  }

  protected ClassInfo clazz;

  private final Logger logger = CustomLoggerFactory.createLogger();

  private StringBuilder sb;

  protected CodeStyler styler;
}
