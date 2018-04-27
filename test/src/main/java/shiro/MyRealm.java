package shiro;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import pojo.User;

public class MyRealm extends AuthorizingRealm {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		log.info("权限认证方法：MyShiroRealm.doGetAuthenticationInfo()");
		User token = (User) SecurityUtils.getSubject().getPrincipal();
		String userId = token.getId();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 根据用户ID查询角色（role），放入到Authorization里。
		/*
		 * Map<String, Object> map = new HashMap<String, Object>(); map.put("user_id",
		 * userId); List<SysRole> roleList = sysRoleService.selectByMap(map);
		 * Set<String> roleSet = new HashSet<String>(); for(SysRole role : roleList){
		 * roleSet.add(role.getType()); }
		 */
		// 实际开发，当前登录用户的角色和权限信息是从数据库来获取的，我这里写死是为了方便测试
		Set<String> roleSet = new HashSet<String>();
		roleSet.add("100002");
		info.setRoles(roleSet);
		// 根据用户ID查询权限（permission），放入到Authorization里。
		/*
		 * List<SysPermission> permissionList = sysPermissionService.selectByMap(map);
		 * Set<String> permissionSet = new HashSet<String>(); for(SysPermission
		 * Permission : permissionList){ permissionSet.add(Permission.getName()); }
		 */
		Set<String> permissionSet = new HashSet<String>();
		permissionSet.add("权限添加");
		info.setStringPermissions(permissionSet);
		System.out.println(userId);
		return info;
	}

	/**
	 * 认证信息.(身份验证) : Authentication 是用来验证用户身份
	 * 
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {

		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		if (!token.getUsername().equals("admin")) {
			log.info("身份认证,用户名错误!");
			return null;
		}
		char[] password = token.getPassword();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < password.length; i++) {
			char c = password[i];
			sb.append(c);
		}
		if (!sb.toString().equals("123")) {
			log.info("身份认证,密码错误!");
			return null;
		}
		User user = new User();
		user.setId("123");
		user.setAge("123");
		user.setName("admin");
		return new SimpleAuthenticationInfo(user, "123", getName());
	}
}
