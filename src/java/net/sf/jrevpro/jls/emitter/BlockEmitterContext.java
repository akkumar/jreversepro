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
package net.sf.jrevpro.jls.emitter;

import java.util.HashMap;
import java.util.Map;

import net.sf.jrevpro.JavaDecompileVersionContext;
import net.sf.jrevpro.JavaDecompileVersionContext.JAVA_VERSION;
import net.sf.jrevpro.jls.emitter.java14.BlockEmitterConfigImpl_14;

/**
 * 
 * @author karthikeyanc Decides which version of Java the emitter is requested
 *         and returns the specific Emitter.
 */
public final class BlockEmitterContext {

  private BlockEmitterContext() {
  }

  private static final Map<JAVA_VERSION, BlockEmitterConfig> configMap = init();

  public static String getBlockEmitterFQCN(String blockFCQN) {
    return configMap.get(
        JavaDecompileVersionContext.getJavaVersionToDecompile()).getConfig()
        .get(blockFCQN);
  }

  private static Map<JAVA_VERSION, BlockEmitterConfig> init() {
    Map<JAVA_VERSION, BlockEmitterConfig> configMap = new HashMap<JAVA_VERSION, BlockEmitterConfig>();
    configMap.put(JAVA_VERSION.JAVA_1_4, getJava14Config());
    // Java 5 and 6 config go here
    // TODO
    return configMap;
  }

  private static BlockEmitterConfig getJava14Config() {
    return new BlockEmitterConfigImpl_14();
  }

}
