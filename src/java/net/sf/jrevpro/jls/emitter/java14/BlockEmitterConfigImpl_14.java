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
package net.sf.jrevpro.jls.emitter.java14;

import java.util.HashMap;
import java.util.Map;

import net.sf.jrevpro.jls.emitter.BlockEmitterConfig;

/**
 * 
 * @author karthikeyanc
 *
 */
public class BlockEmitterConfigImpl_14 extends BlockEmitterConfig {
    
   
    public  Map<String, String> getConfig() {
	Map<String, String> map=new HashMap<String, String>();
	map.put("net.sf.jrevpro.ast.block.MethodBlock", "net.sf.jrevpro.jls.emitter.java14.MethodEmitter");
	map.put("net.sf.jrevpro.ast.block.Statement", "net.sf.jrevpro.jls.emitter.java14.StatementEmitter");
	return map;
    }

}
