package test;

import org.junit.Test;

public class CharSequenceTest {

	@Test
	public void test() {
		for (;;) {
			System.out.println("这是啥意思");
		}
	}

	public static boolean isBlank(final CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		System.out.println(cs.length());
		for (int i = 0; i < strLen; i++) {
			System.out.println("第" + (i + 1) + "个char:" + cs.charAt(i));
		}
		return true;
	}

}
