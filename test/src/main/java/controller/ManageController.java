package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import util.R;

@Controller
public class ManageController {
	
	@RequestMapping("/manage")
		public String manage() {
			//进入管理页面
			return "manage/manage";
		}
	
	@RequestMapping("/testForm")
	@ResponseBody
	public R testForm(String data) {
		//测试form提交重父的数据
		System.out.println(data);
		return R.ok(data);
	}
	
}
