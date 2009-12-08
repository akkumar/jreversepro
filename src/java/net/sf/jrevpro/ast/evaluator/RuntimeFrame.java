/**
 *  @(#) RuntimeFrame.java
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
package net.sf.jrevpro.ast.evaluator;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import net.sf.jrevpro.CustomLoggerFactory;
import net.sf.jrevpro.reflect.ConstantPool;
import net.sf.jrevpro.reflect.instruction.Instruction;
import net.sf.jrevpro.reflect.variabletable.VariableTable;

/**
 * @author akkumar
 * 
 */
public class RuntimeFrame {

  /**
   * @param pool
   *          ConstantPool Information
   * @param varTable
   *          Symbol Table reference.
   */
  public RuntimeFrame(ConstantPool pool, VariableTable varTable) {
    context = new EvaluatorContext(pool, varTable);

    mapEvaluatorInstances = new HashMap<Class<? extends AbstractInstructionEvaluator>, AbstractInstructionEvaluator>();

    initEvaluators();
 
  }

  private void initEvaluators() {

    mapEvaluatorTypes = new HashMap<Integer, Class<? extends AbstractInstructionEvaluator>>();

    registerEvaluator(NullEvaluator.class);
    registerEvaluator(AConstNullEvaluator.class);
    registerEvaluator(IConstEvaluator.class);
    registerEvaluator(LConstEvaluator.class);
    registerEvaluator(FConstEvaluator.class);
    registerEvaluator(DConstEvaluator.class);
    registerEvaluator(IPushEvaluator.class);
    registerEvaluator(LdcEvaluator.class);

    // Load Instructions
    registerEvaluator(ILoadEvaluator.class);
    registerEvaluator(LLoadEvaluator.class);
    registerEvaluator(FLoadEvaluator.class);
    registerEvaluator(DLoadEvaluator.class);
    registerEvaluator(ReferenceLoadEvaluator.class);
    registerEvaluator(ArrayIndexLoadEvaluator.class);

    // Store Instructions
    registerEvaluator(IStoreEvaluator.class);
    registerEvaluator(LongFloatDoubleStoreEvaluator.class);
    registerEvaluator(ReferenceStoreEvaluator.class);
    registerEvaluator(ArrayIndexStoreEvaluator.class);
    registerEvaluator(PopEvaluator.class);
    registerEvaluator(DupEvaluator.class);

    // Operators
    registerEvaluator(ArithmeticEvaluator.class);
    registerEvaluator(LogicalOpEvaluator.class);
    registerEvaluator(NegateEvaluator.class);
    registerEvaluator(IINCEvaluator.class);

    // Switch statements
    registerEvaluator(SwitchEvaluator.class);

    // Casting
    registerEvaluator(CastEvaluator.class);

    // Branches
    registerEvaluator(CompareEvaluator.class);
    registerEvaluator(ConditionUniOperatorEvaluator.class);
    registerEvaluator(ConditionBiOperatorEvaluator.class);
    registerEvaluator(ConditionNullEvaluator.class);
    registerEvaluator(GotoEvaluator.class);
    registerEvaluator(JSREvaluator.class);
    registerEvaluator(ReturnEvaluator.class);

    // Fields accessors
    registerEvaluator(StaticFieldReferenceEvaluator.class);
    registerEvaluator(InstanceFieldReferenceEvaluator.class);

    // Methods accessors
    registerEvaluator(InvokeEvaluator.class);

    // MonitorEnter / Monitor Exit
    registerEvaluator(MonitorEvaluator.class);
    registerEvaluator(NewArrayEvaluator.class);
    registerEvaluator(ReferenceTypeInfoEvaluator.class);

    // Unused
    registerEvaluator(UndefinedEvaluator.class);

  }

  private void registerEvaluator(
      Class<? extends AbstractInstructionEvaluator> clazz) {
    Constructor<? extends AbstractInstructionEvaluator> ctor;
    try {
      ctor = clazz.getDeclaredConstructor(EvaluatorContext.class);

      AbstractInstructionEvaluator eval = ctor.newInstance(context);
      Iterable<Integer> opcodes = eval.getProcessingOpcodes();
      for (Integer number : opcodes) {
        if (mapEvaluatorTypes.get(number) != null) {
          throw new RuntimeException("Opcode " + number
              + " alread registered by "
              + mapEvaluatorTypes.get(number).getClass().getName());
        }
        mapEvaluatorTypes.put(number, clazz);
      }

      mapEvaluatorInstances.put(clazz, eval);

    } catch (Exception e) {
      logger
          .severe(clazz
              + " is not a valid evaluator to be registered. It must have a public contructor that takes a InstructionContext as an argument");
      throw new RuntimeException(e);
    }
  }

  /**
   * @param ins
   *          Current Instruction that is to be operated on the JVM stack.
   * @throws RevEngineException
   *           Thrown in case we get any error while operating the current
   *           instruction on the current JVM stack.
   */
  public void evaluateInstruction(Instruction ins) {

    AbstractInstructionEvaluator eval = getEvaluator(ins.opcode);
    eval.evaluate(ins);
    context.setPreviousOpcode(ins.opcode);
  }

  AbstractInstructionEvaluator getEvaluator(int opcode) {
    Class<? extends AbstractInstructionEvaluator> clazz = mapEvaluatorTypes
        .get( opcode);
    if (clazz == null) {
      logger.severe("Opcode " + opcode + " does not have an evaluator");
      throw new RuntimeException("Opcode does not have an evaluator");
    }
    return mapEvaluatorInstances.get(clazz);
  }

  public EvaluatorContext getEvaluationContext() {
    return context;
  }

  private final EvaluatorContext context;

  private final Map<Class<? extends AbstractInstructionEvaluator>, AbstractInstructionEvaluator> mapEvaluatorInstances;

  /** Set of instruction evaluators * */
  private Map<Integer, Class<? extends AbstractInstructionEvaluator>> mapEvaluatorTypes;

  private final Logger logger = CustomLoggerFactory.createLogger();

}/* End of class */
