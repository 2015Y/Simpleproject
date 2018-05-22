package test;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class StringBuilderTest {

	@Test
	public void test1() {
		StringBuilder sb = new StringBuilder("1");
		sb.append("1");
		sb.append("2");
		sb.append("3");
		System.out.println(sb.reverse().toString());
	}

	@Test
	public void test2() {
		String string = "";
		String string1 = null;
		System.out.println(StringUtils.isBlank("      ".trim()));
		System.out.println(StringUtils.isBlank(null));
		System.out.println("	123   123");
		System.out.println("	123   123".replaceAll(" ", "").trim());
		System.out.println(StringUtils.isBlank(string) ? "这是个空的字符串" : string);
		System.out.println(StringUtils.isBlank(string1) ? "这是个空的字符串" : string1);
	}

}
