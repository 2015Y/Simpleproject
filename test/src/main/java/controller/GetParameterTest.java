package controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetParameterTest {

	@RequestMapping("test1")
	public String test1(HttpServletRequest request) {
		Enumeration<String> paraNames = request.getParameterNames();

		for (Enumeration<String> e = paraNames; e.hasMoreElements();) {

			String thisName = e.nextElement().toString();

			String thisValue = request.getParameter(thisName);

			System.out.println(thisName + "--------------" + thisValue);

		}

		// 获取所有的头部参数

		Enumeration<String> headerNames = request.getHeaderNames();

		for (Enumeration<String> e = headerNames; e.hasMoreElements();) {

			String thisName = e.nextElement().toString();

			String thisValue = request.getHeader(thisName);

			System.out.println(thisName + "--------------" + thisValue);

		}

		// 测试session
		HttpSession session = request.getSession();
		System.out.println("sessionId--------------:" + session.getId());
		return "test1";
	}

}
