package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.concurrent.CountDownLatch;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * 类名称：ReadExcelByThreads.java 类描述 ： 创建人: w_lengjunfeng 创建时间：2018年6月13日 修改人：
 * 修改时间： 修改备注：多线程处理excel 版本： V1.0
 */
public class ReadExcelByThreads {

	// 记录启动并且运行中的线程数
	private volatile static int count = 0;
	// 记录启动线程的总数
	private volatile static int countNum = 0;
	// 设定运行中的最大的线程数
	private static final int maxNum = 4;
	// 设置每个线程处理数据的数量
	private static final int threadDealNum = 5000;
	// 测试的路径
	private static String testFilePath = "D:/temp/";
	// 是否输出到txt中
	private static boolean flag = false;
	// 计数主线程
	private static CountDownLatch countDownLatch = new CountDownLatch(1);

	public static void main(String[] args) {
		File file = new File("D:/temp/test.xls");
		try {
			readExcel(new FileInputStream(file), 0);
		} catch (Exception e) {
			System.out.println("处理excel失败!");
		}
	}

	public static synchronized void readExcel(InputStream file, int index) throws Exception {
		long startTime = System.currentTimeMillis();
		Workbook wb = WorkbookFactory.create(file);
		Sheet sheet = wb.getSheetAt(index);
		// 获取有效的行数
		int rowsNum = sheet.getLastRowNum() + 1;
		// 根据行数确定启动线程数量 但是不能超过设定的最大线程数 maxNum
		if (rowsNum <= threadDealNum) {
			// 少于threadDealNum条数据一个线程处理
			for (int i = 0; i < rowsNum; i++) {
				Row row = sheet.getRow(i);
				StringBuilder sb = new StringBuilder();
				for (int k = 0; k < row.getLastCellNum(); k++) {
					sb.append(ReadExcelUtil.getCellValue(row.getCell(k)));
				}
				appendTxt(testFilePath + Thread.currentThread().getName() + ".txt", sb.toString());
			}
			System.out.println("单线程处理" + rowsNum + "条excel数据用时:" + (System.currentTimeMillis() - startTime) + "ms");
		}
		if (rowsNum > threadDealNum) {
			CountDownLatch cdLatch = new CountDownLatch((rowsNum / threadDealNum) + 1);
			// 启动线程总数值为零
			countNumValueZero();
			// 根据数据数量启动线程
			int intNum = (rowsNum / threadDealNum) + 1;
			for (int i = 0; i < intNum; i++) {
				// 启动新的线程处理excel
				new Thread(new Runnable() {
					@Override
					public void run() {
						cdLatch.countDown();
						countNumAdd();
						countAdd();
						int maxIndex = ((countNum * threadDealNum) > rowsNum ? rowsNum : (countNum * threadDealNum));
						for (int i = (countNum - 1) * threadDealNum; i < maxIndex; i++) {
							StringBuilder sb = new StringBuilder();
							Row row = sheet.getRow(i);
							for (int k = 0; k < row.getLastCellNum(); k++) {
								sb.append(ReadExcelUtil.getCellValue(row.getCell(k)) + "	");
							}
							appendTxt(testFilePath + Thread.currentThread().getName() + ".txt", sb.toString());
						}
						countReduce();
					}
				}).start();
			}
			cdLatch.await();
			System.out.println("用" + ((rowsNum / threadDealNum) + 1) + "个线程处理excel用时:"
					+ (System.currentTimeMillis() - startTime) + "ms");
		}
	}

	public static void appendTxt(String filePath, String conent) {
		if (flag) {
			try {
				File file = new File(filePath);
				if (file.createNewFile()) {
					System.out.println("Create file successed");
				}
			} catch (Exception e) {
				System.err.println(e);
			}
			BufferedWriter out = null;
			try {
				out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true)));
				out.write(conent);
				out.newLine();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (out != null) {
						out.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 启动的总线程数归零
	public synchronized static void countNumValueZero() {
		countNum = 0;
	}

	// 启动的总线程数++
	public synchronized static void countNumAdd() {
		countNum++;
	}

	// 启动的总线程数--
	public synchronized static void countNumReduce() {
		countNum--;
	}

	// 启动中的总线程数++
	public synchronized static void countAdd() {
		count++;
	}

	// 启动的总线程数--
	public synchronized static void countReduce() {
		count--;
	}

}
