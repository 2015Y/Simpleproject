package eureka.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetTime {

	public static String getTimeNow() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}

}
