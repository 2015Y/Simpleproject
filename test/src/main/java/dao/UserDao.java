package dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pojo.User;

@Repository
public interface UserDao extends JpaRepository<User, String> ,JpaSpecificationExecutor<User> {

	@Query("select max(u.id+'')  from User u")
	public Object getMaxId();

	@Query("select new map (u.id as id ,u.name as name,u.age as age) from User u where u.age=?1")
	public List<Map<String, Object>> getUsersByAge(Integer age);
	
	public List<User> findByAge(String age) ;
	
}