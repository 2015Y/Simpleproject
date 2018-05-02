package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * 类名称：ReadExcelUtil 类描述 ： 创建人: 创建时间：2016年6月3日 上午10:11:45 修改人： 修改时间： 修改备注： 版本：
 * V1.0
 */
@SuppressWarnings("deprecation")
public final class ReadExcelUtil {

	private ReadExcelUtil() {
	}

	/**
	 * 获取合并单元格的值。若不是合并单元格，则获取row行column列位置的单元格的值。
	 * 
	 * @param sheet
	 *            sheet表
	 * @param row
	 *            行号，从0开始
	 * @param column
	 *            列号，从0开始
	 * @return
	 */
	public static String getMergedRegionValue(Sheet sheet, int row, int column) {
		// 1.检查是否不为合并单元格
		if (!isMergedRegion(sheet, row, column)) {
			Cell cell = getMergedRegionCell(sheet, row, column);
			return getCellValue(cell);
		}

		// 2.合并单元格
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();
			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					Row fRow = sheet.getRow(firstRow);
					Cell fCell = fRow.getCell(firstColumn);
					return getCellValue(fCell).trim();
				}
			}
		}
		return null;
	}

	/**
	 * 获取合并单元格区域的左上角单元格对象。若该位置不是合并单元格，则获取row行column列位置的单元格对象。
	 * 
	 * @param sheet
	 * @param row
	 *            所在行，行索引从0开始
	 * @param column
	 *            所在列，列索引从0开始
	 * @return
	 */
	public static Cell getMergedRegionCell(Sheet sheet, int row, int column) {
		// 1.检查当前单元格是否为合并单元格，不是合并单元格则直接获取cell对象
		if (!isMergedRegion(sheet, row, column)) {
			Row rowCell = sheet.getRow(row);
			Cell cell = rowCell.getCell(column);
			return cell;
		}
		// 2.是合并单元格则直接获取左上角单元格
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();
			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					Row fRow = sheet.getRow(firstRow);
					Cell fCell = fRow.getCell(firstColumn);
					return fCell;
				}
			}
		}
		return null;
	}

	/**
	 * 判断指定的单元格是否是合并单元格
	 * 
	 * @param sheet
	 * @param row
	 *            行下标
	 * @param column
	 *            列下标
	 * @return
	 */
	public static boolean isMergedRegion(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 获取单元格的值
	 * 
	 * @param cell
	 * @return
	 */
	public static String getCellValue(Cell cell) {
		if (cell == null)
			return "";
		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			return cell.getStringCellValue().trim();
		} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(cell.getBooleanCellValue());
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			// 实际最多支持8位小数，第8位小数由第9位四舍五入，Excel本身也只支持小数点后7位
			DecimalFormat format = new DecimalFormat("#.##########");
			String doubleStr = format.format(cell.getNumericCellValue());
			return doubleStr;
		} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
			return cell.getCellFormula();
		}
		return "";
	}

	/**
	 * 校验单元格中的是否为手机号 合并之后的单元格不能使用该方法校验，存在问题
	 * 
	 * @author dj
	 * @param cell
	 *            单元格对象
	 * @return
	 */
	public static boolean isMobileNumber(Cell cell) {
		boolean flag = false;
		if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BOOLEAN
				|| cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
			return flag;
		}
		String mobilePhone = "";
		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			mobilePhone = cell.getStringCellValue().trim();
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			double numericCellValue = cell.getNumericCellValue();
			mobilePhone = String.format("%11.0f", numericCellValue);
		}
		flag = ValidateUtil.isMobileNumber(mobilePhone);
		return flag;
	}

	/**
	 * 校验指定位置Cell是否为手机号
	 * 
	 * @author dj
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public static boolean isMobileNumber(Sheet sheet, int row, int column) {
		boolean flag = false;
		Cell cell = null;
		if (isMergedRegion(sheet, row, column)) {
			cell = getMergedRegionCell(sheet, row, column);
		} else {
			cell = sheet.getRow(row).getCell(column);
		}
		if (cell == null) {
			return flag;
		}
		return isMobileNumber(cell);
	}

	/**
	 * 获取单元格中的手机号
	 * 
	 * @param cell
	 *            单元格对象
	 * @return 返回手机号
	 * @throws RuntimeException
	 *             不是手机号则抛出异常
	 */
	public static String getMobileNumber(Cell cell) {
		if (cell == null || !isMobileNumber(cell)) {
			throw new RuntimeException("单元格中的内容不是手机号格式！");
		}
		String mobilePhone = "";
		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			mobilePhone = cell.getStringCellValue().trim();
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			double numericCellValue = cell.getNumericCellValue();
			mobilePhone = String.format("%11.0f", numericCellValue);
		}
		if (mobilePhone.length() == 0) {
			throw new RuntimeException("获取手机号失败！");
		}
		return mobilePhone;
	}

	/**
	 * 获取指定位置的手机号
	 * 
	 * @author dj
	 * @param sheet
	 *            sheet对象
	 * @param row
	 * @param column
	 * @return
	 * @throws RuntimeException
	 */
	public static String getMobileNumber(Sheet sheet, int row, int column) {
		Cell cell = null;
		if (isMergedRegion(sheet, row, column)) {
			cell = getMergedRegionCell(sheet, row, column);
		} else {
			cell = sheet.getRow(row).getCell(column);
		}
		return getMobileNumber(cell);
	}

	/**
	 * 校验cell单元格中的内容是否为日期，字符串为yyyy-MM-dd yyyy/MM/dd格式，或者Excel自带日期格式;
	 * 合并之后的单元格不能使用该方法校验，存在问题
	 * 
	 * @author dj
	 * @param cell
	 *            需要校验的单元格
	 * @return
	 */
	public static boolean isDate(Cell cell) {
		boolean flag = false;
		if (cell == null) {
			return flag;
		}
		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			String strDate = cell.getStringCellValue().trim();
			flag = ValidateUtil.isDate(strDate);
			return flag;
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			double doubleDate = cell.getNumericCellValue();
			Date date = DateUtil.getJavaDate(doubleDate);
			if (date == null) {
				return flag;
			} else {
				return true;
			}
		}
		return flag;
	}

	/**
	 * 判断指定位置是否为日期格式，字符串为yyyy-MM-dd yyyy/MM/dd格式，或者Excel自带日期格式
	 * 
	 * @author dj
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public static boolean isDate(Sheet sheet, int row, int column) {
		boolean flag = false;
		Cell cell = null;
		if (isMergedRegion(sheet, row, column)) {
			cell = getMergedRegionCell(sheet, row, column);
		} else {
			cell = sheet.getRow(row).getCell(column);
		}
		if (cell == null) {
			return flag;
		}
		return isDate(cell);
	}

	/**
	 * 获取日期对象<br />
	 * 字符串为yyyy-MM-dd yyyy/MM/dd格式，或者Excel自带日期格式
	 * 
	 * @author dj
	 * @param cell
	 *            单元格对象
	 * @return
	 */
	public static Date getDate(Cell cell) {
		if (cell == null || !isDate(cell)) {
			throw new RuntimeException("单元格中的内容不是正确的日期格式！");
		}
		Date cellDate = null;
		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			String strDate = cell.getStringCellValue().trim();
			// 第1种格式日期
			String format1 = "^[1-9]\\d[0-9]{2}-[0-1]{1}+[0-9]{1}-[0-3]{1}+[0-9]{1}$";
			// 第2种格式日期
			String format2 = "^[1-9]\\d[0-9]{2}/[0-1]{0,1}+[0-9]{1}/[0-3]{0,1}+[0-9]{1}$";
			if (Pattern.compile(format1).matcher(strDate).matches()) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				try {
					cellDate = dateFormat.parse(strDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else if (Pattern.compile(format2).matcher(strDate).matches()) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				try {
					cellDate = dateFormat.parse(strDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			double doubleDate = cell.getNumericCellValue();
			cellDate = DateUtil.getJavaDate(doubleDate);
		}
		// 日期格式错误则抛出运行时异常
		if (cellDate == null) {
			throw new RuntimeException("单元格中的内容不是正确的日期格式！");
		}
		return cellDate;
	}

	/**
	 * 获取日期对象
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 * @throws RuntimeException
	 */
	public static Date getDate(Sheet sheet, int row, int column) {
		Cell cell = null;
		if (isMergedRegion(sheet, row, column)) {
			cell = getMergedRegionCell(sheet, row, column);
		} else {
			cell = sheet.getRow(row).getCell(column);
		}
		return getDate(cell);
	}

	/**
	 * 校验单元格是否为整数，<br />
	 * 若有小数部分，小数部分只能是0
	 * 
	 * @author dj
	 * @param cell
	 * @return
	 */
	public static boolean isInteger(Cell cell) {
		boolean flag = false;
		if (cell == null) {
			return flag;
		}
		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			String strInteger = cell.getStringCellValue().trim();
			flag = ValidateUtil.isInteger(strInteger);
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			double numericCellValue = cell.getNumericCellValue();
			String strInteger = String.format("%f", numericCellValue);
			// 正负均可，若有小数部分，小数部分只能是0
			String regex = "^[-+]?\\d+(.0+)?$";
			flag = Pattern.compile(regex).matcher(strInteger).matches();
		}
		return flag;
	}

	/**
	 * 校验指定位置是否为整数格式，正负均可
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public static boolean isInteger(Sheet sheet, int row, int column) {
		Boolean flag = false;
		Cell cell = null;
		if (isMergedRegion(sheet, row, column)) {
			cell = getMergedRegionCell(sheet, row, column);
		} else {
			cell = sheet.getRow(row).getCell(column);
		}
		if (cell == null) {
			return flag;
		}
		return isInteger(cell);
	}

	/**
	 * 获取指定单元格的整数 被合并的单元格不适用
	 * 
	 * @author dj
	 * @param cell
	 * @return
	 */
	public static long getInteger(Cell cell) {
		if (cell == null || !isInteger(cell)) {
			throw new RuntimeException("单元格中的内容不是正确的整数格式！");
		}
		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			String strInteger = cell.getStringCellValue().trim();
			long number = Long.parseLong(strInteger);
			return number;
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			double numericCellValue = cell.getNumericCellValue();
			long number = (long) numericCellValue;
			return number;
		}
		throw new RuntimeException("单元格中的内容不是正确的整数格式！");
	}

	/**
	 * 从指定位置获取整数
	 * 
	 * @author dj
	 * @param sheet
	 * @param row
	 *            所在行
	 * @param column
	 *            所在列
	 * @return
	 */
	public static long getInteger(Sheet sheet, int row, int column) {
		Cell cell = null;
		if (isMergedRegion(sheet, row, column)) {
			cell = getMergedRegionCell(sheet, row, column);
		} else {
			cell = sheet.getRow(row).getCell(column);
		}
		return getInteger(cell);
	}

	/**
	 * 校验指定单元格是否为数值
	 * 
	 * @author dj
	 * @param cell
	 * @return
	 */
	public static boolean isNumber(Cell cell) {
		boolean flag = false;
		if (cell == null) {
			return flag;
		}
		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			flag = true;
		} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			String strInteger = cell.getStringCellValue();
			// 正负均可
			flag = isNumber(strInteger);
		}
		return flag;
	}

	/**
	 * 检查指定位置是否为数值格式
	 * 
	 * @author dj
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public static boolean isNumber(Sheet sheet, int row, int column) {
		Boolean flag = false;
		Cell cell = null;
		if (isMergedRegion(sheet, row, column)) {
			cell = getMergedRegionCell(sheet, row, column);
		} else {
			cell = sheet.getRow(row).getCell(column);
		}
		if (cell == null) {
			return flag;
		}
		return isNumber(cell);
	}

	/**
	 * 从指定位置获取数值
	 * 
	 * @param cell
	 * @return
	 */
	public static double getNumber(Cell cell) {
		if (cell == null || !isNumber(cell)) {
			throw new RuntimeException("单元格中的内容不是正确的数值格式！");
		}
		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			String strNumber = cell.getStringCellValue().trim();
			double numericCellValue = Double.parseDouble(strNumber);
			return numericCellValue;
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			double numericCellValue = cell.getNumericCellValue();
			return numericCellValue;
		}
		throw new RuntimeException("单元格中的内容不是正确的数值格式！");
	}

	/**
	 * 获取指定位置的单元格数值
	 * 
	 * @author dj
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public static double getNumber(Sheet sheet, int row, int column) {
		Cell cell = null;
		if (isMergedRegion(sheet, row, column)) {
			cell = getMergedRegionCell(sheet, row, column);
		} else {
			cell = sheet.getRow(row).getCell(column);
		}
		double result = getNumber(cell);
		return result;
	}

	/**
	 * 读取excel数据
	 * 
	 * @param filePath
	 * @return
	 */
	public static Workbook readExcelToWorkbook(String filePath) {
		Workbook wb = null;
		File file = new File(filePath);
		try (InputStream is = new FileInputStream(file);) {
			wb = WorkbookFactory.create(is);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wb;
	}

	/**
	 * 获取单元格在Excel中的坐标(xy) 例如： A1 B3
	 * 
	 * @param cell
	 * @return String
	 */

	public static String getCellAddress(Cell cell) {

		if (cell == null) {
			throw new RuntimeException("获取坐标的单元格不允许为空！");
		}
		CellAddress add = new CellAddress(cell);
		return add.formatAsString();
	}

	/**
	 * 校验是否是number类型
	 * 
	 * @param numberString 要检验的数值
	 * @return
	 */
	public static boolean isNumber(String numberString) {
		String regex = "^[+-]?\\d+.?\\d*$";
		boolean flag = Pattern.compile(regex).matcher(numberString).matches();
		return flag;
	}
	
}
