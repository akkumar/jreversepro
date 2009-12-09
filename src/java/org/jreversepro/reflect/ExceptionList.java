package org.jreversepro.reflect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * List of exceptions thrown by a given method.
 * 
 * @author karthik.kumar
 * 
 */
public class ExceptionList {

  
  private final List<ExceptionThrown> exceptions;

  public ExceptionList() {
    this.exceptions = new ArrayList<ExceptionThrown>();
  }

  /**
   * Returns a map. 
   * TODO: ??
   * 
   * @return Returns a map of exception tables.
   */
  public Map<Integer, String> getAllExceptionsAsMap() {
    final Map<Integer, String> newMap = new HashMap<Integer, String>();
    for (final ExceptionThrown exc : exceptions) {
      newMap.putAll(exc.excCatchTable);
    }
    return newMap;
  }

  /**
   * Add an exception block.
   * 
   * @param startPc
   *          Start of the try block
   * @param endPc
   *          End of try block
   * @param handlerPc
   *          Beginning of handler block
   * @param datatype
   *          Type of the class that the handler is going to handle.
   */
  public void addExceptionBlock(int startPc, int endPc, int handlerPc,
      String datatype) {

    ExceptionThrown exc = new ExceptionThrown(startPc, endPc,
        handlerPc, datatype);
    // Probably some changes to the keys put in the list.
    int tryIndex = exceptions.indexOf(exc);
    if (tryIndex == -1) {
      exceptions.add(exc);
    } else {
      ExceptionThrown oldTry = exceptions.get(tryIndex);
      oldTry.addCatchBlock(handlerPc, datatype);
    }
  }

  @Override
  public String toString() {
    final StringBuilder result = new StringBuilder();
    result.append("\n\n\t  /**");
    result.append("\n\t\tFrom  To  Handler\tClass\n");
    for (final ExceptionThrown exc : exceptions) {
      result.append(exc.toString());
    }
    result.append("\t  **/\n");
    return result.toString();
  }

}
