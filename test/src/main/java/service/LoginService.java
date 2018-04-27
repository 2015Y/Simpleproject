package service;

import java.util.List;
import java.util.Map;

import pojo.User;

public interface LoginService {

	String login(String user);

	Map<String, Object> getUsers(Integer pageNumber, Integer pageSize);

	List<User> getAlluser();

	Map<String, Object> getDynamicData(Integer pageNumber, Integer pageSize);

	Map<String, Object> getDynamicColumns();

	List<Map<String, Object>> getUsersByAge(Integer age);

	User findOne(User user);

	User updateOne(User user);

	void deleteById(User user);

	int addUser();

	void addOneUser(User user);

	void test(User user);

	List<User> findAge(String age);
}
