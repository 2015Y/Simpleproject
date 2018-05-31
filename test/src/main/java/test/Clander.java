package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Clander {

	public static Date calendarToData(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();// 日历类的实例化
		calendar.set(year, month - 2, day);// 设置日历时间，月份必须减一
		Date date = calendar.getTime(); // 从一个 Calendar 对象中获取 Date 对象
		return date;
	}

	public static void main(String[] args) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd");
		String str = "2011 08 23";
		Date dt = sdf.parse(str);
		GregorianCalendar gc = new GregorianCalendar();

		Calendar rightNow = Calendar.getInstance();
		for (int i = 0; i < 10; i++) {
			rightNow.setTime(dt);
			 rightNow.add(Calendar.YEAR, 0);// 日期减1年
			rightNow.add(Calendar.MONTH, i);// 日期加个月
			 rightNow.add(Calendar.DAY_OF_YEAR, 0);// 日期加10天
			Date dt1 = rightNow.getTime();
			String reStr = sdf.format(dt1);
			System.out.println(reStr);
			gc.setTime(dt);
			gc.add(Calendar.MONTH, 1);
			System.out.println(sdf.format(gc.getTime()));
			System.out.println(getOpeDate("2018-05-02 12:12:12",1));
		}
	}

	  public static String getOpeDate(String date, int dayNum) {
	        Date dt = null;
	        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        try {
	            dt = df.parse(date);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	        GregorianCalendar gc = new GregorianCalendar();
	        assert dt != null;
	        gc.setTime(dt);
	        gc.add(5, dayNum);
	        // gc.set(gc.YEAR,gc.get(gc.MONTH),gc.get(gc.DATE));
	        return String.valueOf(df.format(gc.getTime()));
	    }
	
}
