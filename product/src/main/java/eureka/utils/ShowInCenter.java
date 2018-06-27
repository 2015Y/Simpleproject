package eureka.utils;

public class ShowInCenter {

	public static String showInCenter(String str) {
		StringBuilder sb = new StringBuilder();
		sb.append("<div align='center'>");
		sb.append("<h3>");
		sb.append(str);
		sb.append("</h3>");
		sb.append("</div>");
		return sb.toString();
	}

}
