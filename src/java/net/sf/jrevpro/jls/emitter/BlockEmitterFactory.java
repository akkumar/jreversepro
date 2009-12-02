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
import java.util.logging.Logger;

import net.sf.jrevpro.CustomLoggerFactory;
import net.sf.jrevpro.ast.block.Block;

/**
 * 
 * @author karthikeyan c (bercolax)
 * 
 *         This class returns the specific <code>BlockEmitter</code> to a Block
 *         or its sub classes.
 * 
 * 
 * 
 */
public final class BlockEmitterFactory {

  private BlockEmitterFactory() {
  }

  // Caching can be done as all method invoction are stateless
  private static Map<Class<? extends Block>, BlockEmitter> emitterCache = new HashMap<Class<? extends Block>, BlockEmitter>();

  public static final BlockEmitter getBlockEmitter(Class<? extends Block> clazz)
      throws Exception {

    BlockEmitter emitter = emitterCache.get(clazz);

    // Do not worry about any synchronization here.. No harm done by
    // creating it twice or more
    // during the window of vulnerability when simultaneous requests are
    // coming in.
    if (emitter == null) {
      emitter = Class.forName(BlockEmitterContext
          .getBlockEmitterFQCN(clazz.getName())).asSubclass(BlockEmitter.class).newInstance();

      emitterCache.put(clazz, emitter);
    }
    return emitter;
  }

  private static Logger logger = CustomLoggerFactory.createLogger();

}
