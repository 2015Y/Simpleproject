package eureka.utils;

import org.apache.commons.logging.LogFactory;

public class LogUtil {

	@SuppressWarnings("rawtypes")
	public static org.apache.commons.logging.Log getLog(Class clazz) {
		return LogFactory.getLog(clazz);
	}

}
