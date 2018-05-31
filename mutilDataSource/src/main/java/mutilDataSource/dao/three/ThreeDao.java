package mutilDataSource.dao.three;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ThreeDao {

	@Select("select id,name,age,remarks from test where id = #{id} ")
	Map<String, Object> findOne(String id);

	int addOne();

	List<Map<String, Object>> findAll();

}
