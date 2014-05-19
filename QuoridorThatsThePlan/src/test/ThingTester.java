package test;

import org.junit.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class ThingTester extends TestCase {
	public ThingTester (String name) {
		super (name);
	}
	
	public static void main(String[] args) {
		TestRunner.runAndWait(new TestSuite(ThingTester.class));
	}

	@Test
	public void testGetName() throws Exception {
	    String fileSpec = new String("c:xxxyyyzzz.txt");
	    assertEquals("zzz.txt", getName(fileSpec));
	}
	
	private String getName(String fileSpec) {
		return fileSpec;
	}
}