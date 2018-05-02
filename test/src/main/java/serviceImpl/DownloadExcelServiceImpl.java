package serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import service.DownloadExcelService;

@Service
public class DownloadExcelServiceImpl implements DownloadExcelService {

	@Override
	public HSSFWorkbook createWorkBook() {
		HSSFWorkbook wb = new HSSFWorkbook();
		//设置样式
		HSSFCellStyle style = getStyle(wb);
		HSSFSheet test1Sheet = wb.createSheet("test1");
		HSSFRow testRow = test1Sheet.createRow((int) 0);
		String[] excelHeader = new String[] { "test1", "test1", "test1", "test1", "test1" };
		for (int i = 0; i < excelHeader.length; i++) {
			HSSFCell cell = testRow.createCell(i);
			cell.setCellValue(excelHeader[i]);
			cell.setCellStyle(style);
			test1Sheet.autoSizeColumn(i);
			test1Sheet.setColumnWidth(i, 4000);
		}
		
		List<String> dataList = Arrays.asList("one", "two","three","four","five");
		List<List<String>> list = new ArrayList<>();
		list.add(dataList);
		list.add(dataList);
		list.add(dataList);
		list.add(dataList);
		list.add(dataList);
		list.add(dataList);
		for (int i = 0; i < list.size(); i++) {
			testRow = test1Sheet.createRow(i + 1);
			for (int k = 0; k < dataList.size(); k++) {
				testRow.createCell(k).setCellValue(list.get(i).get(k));
			}
		}
		return wb;
	}
	
	public  static  HSSFCellStyle getStyle(HSSFWorkbook wb) {
		
		HSSFCellStyle cellStyle = wb.createCellStyle();    	  
		  
		cellStyle.setFillForegroundColor((short) 13);// 设置背景色    
		  
		cellStyle.setWrapText(true);//设置自动换行
		
		return cellStyle ;
	}
	
}
