/**
 * JReversePro - Java Decompiler / Disassembler. Copyright (C) 2008 Karthik Kumar. Licensed under
 * the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in
 * writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */
package net.sf.jrevpro;

/**
 * @author karthikeyanc
 */
public final class JavaDecompileVersionContext
{

  private JavaDecompileVersionContext()
  {
  }

  private static final JavaDecompileVersionContext ctx = new JavaDecompileVersionContext();

  public enum JAVA_VERSION
  {
    JAVA_1_4,
    JAVA_5,
    JAVA_6
  }


  public static final void setJavaVersionToDecompile(final String version)
  {
    parseJavaVersionToDecompile(version);
  }


  public static JAVA_VERSION getJavaVersionToDecompile()
  {
    return ctx.getJavaVersion();
  }


  private static final void parseJavaVersionToDecompile(final String userInput)
  {
    // TODO implementation is to be provided
    // till then setting it as java 1.4

    ctx.setJavaVersion(JAVA_VERSION.JAVA_1_4);
  }

  private JAVA_VERSION javaVersion = null;


  private JAVA_VERSION getJavaVersion()
  {
    return this.javaVersion;
  }


  private void setJavaVersion(final JAVA_VERSION javaVersion)
  {
    this.javaVersion = javaVersion;
  }

}
