package controller;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.DownloadExcelService;
import util.R;

@Controller
public class ManageController {
	
	@Autowired
	private DownloadExcelService downloadExcelService ;
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
	
	@RequestMapping("/downloadExcel")
	@ResponseBody
	public void downloadExcel(HttpServletResponse response) throws Exception {
		//下载excel
		String filename = "test";
		HSSFWorkbook wb = downloadExcelService.createWorkBook();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment; filename=" + new String(filename.getBytes("gb2312"), "ISO-8859-1") + ".xls");
		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		ouputStream.flush();
		wb.close();
	}
	
}
