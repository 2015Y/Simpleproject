package mutilDataSource.service;

import java.util.Map;

public interface TestService {

	int testTransaction() throws Exception;

	Map<String, Object> findAll();

	Map<String, Object> findOneUser(String id);

	Map<String, Object> findOneTest(String id);

	Map<String, Object> findOneThree(String id);

}
