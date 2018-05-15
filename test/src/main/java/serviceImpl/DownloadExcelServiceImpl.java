package serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.stereotype.Service;

import service.DownloadExcelService;

@Service
public class DownloadExcelServiceImpl implements DownloadExcelService {
	
	@Override
	public HSSFWorkbook createWorkBook() {
		HSSFWorkbook wb = new HSSFWorkbook();
		// 设置样式
		HSSFCellStyle style = getStyle(wb);
		HSSFSheet test1Sheet = wb.createSheet("test1");
		HSSFRow testRow = test1Sheet.createRow((int) 0);
		String[] excelHeader = new String[] { "columnone", "columntwo", "columnthree", "columnfour", "columnfive" };
		for (int i = 0; i < excelHeader.length; i++) {
			HSSFCell cell = testRow.createCell(i);
			cell.setCellValue(excelHeader[i]);
			cell.setCellStyle(style);
			test1Sheet.autoSizeColumn(i);
			test1Sheet.setColumnWidth(i, 4000);
		}

		List<String> dataList = Arrays.asList("oneoneoneoneoneone", "twotwotwotwotwotwotwo", "threethreethreethreethreethree", "fourfourfourfourfourfourfour", "fivefivefivefivefivefive");
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

	public static HSSFCellStyle getStyle(HSSFWorkbook wb) {

		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFillForegroundColor((short) 13);// 设置背景色
		cellStyle.setFillBackgroundColor((short) 13);// 设置背景色
		
		cellStyle.setWrapText(true);// 设置自动换行
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		HSSFFont font = wb.createFont();
		font.setFontName("黑体");
		return cellStyle;
	}
	// HSSFWorkbook wb = new HSSFWorkbook();
	// HSSFSheet sheet = wb.createSheet();
	// HSSFCellStyle setBorder = wb.createCellStyle();
	// 一、设置背景色：
	// setBorder.setFillForegroundColor((short) 13);// 设置背景色
	// setBorder.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	// 二、设置边框:
	// setBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
	// setBorder.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
	// setBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
	// setBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
	// 三、设置居中:
	// setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
	// 四、设置字体:
	// HSSFFont font = wb.createFont();
	// font.setFontName("黑体");
	// font.setFontHeightInPoints((short) 16);//设置字体大小
	// HSSFFont font2 = wb.createFont();
	// font2.setFontName("仿宋_GB2312");
	// font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
	// font2.setFontHeightInPoints((short) 12);
	// setBorder.setFont(font);//选择需要用到的字体格式
	// 五、设置列宽:
	// sheet.setColumnWidth(0, 3766); //第一个参数代表列id(从0开始),第2个参数代表宽度值
	// 六、设置自动换行:
	// setBorder.setWrapText(true);//设置自动换行
	// 七、合并单元格:
	// Region region1 = new Region(0, (short) 0, 0, (short) 6);
	// //参数1：行号 参数2：起始列号 参数3：行号 参数4：终止列号
	// sheet.addMergedRegion(region1);
	// 八、加边框
	// HSSFCellStyle cellStyle= wookBook.createCellStyle();
	// cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	// cellStyle.setBorderBottom(HSSFCellStyle.BorderBORDER_MEDIUM);
	// cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
	// cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
	// cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
	// cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
	// cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
	// cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
	// cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
	// 另附:完整小例子一个
}
