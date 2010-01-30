package org.jreversepro.input;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Hello {

  static int variable;
  
  public static void main(String[] args1) throws FileNotFoundException {
    @SuppressWarnings("unused")
    FileInputStream fst = new FileInputStream("a.txt");
  }

  public void testIfCondition1(int compiledLongName) {
    int a = 1;
    int b = 2;

    if (a < b) {
      b = 3;
    }
  }
  
  static { 
    Hello.variable = 2;
  }
}
