package controller;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import base.BaseController;
import lombok.extern.slf4j.Slf4j;
import pojo.User;
import service.LoginService;
import util.ShiroUtil;

@Slf4j
@Controller
public class LoginController<ShiroToken> extends BaseController {

	@Autowired
	LoginService loginService;

	@RequestMapping("/Error")
	String Error() {
		System.out.println(
				"------------------------------------------------------------------500------------------------------------------------------------------");
		return "Error";
	}

	@RequestMapping("/")
	String home(ModelMap model) {
		model.addAttribute("name", "GS");
		return "login";
	}

	@RequestMapping("/index")
	public String indexPage(ModelMap model) {
		log.info("请求登陆的首页.");
		model.addAttribute("name", "world");
		return "index";
	}

	@RequestMapping("/login")
	String login() {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		if (!pattern.matcher("1").matches()) {
			return "请输入正确格式的代号!";
		}
		return "login";
	}

	/**
	 * shiro框架登录
	 * 
	 * @param user
	 */
	@ResponseBody
	@RequestMapping(value = "/doLogin")
	public String login(User user, HttpSession session) {
		log.info("{}	请求登录.", user.getName());
		session.setAttribute("user", user);
		// 获取主体
		Subject subject = SecurityUtils.getSubject();
		try {
			// 调用安全认证框架的登录方法
			subject.login(new UsernamePasswordToken(user.getName(), user.getId()));
		} catch (AuthenticationException ex) {
			log.info("登陆失败: " + ex.getMessage());
			return "FAIL";
		}
		// 登录成功后重定向到首页
		return "SUCCESS";
	}

	@ResponseBody
	@RequestMapping("/ifLog")
	public String ifLog(HttpSession session) {
		if (session == null) {
			return "200";
		} else {
			return "404";
		}
	}

	@ResponseBody
	@RequestMapping("/logout")
	public void logOut(HttpSession session) {
		try {
			Subject subject = SecurityUtils.getSubject();
			subject.logout();
			session.invalidate();
		} catch (Exception e) {
		}
	}

	@ResponseBody
	@RequestMapping("/getUsers")
	Map<String, Object> getUsers(Integer page, Integer rows) {
		try {
			log.info("登陆的用户名:	" + ShiroUtil.getUserName());
		} catch (Exception e) {
		}
		return loginService.getUsers(page, rows);
	}

	@ResponseBody
	@RequestMapping("/addUser")
	String addUser() {
		int i = loginService.addUser();
		return "SUCCESS:用时:" + i + "s";
	}

	@ResponseBody
	@RequestMapping("/getDynamicData")
	Map<String, Object> getDynamicData(Integer page, Integer rows) {
		return loginService.getDynamicData(page, rows);
	}

	@ResponseBody
	@RequestMapping("/getDynamicColumns")
	Map<String, Object> getDynamicColumns() {
		return loginService.getDynamicColumns();
	}

	@ResponseBody
	@RequestMapping("/cancel")
	String cancel() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		request.getSession().invalidate();
		return "SUCCESS";
	}

	@ResponseBody
	@RequestMapping("/getUserByAge")
	public List<Map<String, Object>> getUserByAge(Integer age) {
		return loginService.getUsersByAge(age);
	}

	@ResponseBody
	@RequestMapping("/updateOne")
	public User updateOne(User user) {
		return loginService.updateOne(user);
	}

	@ResponseBody
	@RequestMapping("/findOne")
	public User findOne(User user) {
		user.setName("");
		user.setAge("0");
		return loginService.findOne(user);
	}

	@ResponseBody
	@RequestMapping("/deleteById")
	public User DeleteById(User user) {
		user.setName("");
		user.setAge("0");
		loginService.deleteById(user);
		return user;
	}

	@ResponseBody
	@RequestMapping("/findAge")
	public Object findAge(String age) {
		return loginService.findAge(age);
	}

	@ResponseBody
	@RequestMapping("/test")
	public User test(User user) {
		user.setName("0");
		user.setAge("0");
		loginService.test(user);
		return user;
	}

}
