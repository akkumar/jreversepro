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
 *  * 
 */
package net.sf.jrevpro.jvm;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TypeInferrerTest implements JVMConstants {

  @Test
  public void testBasicType() {
    assertTrue("Test for Basic Type INT ", TypeInferrer.isBasicType(String
        .valueOf(JVM_TYPE_INT)));
    assertTrue("Test for Basic Type Boolean ", TypeInferrer.isBasicType(String
        .valueOf(JVM_TYPE_BOOLEAN)));
    assertTrue("Test for Basic Type Byte ", TypeInferrer.isBasicType(String
        .valueOf(JVM_TYPE_BYTE)));
    assertTrue("Test for Basic Type Char ", TypeInferrer.isBasicType(String
        .valueOf(JVM_TYPE_CHAR)));
    assertTrue("Test for Basic Type Short ", TypeInferrer.isBasicType(String
        .valueOf(JVM_TYPE_SHORT)));
    assertTrue("Test for Basic Type Float ", TypeInferrer.isBasicType(String
        .valueOf(JVM_TYPE_FLOAT)));
    assertTrue("Test for Basic Type Long ", TypeInferrer.isBasicType(String
        .valueOf(JVM_TYPE_LONG)));
    assertTrue("Test for Basic Type DOUBLE ", TypeInferrer.isBasicType(String
        .valueOf(JVM_TYPE_DOUBLE)));
    assertFalse("Test for Basic Type ARRAY ", TypeInferrer.isBasicType(String
        .valueOf(JVM_TYPE_ARRAY)));

  }

}
