package net.sf.jrevpro.ast.evaluator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.List;

import net.sf.jrevpro.ast.expression.Expression;
import net.sf.jrevpro.ast.expression.InstanceMethodAccessExpression;
import net.sf.jrevpro.ast.expression.MethodAccessExpression;
import net.sf.jrevpro.jvm.TypeInferrer;
import net.sf.jrevpro.reflect.instruction.Instruction;

public class InvokeSpecialEvaluator extends AbstractInstructionEvaluator {

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
    List<Expression> argValues = new ArrayList<Expression>(popMax);
    for (int i = popMax - 1; i >= 0; i--) {
      // add arguments in reverse order
      argValues.add(0, evalStack.pop());
    }

    Expression accessTarget = evalStack.pop();

    MethodAccessExpression mex = new InstanceMethodAccessExpression(
        accessTarget, methodName, methodType, argValues, true);
    // Peek the top and replace the top object reference. Stack remains
    // the same.
    try {
      evalStack.pop(); // Popped expression is not needed. we are just
      // replacing it.
      evalStack.push(mex);
    } catch (EmptyStackException ex) {
      logger.warning("invokespecial: Cannot peek the stack when pushing "
          + mex.getJLSCode());
    }
  }

  @Override
  Iterable<Integer> getProcessingOpcodes() {
    return Arrays.asList(OPCODE_INVOKESPECIAL);
  }

}
