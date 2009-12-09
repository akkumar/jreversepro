package net.sf.jrevpro.ast.evaluator;

import java.util.Arrays;
import java.util.List;

import net.sf.jrevpro.ast.expression.Expression;
import net.sf.jrevpro.ast.expression.InstanceMethodAccessExpression;
import net.sf.jrevpro.ast.expression.MethodAccessExpression;
import net.sf.jrevpro.jvm.TypeInferrer;
import net.sf.jrevpro.reflect.instruction.Instruction;

public class InvokeSpecialEvaluator extends InvokeEvaluator {

  public InvokeSpecialEvaluator(EvaluatorContext context) {
    super(context);
    // TODO Auto-generated constructor stub
  }

  @Override
  void evaluate(Instruction ins) {
    int offset = ins.getArgUnsignedShort();
    int classIndex = pool.getPtr1(offset);

    int nameIndex = pool.getPtr2(offset);
    String methodName = pool.getFirstDirectName(nameIndex);
    String className = pool.getClassName(classIndex);
    String argsList = pool.getEntryValue(pool.getPtr2(nameIndex));

    List<String> jvmArgTypes = TypeInferrer.getArguments(argsList);
    int popMax = jvmArgTypes.size();
    // Equals Number of Arguments

    String methodType = className;
    // possibly a constructor.
    // then the type is the same as className
    List<Expression> argValues = this.getArguments(popMax);

    Expression accessTarget = evalMachine.pop();

    MethodAccessExpression mex = new InstanceMethodAccessExpression(
        accessTarget, methodName, methodType, argValues);
    evalMachine.push(mex);
  }

  @Override
  Iterable<Integer> getProcessingOpcodes() {
    return Arrays.asList(OPCODE_INVOKESPECIAL);
  }

}
