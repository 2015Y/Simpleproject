package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import run.Application;

/**
 * 类名称：ReadExcelToMapUtil.java 类描述 ：处理含有单个工作表的excel为List<Map<String,Object>>
 * 创建人: w_lengjunfeng 创建时间：2018年4月28日 修改人： 修改时间： 修改备注： 版本： V1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ReadExcelToListUtil {

	public static List<Map<String, Object>> readExcel(InputStream input, List<String> keyList) throws Exception {
		List<Map<String, Object>> resultList = new ArrayList<>();
		Workbook workbook = WorkbookFactory.create(input);
		Sheet sheet = workbook.getSheetAt(0);
		Map<String, Object> map = null;
		for (int j = 0; j <= sheet.getLastRowNum(); j++) {
			Row row = sheet.getRow(j);
			// 某一行的第一列为空就中断处理
			String one = ReadExcelUtil.getCellValue(row.getCell(0));
			if ("".equals(one)) {
				break;
			}
			String value = "";
			map = new HashMap<>();
			for (int k = 0; k < keyList.size(); k++) {
				value = ReadExcelUtil.getCellValue(row.getCell(k));
				map.put(keyList.get(k), value);
			}
			resultList.add(map);
		}
		return resultList;
	}

	@Test
	public void tes() throws Exception {
		File file = new File("D:/temp/test.xls");
		List<String> keyList = Arrays.asList("A", "B", "C", "D", "E", "F");
		List<Map<String, Object>> readLocalEccel = readExcel(new FileInputStream(file), keyList);
		for (Map<String, Object> map : readLocalEccel) {
			System.out.println(map);
		}
	}

}
