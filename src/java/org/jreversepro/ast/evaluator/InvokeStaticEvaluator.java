package org.jreversepro.ast.evaluator;

import java.util.Arrays;
import java.util.List;

import org.jreversepro.ast.expression.Expression;
import org.jreversepro.ast.expression.MethodAccessExpression;
import org.jreversepro.ast.expression.StaticMethodAccessExpression;
import org.jreversepro.ast.intermediate.CompleteLine;
import org.jreversepro.jvm.TypeInferrer;
import org.jreversepro.reflect.instruction.Instruction;


public class InvokeStaticEvaluator  extends InvokeEvaluator {

  public InvokeStaticEvaluator(EvaluatorContext context) {
    super(context);
    // TODO Auto-generated constructor stub
  }

  @Override
  void evaluate(Instruction ins) {
    int offset = ins.getArgUnsignedShort();
    int classIndex = pool.getPtr1(offset);
    String classType = pool.getClassName(classIndex);

    // GetMethodName
    int nameIndex = pool.getPtr2(offset);
    String methodName = pool.getFirstDirectName(nameIndex);

    // Get No: of arguments
    int argsIndex = pool.getPtr2(nameIndex);
    String argsList = pool.getEntryValue(argsIndex);
    List<String> args = TypeInferrer.getArguments(argsList);
    int popMax = args.size();

    String methodType = TypeInferrer.getReturnType(argsList);
    // Get Return type

    List<Expression> argValues = this.getArguments(popMax);
    MethodAccessExpression mex = new StaticMethodAccessExpression(classType,
        methodName, methodType, argValues);

    if (!methodType.equals(String.valueOf(JVM_TYPE_VOID))) {
      // Non-void method.Push the result onto the stack.
      evalMachine.push(mex);
    } else {
      // Void return. Has to indicate End of line. 
      // Push -it as a statement.
      statements.append(new CompleteLine(ins, mex));
    }
    
  }

  @Override
  Iterable<Integer> getProcessingOpcodes() {
    return Arrays.asList(OPCODE_INVOKESTATIC);
  }

}
