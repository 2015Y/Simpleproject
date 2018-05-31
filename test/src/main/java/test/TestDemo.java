package test;

public class TestDemo {

	public static void main(String[] args) {
		String string = "string";
		System.out.println(string.hashCode());
		String aString = "aString";
		string += aString;
		System.out.println(string.hashCode());
		
		String abcd = "a"+"b"+"c"+"d";
		System.out.println(abcd=="abcd");
	}

}
