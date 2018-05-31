package config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

/**
 * 类名称：GlobalDefaultExceptionHandler.java 类描述 ：异常处理 创建人: w_lengjunfeng
 * 创建时间：2018年2月7日 修改人： 修改时间： 修改备注： 版本： V1.0
 */

@Slf4j
@ControllerAdvice
public class GlobalDefaultExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public String defaultExceptionHandler(HttpServletRequest req, Exception e) {
		// 是返回的String.
		// 异常产生的位置 最底层
		StackTraceElement stackTranceElement = e.getStackTrace()[0];
		String className = stackTranceElement.getClassName();
		int lineNumber = stackTranceElement.getLineNumber();
		String methodName = stackTranceElement.getMethodName();
		log.error("异常的类型:  {}.", e.toString());
		log.error("异常出现类的名字:  {}.", className);
		log.error("异常方法的名字:  {}.", methodName);
		log.error("异常出现的行数:  {}", lineNumber);
		return "程序出BUG了,杀个程序员祭天吧!";
	}

}
