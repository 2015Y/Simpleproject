package util;

import java.util.regex.Pattern;

/**
 * @ClassName: ValidateUtil
 * @Description: 格式校验工具类
 * @author w_liuzhaohua
 * @date 2016年6月13日 下午4:01:16
 */
public class ValidateUtil {

	/**
	 * 手机号码校验(宽松校验,允许13、14、15、17、18开头的11位数字)
	 * 
	 * @author w_liuzhaohua
	 * @return true(Is Mobile Number) or false(Not Is Mobile Number)
	 */
	public static boolean isMobileNumber(String mobile) {
		String regex = "^1[34578]\\d{9}$";
		return Pattern.compile(regex).matcher(mobile).matches();
	}

	/**
	 * 邮箱有效性效验
	 * 
	 * @author w_liuzhaohua
	 * @return true or false
	 */
	public static boolean isEmailAddress(String email) {
		String regex = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		return Pattern.compile(regex).matcher(email).matches();
	}

	/**
	 * 校验金额是否符合规则(小数点前 ：Max :11位 Min:1位,小数点后：Max:6位 Min:0位)
	 * 
	 * @param amount
	 * @author w_liuzhaohua
	 * @return
	 */
	public static boolean isAmount(String amount) {
		String regex = "^[0-9]{1,11}+(.[0-9]{0,6})?$";
		return Pattern.compile(regex).matcher(amount).matches();

	}

	/**
	 * 校验是否是固话(格式：区号-座机号 or 区号-座机号-分机号)
	 * 
	 * @param telephone
	 * @author w_liuzhaohua
	 * @return
	 */
	public static boolean isTelePhone(String telephone) {
		// String regex = "^[0-9]{3,4}+-[0-9]{7}(|-[0-9]{1,4})$";
		String regex = "^(0[0-9]{2,3}\\-)?([1-9][0-9]{6,7})+(\\-[0-9]{1,4})?$";
		return Pattern.compile(regex).matcher(telephone).matches();
	}

	/**
	 * 校验字符串内容是否符合日期格式<br />
	 * 支持两种校验格式 yyyy-MM-dd yyyy/MM/dd
	 * 
	 * @param date
	 * @author w_liuzhaohua
	 */
	public static boolean isDate(String date) {
		String first = "^[1-9]\\d[0-9]{2}-[0-1]{1}+[0-9]{1}-[0-3]{1}+[0-9]{1}$";
		String second = "^[1-9]\\d[0-9]{2}/[0-1]{0,1}+[0-9]{1}/[0-3]{0,1}+[0-9]{1}$";
		if (!Pattern.compile(first).matcher(date).matches()) {
			return Pattern.compile(second).matcher(date).matches();
		}
		return Pattern.compile(first).matcher(date).matches();
	}

	/**
	 * 效验值是否为数字与小数点组成及小数点的精度
	 * 
	 * @param exchangeRate
	 *            汇率字符串
	 * @param accurate
	 *            精确的位数
	 * @author w_liuzhaohua
	 */
	public static boolean isExchangeRate(String exchangeRate, Integer accurate) {
		String regex = "^[0-9]{1,11}+(.[0-9]{0," + accurate + "})?$";
		return Pattern.compile(regex).matcher(exchangeRate).matches();
	}

	/**
	 * 校验是否是数字，包括正整数和负整数
	 * 
	 * @param value
	 * @author liuzh
	 */
	public static boolean isInteger(String value) {
		String regex = "^[+-]?\\d+$";
		return Pattern.compile(regex).matcher(value).matches();
	}
}
