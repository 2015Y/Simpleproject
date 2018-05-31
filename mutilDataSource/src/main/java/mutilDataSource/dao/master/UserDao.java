package mutilDataSource.dao.master;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {

	List<Map<String, Object>> findAll();

	int addOne();
	
	@Select("select id,name,age from s_user where id = #{id} ")
	Map<String,Object> findOne(String id);
	
}
