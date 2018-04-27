package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import lombok.extern.slf4j.Slf4j;
import run.Application;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class CreateMysqlEntityUtil {

	private final String driverName = "com.mysql.jdbc.Driver";

	private final String user = "root";// 用户

	private final String password = "gsgogo123";// 密码

	private final String url = "jdbc:mysql://localhost:3306/springboot?characterEncoding=utf8";// 数据库地址

	// Mapper文件生成地址
	private final String mapper_path = "d:/temp/mapper";

	// xml生成地址
	private final String xml_path = "d:/temp/xml";

	// service生成地址
	private final String service_path = "d:/temp/service";

	// serviceImpl 生成的地址
	private final String map_path = "d:/temp/map";

	// pojo 生成的地址
	private final String pojo_path = "d:/temp/pojo";// 生成的pojo地址

	// map 生成的地址
	private final String service_impl_path = "d:/temp/serviceImpl";

	// service 包
	private final String service_package = "com.test.service";

	// serviceImpl 包
	private final String service_impl_package = "com.test.service.impl";

	// xml的命名空间
	private final String mapper_package = "com.test.mapper";

	// pojo包
	private static String package_path = "com.test.pojo";

	// 是否需要导入包java.util.*
	private static boolean f_util = false;

	// 是否需要导入包java.sql.*
	private static boolean f_sql = false;

	private String tableName = null;

	// private String beanName = null;

	private String mapperName = null;

	private String serviceName = null;

	private static String[] colnames; // 列名数组

	private static String[] colTypes; // 列名类型数组

	private static int[] colSizes; // 列名大小数组

	private Connection conn = null;

	private void init() throws ClassNotFoundException, SQLException {
		Class.forName(driverName);
		conn = DriverManager.getConnection(url, user, password);
	}

	/**
	 * 获取所有的表
	 *
	 * @return
	 * @throws SQLException
	 */
	private List<String> getTables() throws SQLException {
		List<String> tables = new ArrayList<String>();
		PreparedStatement pstate = conn.prepareStatement("show tables");
		ResultSet results = pstate.executeQuery();
		while (results.next()) {
			String tableName = results.getString(1);
			tables.add(tableName);
		}
		return tables;
	}

	// 处理表对应Mapper名字
	private void processTable(String table) {
		if (table != null) {
			mapperName = getMapperName(table) + "Mapper";
			serviceName = getMapperName(table) + "Service";
		}
	}

	// 处理表名带_字段为驼峰式的命名 如:test_id处理为:testId
	private String getMapperName(String table) {
		String str = table.substring(0, 1).toUpperCase();
		StringBuffer sb = new StringBuffer(table.length());
		table = table.toLowerCase();
		String[] fields = table.split("_");
		String temp = null;
		sb.append(fields[0]);
		for (int i = 1; i < fields.length; i++) {
			temp = fields[i].trim();
			sb.append(temp.substring(0, 1).toUpperCase()).append(temp.substring(1));
		}
		String resultStr = str + sb.toString().substring(1, sb.length());
		return resultStr;
	}

	// 处理数据库中带_字段为驼峰式的命名 如:test_id处理为:testId
	private String processField(String field) {
		// StringBuffer sb = new StringBuffer(field.length());
		// field = field.toLowerCase();
		// String[] fields = field.split("_");
		// String temp = null;
		// sb.append(fields[0]);
		// for (int i = 1; i < fields.length; i++) {
		// temp = fields[i].trim();
		// sb.append(temp.substring(0,
		// 1).toUpperCase()).append(temp.substring(1));
		// }
		// return sb.toString();

		return field;
	}

	/**
	 * 构建类上面的注释
	 *
	 * @param bw
	 * @param text
	 * @return
	 * @throws IOException
	 */
	private BufferedWriter buildClassComment(BufferedWriter bw, String text) throws IOException {
		bw.newLine();
		bw.newLine();
		bw.write("/**");
		bw.newLine();
		bw.write(" * ");
		bw.newLine();
		bw.write(" * " + text);
		bw.newLine();
		bw.write(" * ");
		bw.newLine();
		bw.write(" **/");
		return bw;
	}

	/**
	 * 构建方法上面的注释
	 *
	 * @param bw
	 * @param text
	 * @return
	 * @throws IOException
	 */
	private BufferedWriter buildMethodComment(BufferedWriter bw, String text) throws IOException {
		bw.newLine();
		bw.write("\t/**");
		bw.newLine();
		bw.write("\t * ");
		bw.newLine();
		bw.write("\t * " + text);
		bw.newLine();
		bw.write("\t * ");
		bw.newLine();
		bw.write("\t **/");
		return bw;
	}

	/*
	 * 构建service文件
	 *
	 */
	private void buildService() throws IOException {
		File folder = new File(service_path);
		if (!folder.exists()) {
			folder.mkdirs();
		}

		File mapperFile = new File(service_path, serviceName + ".java");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperFile), "utf-8"));
		bw.write("package " + service_package + ";");
		bw.newLine();
		bw.write("import java.util.List;");
		bw.newLine();
		bw.write("import java.util.Map;");
		bw.newLine();
		bw = buildClassComment(bw, serviceName + "接口");
		bw.newLine();
		bw.newLine();
		bw.write("public interface " + serviceName + " {");
		bw.newLine();
		bw.newLine();
		// ----------定义service中的方法Begin----------
		bw.newLine();
		bw = buildMethodComment(bw, "删除（根据主键ID删除）");
		bw.newLine();
		bw.write("\t" + "int deleteByPrimaryKey (String id );");
		bw.newLine();
		bw = buildMethodComment(bw, "根据map添加单条数据");
		bw.newLine();
		bw.write("\t" + "int insertByMap(Map<String,Object> map );");
		bw.newLine();
		bw = buildMethodComment(bw, "修改（根据主键ID修改）");
		bw.newLine();
		bw.write("\t" + "int updateByMap (Map<String,Object> map );");
		bw.newLine();
		bw = buildMethodComment(bw, "根据ArrayList批量添加");
		bw.newLine();
		bw.write("\t" + "int insertByList (List<Map<String,Object>> list );");
		bw.newLine();
		bw = buildMethodComment(bw, "根据Map查询");
		bw.newLine();
		bw.write("\t" + "List<Map<String,Object>> queryByMap (Map<String,Object> map );");
		bw.newLine();
		bw = buildMethodComment(bw, "根据List<String>批量删除(list是主键id的集合)");
		bw.newLine();
		bw.write("\t" + "int deleteByList (List<String> list );");
		bw.newLine();
		bw.newLine();
		bw = buildMethodComment(bw, "根据List<Map<String,Object>>中主键id批量更新(list是主键id的集合)");
		bw.newLine();
		bw.write("\t" + "int updateByList (List<Map<String,Object>> list );");
		bw.newLine();
		// ----------定义service中的方法End----------
		bw.newLine();
		bw.write("}");
		bw.flush();
		bw.close();
	}

	/*
	 * 构建serviceImpl文件
	 *
	 */
	private void buildServiceImpl() throws IOException {
		File folder = new File(service_impl_path);
		if (!folder.exists()) {
			folder.mkdirs();
		}

		File mapperFile = new File(service_impl_path, serviceName + "Impl.java");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperFile), "utf-8"));
		bw.write("package " + service_impl_package + ";");
		bw.newLine();
		bw.newLine();
		bw.write("import java.util.List;");
		bw.newLine();
		bw.write("import java.util.Map;");
		bw.newLine();
		bw.write("import org.springframework.stereotype.Service;");
		bw.newLine();
		bw.write("import org.springframework.transaction.annotation.Transactional;");
		bw.newLine();
		bw.write("import org.springframework.beans.factory.annotation.Autowired;");
		bw.newLine();
		bw.write("import " + mapper_package + "." + mapperName + ";");
		bw.newLine();
		bw.write("import " + service_package + "." + serviceName + ";");
		bw.newLine();
		bw = buildClassComment(bw, serviceName + "接口实现");
		bw.newLine();
		bw.newLine();
		bw.write("@Service");
		bw.newLine();
		bw.write("@Transactional");
		bw.newLine();
		bw.write("public class " + serviceName + "Impl  implements " + serviceName + " {");
		bw.newLine();
		bw.newLine();
		bw.write("\t" + "@Autowired");
		bw.newLine();
		bw.write("\t" + "private  " + mapperName + " " + mapperName + ";");
		bw.newLine();
		bw.newLine();
		bw = buildMethodComment(bw, "删除（根据主键ID删除）");
		bw.newLine();
		bw.write("@Override");
		bw.newLine();
		bw.write("public int deleteByPrimaryKey (String id ){ ");
		bw.newLine();
		bw.write("\t" + "return " + mapperName + ".deleteByPrimaryKey(id);");
		bw.newLine();
		bw.write("} ");
		bw.newLine();
		bw = buildMethodComment(bw, "根据map添加单条数据");
		bw.newLine();
		bw.write("@Override");
		bw.newLine();
		bw.write("public int insertByMap(Map<String,Object> map ){ ");
		bw.newLine();
		bw.write("\t" + "return " + mapperName + ".insertByMap(map);");
		bw.newLine();
		bw.write("} ");
		bw.newLine();
		bw = buildMethodComment(bw, "修改（根据主键ID修改）");
		bw.newLine();
		bw.write("@Override");
		bw.newLine();
		bw.write("public int updateByMap (Map<String,Object> map  ){ ");
		bw.newLine();
		bw.write("\t" + "return " + mapperName + ".updateByMap(map);");
		bw.newLine();
		bw.write("} ");
		bw.newLine();
		bw = buildMethodComment(bw, "根据ArrayList批量添加");
		bw.newLine();
		bw.write("@Override");
		bw.newLine();
		bw.write("public int insertByList (List<Map<String,Object>> list ){ ");
		bw.newLine();
		bw.write("\t" + "return " + mapperName + ".insertByList(list);");
		bw.newLine();
		bw.write("} ");
		bw.newLine();
		bw = buildMethodComment(bw, "根据Map查询");
		bw.newLine();
		bw.write("@Override");
		bw.newLine();
		bw.write("public List<Map<String,Object>> queryByMap (Map<String,Object> map ){ ");
		bw.newLine();
		bw.write("\t" + "return " + mapperName + ".queryByMap(map);");
		bw.newLine();
		bw.write("} ");
		bw.newLine();
		bw = buildMethodComment(bw, "根据List<String>批量删除(list是主键id的集合)");
		bw.newLine();
		bw.write("@Override");
		bw.newLine();
		bw.write("public int deleteByList (List<String> list ){ ");
		bw.newLine();
		bw.write("\t" + "return " + mapperName + ".deleteByList(list);");
		bw.newLine();
		bw.write("} ");
		bw.newLine();
		bw = buildMethodComment(bw, "根据List<Map<String,Object>>根据主键ID批量更新(list是主键id的Map集合)");
		bw.newLine();
		bw.write("@Override");
		bw.newLine();
		bw.write("public int updateByList (List<Map<String,Object>> list ){ ");
		bw.newLine();
		bw.write("\t" + "return " + mapperName + ".updateByList(list);");
		bw.newLine();
		bw.write("} ");
		bw.newLine();
		bw.write("} ");
		bw.flush();
		bw.close();
	}

	/**
	 * 构建Mapper文件
	 *
	 * @throws IOException
	 */
	private void buildMapper() throws IOException {
		File folder = new File(mapper_path);
		if (!folder.exists()) {
			folder.mkdirs();
		}

		File mapperFile = new File(mapper_path, mapperName + ".java");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperFile), "utf-8"));
		bw.write("package " + mapper_package + ";");
		bw.newLine();
		bw.newLine();
		bw.newLine();
		bw.write("import java.util.Map;");
		bw.newLine();
		bw.write("import java.util.List;");
		bw = buildClassComment(bw, mapperName + "数据库操作接口类");
		bw.newLine();
		bw.newLine();
		// bw.write("public interface " + mapperName + " extends " +
		// mapper_extends + "<" + beanName + "> {");
		bw.write("public interface " + mapperName + "{");
		bw.newLine();
		bw.newLine();
		// ----------定义Mapper中的方法Begin----------
		bw.newLine();
		bw = buildMethodComment(bw, "删除（根据主键ID删除）");
		bw.newLine();
		bw.write("\t" + "int deleteByPrimaryKey (String id );");
		bw.newLine();
		bw = buildMethodComment(bw, "根据map添加单条数据");
		bw.newLine();
		bw.write("\t" + "int insertByMap(Map<String,Object> map );");
		bw.newLine();
		bw = buildMethodComment(bw, "修改（根据主键ID修改）");
		bw.newLine();
		bw.write("\t" + "int updateByMap (Map<String,Object> map );");
		bw.newLine();
		bw = buildMethodComment(bw, "根据ArrayList批量添加");
		bw.newLine();
		bw.write("\t" + "int insertByList (List<Map<String,Object>> list );");
		bw.newLine();
		bw = buildMethodComment(bw, "根据Map查询");
		bw.newLine();
		bw.write("\t" + "List<Map<String,Object>> queryByMap (Map<String,Object> map );");
		bw.newLine();
		bw = buildMethodComment(bw, "根据List<String>批量删除(list是主键id的集合)");
		bw.newLine();
		bw.write("\t" + "int deleteByList (List<String> list );");
		bw.newLine();
		bw = buildMethodComment(bw, "根据List<Map<String,Object>>批量更新(list是主键id的集合)");
		bw.newLine();
		bw.write("\t" + "int updateByList (List<Map<String,Object>> list );");
		bw.newLine();

		// ----------定义Mapper中的方法End----------
		bw.newLine();
		bw.write("}");
		bw.flush();
		bw.close();
	}

	/**
	 * 构建实体类映射XML文件
	 *
	 * @param columns
	 * @param types
	 * @param comments
	 * @throws IOException
	 */
	private void buildMapperXml(List<String> columns, List<String> types, List<String> comments) throws IOException {
		File folder = new File(xml_path);
		if (!folder.exists()) {
			folder.mkdirs();
		}

		File mapperXmlFile = new File(xml_path, mapperName + ".xml");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperXmlFile)));
		bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		bw.newLine();
		bw.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" ");
		bw.newLine();
		bw.write("    \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
		bw.newLine();
		bw.write("<mapper namespace=\"" + mapper_package + "." + mapperName + "\">");
		bw.newLine();
		// 下面开始写SqlMapper中的方法
		// this.outputSqlMapperMethod(bw, columns, types);
		buildSQL(bw, columns, types);

		bw.write("</mapper>");
		bw.flush();
		bw.close();
	}

	private void buildSQL(BufferedWriter bw, List<String> columns, List<String> types) throws IOException {
		int size = columns.size();
		// 删除（根据主键ID删除）
		bw.newLine();
		bw.write("\t<!--删除：根据主键ID删除-->");
		bw.newLine();
		bw.write("\t<delete id=\"deleteByPrimaryKey\" parameterType=\"java.lang.String\">");
		bw.newLine();
		bw.write("\t\t DELETE FROM " + tableName);
		bw.newLine();
		bw.write("\t\t WHERE " + columns.get(0) + " = #{" + processField(columns.get(0)) + "}");
		bw.newLine();
		bw.write("\t</delete>");
		bw.newLine();
		bw.newLine();
		// 删除完

		// 添加insert方法
		bw.write("\t<!-- 根据Map添加添加单条数据 -->");
		bw.newLine();
		bw.write("\t<insert id=\"insertByMap\" parameterType=\"Map\">");
		bw.newLine();
		bw.write("\t\t INSERT INTO " + tableName);
		bw.newLine();
		bw.write(" \t\t(");
		for (int i = 0; i < size; i++) {
			bw.write(columns.get(i));
			if (i != size - 1) {
				bw.write(",");
			}
		}
		bw.write(") ");
		bw.newLine();
		bw.write("\t\t VALUES ");
		bw.newLine();
		bw.write(" \t\t(");
		for (int i = 0; i < size; i++) {
			bw.write("#{" + processField(columns.get(i)) + "}");
			if (i != size - 1) {
				bw.write(",");
			}
		}
		bw.write(") ");
		bw.newLine();
		bw.write("\t</insert>");
		bw.newLine();
		bw.newLine();
		// 添加insert完
		String tempField = null;
		tempField = null;
		// 修改update方法
		bw.write("\t<!--根据主键修改单条数据-->");
		bw.newLine();
		bw.write("\t<update id=\"updateByMap\" parameterType=\"Map\">");
		bw.newLine();
		bw.write("\t\t UPDATE " + tableName);
		bw.newLine();
		bw.write(" \t\t <set> ");
		bw.newLine();

		tempField = null;
		for (int i = 1; i < size; i++) {
			tempField = processField(columns.get(i));
			bw.write("\t\t\t<if test=\"" + tempField + " != null and " + tempField + " != ''\">");
			bw.newLine();
			bw.write("\t\t\t\t " + columns.get(i) + " = #{" + tempField + "},");
			bw.newLine();
			bw.write("\t\t\t</if>");
			bw.newLine();
		}

		bw.write(" \t\t </set>");
		bw.newLine();
		bw.write("\t\t WHERE " + columns.get(0) + " = #{" + processField(columns.get(0)) + "}");
		bw.newLine();
		bw.write("\t</update>");
		bw.newLine();
		bw.newLine();
		// update方法完毕

		// 批量添加方法
		bw.write("\t<!-- 根据List批量添加数据 -->");
		bw.newLine();
		bw.write("\t<insert id=\"insertByList\" parameterType=\"ArrayList\">");
		bw.newLine();
		bw.write("\t\t INSERT INTO " + tableName);
		bw.newLine();
		bw.write(" \t\t(");
		for (int i = 0; i < size; i++) {
			bw.write(columns.get(i));
			if (i != size - 1) {
				bw.write(",");
			}
		}
		bw.write(") ");
		bw.newLine();
		bw.write("\t\t VALUES ");
		bw.newLine();
		bw.write("\t<foreach collection=\"list\" item=\"item\" index=\"index\"");
		bw.write("\t separator=\",\">");
		bw.newLine();
		bw.write(" \t\t(");
		for (int i = 0; i < size; i++) {
			bw.write("#{item." + processField(columns.get(i)) + "}");
			if (i != size - 1) {
				bw.write(",");
			}
		}
		bw.write(") ");
		bw.newLine();
		bw.write("\t</foreach>");
		bw.newLine();
		bw.write("\t</insert>");
		bw.newLine();
		// 批量添加完成
		// 查询query方法
		bw.write("\t<!--根据Map查询数据-->");
		bw.newLine();
		bw.write("\t<select id=\"queryByMap\"  resultType=\"Map\" parameterType=\"Map\">");
		bw.newLine();
		bw.write("\t\t SELECT " + getColumns(columns) + " FROM " + tableName);
		bw.newLine();
		bw.write("\t\t <where>");
		bw.newLine();
		tempField = null;
		for (int i = 0; i < size; i++) {
			tempField = processField(columns.get(i));
			bw.write("\t\t\t<if test=\"" + tempField + " != null and " + tempField + " != '' \">");
			bw.newLine();
			bw.write("\t\t\t\t AND " + columns.get(i) + " = #{" + tempField + "}");
			bw.newLine();
			bw.write("\t\t\t</if>");
			bw.newLine();
		}
		bw.write("\t</where>");
		bw.newLine();
		bw.write("\t</select>");
		bw.newLine();
		bw.newLine();
		// update方法完毕
		// 批量删除方法
		bw.write("\t<!-- 根据List id 批量删除数据 -->");
		bw.newLine();
		bw.write("\t<delete id=\"deleteByList\" parameterType=\"ArrayList\">");
		bw.newLine();
		bw.write("\t\t DELETE  FROM " + tableName + " WHERE " + processField(columns.get(0)) + " in");
		bw.newLine();
		bw.write("\t<foreach item=\"" + processField(columns.get(0)) + "\" collection=\"list\" open=\"(\" close=\")\"");
		bw.write("\t separator=\",\">");
		bw.newLine();
		bw.write("\t\t  #{" + processField(columns.get(0)) + "}");
		bw.newLine();
		bw.write("\t</foreach>");
		bw.newLine();
		bw.write("\t</delete>");
		bw.newLine();
		// 批量删除方法完成

		// 批量更新方法
		bw.write("\t<!--根据主键批量修改数据 数据量不易过大-->");
		bw.newLine();
		bw.write("\t<update id=\"updateByList\" parameterType=\"ArrayList\">");
		bw.newLine();
		bw.write("\t\t UPDATE " + tableName);
		bw.newLine();
		bw.write("\t\t <trim prefix=\"set\" suffixOverrides=\",\">");
		for (int i = 1; i < size; i++) {
			bw.newLine();
			bw.write("\t<trim prefix=\"" + columns.get(i).toUpperCase() + " =case\" suffix=\"end,\">");
			bw.newLine();
			bw.write("\t<foreach collection=\"list\" item=\"i\" index=\"index\">");
			bw.newLine();
			bw.write("\t<if test=\"i." + columns.get(i) + "!=null\">");
			bw.newLine();
			bw.write("\twhen " + columns.get(0) + "=#{i." + columns.get(0) + "} then");
			bw.newLine();
			bw.write("\t	#{i." + columns.get(i) + "}");
			bw.newLine();
			bw.write("\t	</if>");
			bw.newLine();
			bw.write("\t	</foreach>");
			bw.newLine();
			bw.write("\t	</trim>");
		}
		bw.newLine();
		bw.write("\t	</trim>");
		bw.newLine();
		bw.write("\tWHERE " + columns.get(0) + " in");
		bw.newLine();
		bw.write("\t<foreach item=\"item\" collection=\"list\" open=\"(\" close=\")\"  separator=\",\">");
		bw.newLine();
		bw.write("\t#{item." + columns.get(0) + "}");
		bw.newLine();
		bw.write("\t</foreach>");
		bw.newLine();
		bw.write("\t</update>");
		bw.newLine();
		// 批量修改完成
	}

	public String getColumns(List<String> Columns) {
		String str = "";
		for (int i = 0, s = Columns.size(); i < s; i++) {
			str += Columns.get(i) + ",";
		}
		return str.substring(0, str.length() - 1);
	}

	private void buildMap(List<String> columns, List<String> types) throws IOException {
		File folder = new File(map_path);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		File mapTxtFile = new File(map_path, tableName + ".txt");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapTxtFile)));
		bw.newLine();
		bw.write("\tMap<String,Object> map = new HashMap<>();");
		bw.newLine();
		bulidMap(bw, columns, types);
		bw.flush();
		bw.close();
	}

	private void bulidMap(BufferedWriter bw, List<String> columns, List<String> types) throws IOException {
		if (!columns.isEmpty()) {
			for (int i = 0, s = columns.size(); i < s; i++) {
				bw.write("\tmap.put(\"" + columns.get(i) + "\",\"\");");
				bw.newLine();
			}
		}
	}

	private void buildPojo(String table) throws ClassNotFoundException {
		getGenEntity(table);
	}

	private void getGenEntity(String table) throws ClassNotFoundException {

		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, user, password);
			// 查要生成实体类的表
			String sql = "select * from " + table;
			PreparedStatement pStemt = null;
			pStemt = conn.prepareStatement(sql);
			ResultSetMetaData rsmd = pStemt.getMetaData();
			int size = rsmd.getColumnCount(); // 统计列
			colnames = new String[size];
			colTypes = new String[size];
			colSizes = new int[size];
			for (int i = 0; i < size; i++) {
				colnames[i] = rsmd.getColumnName(i + 1);
				colTypes[i] = rsmd.getColumnTypeName(i + 1);

				if (colTypes[i].equalsIgnoreCase("datetime") || colTypes[i].equalsIgnoreCase("timestamp")) {
					f_util = true;
				}
				if (colTypes[i].equalsIgnoreCase("image") || colTypes[i].equalsIgnoreCase("text")) {
					f_sql = true;
				}
				colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
			}

			String content = parse(colnames, colTypes, colSizes, table);

			try {
				File folder = new File(pojo_path);
				if (!folder.exists()) {
					folder.mkdirs();
				}
				File mapTxtFile = new File(pojo_path, initcapHump(table) + ".java");
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapTxtFile)));
				bw.write(content);
				bw.flush();
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// try {
			// con.close();
			// } catch (SQLException e) {
			// e.printStackTrace();
			// }
		}
	}

	/**
	 * 功能：生成实体类主体代码
	 *
	 * @param colnames
	 * @param colTypes
	 * @param colSizes
	 * @return
	 */
	private static String parse(String[] colnames, String[] colTypes, int[] colSizes, String table) {
		StringBuffer sb = new StringBuffer();
		sb.append("package " + package_path + ";\r\n");
		// 判断是否导入工具包
		if (f_util) {
			sb.append("import java.util.Date;\r\n");
		}
		if (f_sql) {
			sb.append("import java.sql.*;\r\n");
		}
		sb.append("\r\n");
		// 注释部分
		sb.append("   /**\r\n");
		sb.append("    * " + table + " 实体类\r\n");
		sb.append("    * " + new java.util.Date() + "  \r\n");
		sb.append("    */ \r\n");
		// 实体部分
		sb.append("\r\n\r\npublic class " + initcapHump(table) + "{\r\n");
		processAllAttrs(sb);// 属性
		processAllMethod(sb);// get set方法
		sb.append("}\r\n");

		// System.out.println(sb.toString());
		return sb.toString();
	}

	/**
	 * 功能：生成所有属性
	 *
	 * @param sb
	 */
	private static void processAllAttrs(StringBuffer sb) {

		for (int i = 0; i < colnames.length; i++) {
			sb.append("\tprivate " + sqlType2JavaType(colTypes[i]) + " " + initcap(colnames[i]) + ";\r\n");
		}

	}

	/**
	 * 功能：生成所有方法
	 *
	 * @param sb
	 */
	private static void processAllMethod(StringBuffer sb) {

		for (int i = 0; i < colnames.length; i++) {
			sb.append("\tpublic void set" + initcapHump(colnames[i]) + "(" + sqlType2JavaType(colTypes[i]) + " "
					+ initcap(colnames[i]) + "){\r\n");
			sb.append("\tthis." + initcap(colnames[i]) + "=" + initcap(colnames[i]) + ";\r\n");
			sb.append("\t}\r\n");
			sb.append("\tpublic " + sqlType2JavaType(colTypes[i]) + " get" + initcapHump(colnames[i]) + "(){\r\n");
			sb.append("\t\treturn " + initcap(colnames[i]) + ";\r\n");
			sb.append("\t}\r\n");
		}

	}

	/**
	 * 功能：将输入字符串的首字母改成大写
	 *
	 * @param
	 * @return
	 */
	// private static String initcap(String str) {
	//
	// char[] ch = str.toCharArray();
	// if (ch[0] >= 'a' && ch[0] <= 'z') {
	// ch[0] = (char) (ch[0] - 32);
	// }
	//
	// return new String(ch);
	// }

	// test_up命名为testUp
	private static String initcap(String field) {
		StringBuffer sb = new StringBuffer(field.length());
		field = field.toLowerCase();
		String[] fields = field.split("_");
		String temp = null;
		sb.append(fields[0]);
		for (int i = 1; i < fields.length; i++) {
			temp = fields[i].trim();
			sb.append(temp.substring(0, 1).toUpperCase()).append(temp.substring(1));
		}
		return sb.toString();
	}

	// test_up命名为TestUp
	private static String initcapHump(String field) {
		StringBuffer sb = new StringBuffer(field.length());
		field = field.toLowerCase();
		String[] fields = field.split("_");
		String temp = null;
		// sb.append(fields[0]);
		for (int i = 0; i < fields.length; i++) {
			temp = fields[i].trim();
			sb.append(temp.substring(0, 1).toUpperCase()).append(temp.substring(1));
		}
		return sb.toString();
	}

	/**
	 * 功能：获得列的数据类型
	 *
	 * @param sqlType
	 * @return
	 */
	private static String sqlType2JavaType(String sqlType) {

		if (sqlType.equalsIgnoreCase("bit")) {
			return "boolean";
		} else if (sqlType.equalsIgnoreCase("tinyint")) {
			return "byte";
		} else if (sqlType.equalsIgnoreCase("smallint")) {
			return "short";
		} else if (sqlType.equalsIgnoreCase("int")) {
			return "int";
		} else if (sqlType.equalsIgnoreCase("bigint")) {
			return "long";
		} else if (sqlType.equalsIgnoreCase("float")) {
			return "float";
		} else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")
				|| sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money")
				|| sqlType.equalsIgnoreCase("smallmoney")) {
			return "double";
		} else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
				|| sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
				|| sqlType.equalsIgnoreCase("text")) {
			return "String";
		} else if (sqlType.equalsIgnoreCase("datetime")) {
			return "Date";
		} else if (sqlType.equalsIgnoreCase("image")) {
			return "Blod";
		} else if (sqlType.equalsIgnoreCase("timestamp")) {
			return "Date";
		}

		return null;
	}

	public void generate() throws ClassNotFoundException, SQLException, IOException {
		init();
		String prefix = "show full fields from ";
		List<String> columns = null;
		List<String> types = null;
		List<String> comments = null;
		PreparedStatement pstate = null;
		List<String> tables = getTables();
		for (String table : tables) {
			columns = new ArrayList<String>();
			types = new ArrayList<String>();
			comments = new ArrayList<String>();
			pstate = conn.prepareStatement(prefix + table);
			ResultSet results = pstate.executeQuery();
			while (results.next()) {
				columns.add(results.getString("FIELD"));
				types.add(results.getString("TYPE"));
				comments.add(results.getString("COMMENT"));
			}
			tableName = table;
			processTable(table);
			buildMap(columns, types);
			buildPojo(table);
			buildService();
			buildServiceImpl();
			buildMapper();
			buildMapperXml(columns, types, comments);

		}
		conn.close();
	}

	@Test
	public void doCreateMysqlEntity() {
		try {
			new CreateMysqlEntityUtil().generate();
			// 自动打开生成文件的目录
			Runtime.getRuntime().exec("cmd /c start explorer D:\\temp");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("生成成功!");
	}
}
