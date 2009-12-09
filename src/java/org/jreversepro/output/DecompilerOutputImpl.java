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
package org.jreversepro.output;

import java.util.logging.Logger;

import org.jreversepro.CustomLoggerFactory;
import org.jreversepro.ast.block.Block;
import org.jreversepro.decompile.DecompilationContext;
import org.jreversepro.decompile.Decompiler;
import org.jreversepro.jls.emitter.SourceEmitter;
import org.jreversepro.jls.emitter.SourceEmitterFactory;
import org.jreversepro.jls.emitter.SourceEmitterFactory.JLSSource;
import org.jreversepro.reflect.ClassInfo;
import org.jreversepro.reflect.Method;


public class DecompilerOutputImpl extends AbstractClassOutputterImpl {

  protected DecompilerOutputImpl(ClassInfo _clazz, CodeStyler _styler) {
    super(_clazz, _styler);

  }

  @Override
  public void process() {
    clearContents();

    outputHeaderComments();
    outputPackageImports();

    outputThisSuperClasses();
    outputInterfaces();

    openBlock();

    outputFields();
    outputMethods();

    closeBlock();
  }

  /**
   * Outputs  the stringified decompiled method.
   * 
   *
   */
  protected void outputMethods() {

    for (Method method : clazz.getMethods()) {
      outputMethodHeader(method);
      openBlock();
      processMethod(method);
      closeBlock();
    }
  }

  public void processMethod(Method method) {
    DecompilationContext ctx = new DecompilationContext(method, clazz
        .getConstantPool());
    Decompiler decompiler = new Decompiler(ctx);
    Block block = decompiler.extractAST();
    SourceEmitter emitter = SourceEmitterFactory
        .getSourceEmitter(JLSSource.JDK14);
    outputString(emitter.emitCode(block));

  }

  private final Logger logger = CustomLoggerFactory.createLogger();

}
