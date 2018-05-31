package mutilDataSource.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TestDao {

	List<Map<String, Object>> findAll();

	int addOne();

	@Select("select id,name,age,remarks from test where id = #{id} ")
	Map<String, Object> findOne(String id);

}
