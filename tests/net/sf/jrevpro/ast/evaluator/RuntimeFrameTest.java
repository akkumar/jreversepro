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
package net.sf.jrevpro.ast.evaluator;

import static org.junit.Assert.assertNotNull;
import net.sf.jrevpro.jvm.JVMInstructionSet;
import net.sf.jrevpro.reflect.ConstantPool;
import net.sf.jrevpro.reflect.Method;
import net.sf.jrevpro.reflect.variabletable.VariableTable;

import org.junit.Test;

/**
 * @author akkumar
 * 
 */
public class RuntimeFrameTest {

	public RuntimeFrameTest() {
		pool = new ConstantPool(10);

		Method method = new Method();
		method.setName("noname");
		method.setSignature("(II)V");
		method.setMaxLocals(10);
		method.initializeSymbolTable();

		varTable = method.getVariableTable();
	}

	@Test
	public void testInstantiation() {
		run = new RuntimeFrame(pool, varTable);
		assertNotNull("Failed to instante runtime frame", run);
		for (int i = 0; i < 255; ++i) {
			AbstractInstructionEvaluator eval = run.getEvaluator(i);
			assertNotNull("Retrieving evaluator for " + i, eval);
			System.out.println(eval.getClass().getName() + "\t"
					+ JVMInstructionSet.getOpcodeString(i));
		}
	}

	ConstantPool pool;
	VariableTable varTable;

	RuntimeFrame run;
}
