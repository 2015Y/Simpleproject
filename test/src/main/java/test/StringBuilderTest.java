package test;

import org.junit.Test;

public class StringBuilderTest {
	
	@Test
	public void test() {
		StringBuilder sb = new StringBuilder("1");
		sb.append("1");
		sb.append("2");
		sb.append("3");
		System.out.println(sb.reverse().toString());
	}
	
}
