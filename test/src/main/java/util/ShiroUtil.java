package util;

import org.apache.shiro.SecurityUtils;

import pojo.User;

public class ShiroUtil {

	public static User getUser() {
		User user = (User)SecurityUtils.getSubject().getPrincipal();
		return user ;
	}
	
	public static String  getUserID() {
		return getUser().getId();
	}
	
	public static String getUserName() {
		return  getUser().getName();
	}
	
}
