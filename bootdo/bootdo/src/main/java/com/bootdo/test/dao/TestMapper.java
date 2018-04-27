package com.bootdo.test.dao;

import com.bootdo.test.domain.TestDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
/**
 * 
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-09-26 10:54:46
 */
@Mapper
public interface TestMapper {

	@Select("select `test_id`, `test_name`, `test_age`, `test_addr` from test where test_id = #{id}")
	TestDO get(Integer testId);
	
	@Select("<script>" +
	"select * from test " + 
			"<where>" + 
		  		  "<if test=\"testId != null and testId != ''\">"+ "and test_id = #{testId} " + "</if>" + 
		  		  "<if test=\"testName != null and testName != ''\">"+ "and test_name = #{testName} " + "</if>" + 
		  		  "<if test=\"testAge != null and testAge != ''\">"+ "and test_age = #{testAge} " + "</if>" + 
		  		  "<if test=\"testAddr != null and testAddr != ''\">"+ "and test_addr = #{testAddr} " + "</if>" + 
		  			"</where>"+ 
			" <choose>" + 
	            "<when test=\"sort != null and sort.trim() != ''\">" + 
	                "order by ${sort} ${order}" + 
	            "</when>" + 
				"<otherwise>" + 
	                "order by test_id desc" + 
				"</otherwise>" + 
	        "</choose>"+
			"<if test=\"offset != null and limit != null\">"+
			"limit #{offset}, #{limit}" + 
			"</if>"+
			"</script>")
	List<TestDO> list(Map<String,Object> map);
	
	@Select("<script>" +
	"select count(*) from test " + 
			"<where>" + 
		  		  "<if test=\"testId != null and testId != ''\">"+ "and test_id = #{testId} " + "</if>" + 
		  		  "<if test=\"testName != null and testName != ''\">"+ "and test_name = #{testName} " + "</if>" + 
		  		  "<if test=\"testAge != null and testAge != ''\">"+ "and test_age = #{testAge} " + "</if>" + 
		  		  "<if test=\"testAddr != null and testAddr != ''\">"+ "and test_addr = #{testAddr} " + "</if>" + 
		  			"</where>"+ 
			"</script>")
	int count(Map<String,Object> map);
	
	@Insert("insert into test (`test_name`, `test_age`, `test_addr`)"
	+ "values (#{testName}, #{testAge}, #{testAddr})")
	int save(TestDO test);
	
	@Update("<script>"+ 
			"update test " + 
					"<set>" + 
		            "<if test=\"testId != null\">`test_id` = #{testId}, </if>" + 
                    "<if test=\"testName != null\">`test_name` = #{testName}, </if>" + 
                    "<if test=\"testAge != null\">`test_age` = #{testAge}, </if>" + 
                    "<if test=\"testAddr != null\">`test_addr` = #{testAddr}, </if>" + 
          					"</set>" + 
					"where test_id = #{testId}"+
			"</script>")
	int update(TestDO test);
	
	@Delete("delete from test where test_id =#{testId}")
	int remove(Integer test_id);
	
	@Delete("<script>"+ 
			"delete from test where test_id in " + 
					"<foreach item=\"testId\" collection=\"array\" open=\"(\" separator=\",\" close=\")\">" + 
						"#{testId}" + 
					"</foreach>"+
			"</script>")
	int batchRemove(Integer[] testIds);
}
