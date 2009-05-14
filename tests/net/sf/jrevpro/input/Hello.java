package net.sf.jrevpro.input;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Hello {

	public static void main(String[] args) throws FileNotFoundException {
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
}
