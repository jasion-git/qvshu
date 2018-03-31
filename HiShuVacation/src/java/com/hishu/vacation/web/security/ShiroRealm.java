package com.hishu.vacation.web.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.hishu.vacation.biz.service.AdminUserService;
import com.hishu.vacation.dto.AdminUser;
import com.hishu.vacation.dto.Auth;
import com.hishu.vacation.dto.Role;



public class ShiroRealm extends AuthorizingRealm{

	protected final Logger log=Logger.getLogger(this.getClass());
	
	@Autowired
	private AdminUserService adminUserService;
	
	/*
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用,负责在应用程序中决定用户的访问控制的方法 
     */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		/*
		 * 已经登录成功
		 * 给用户设置权限
		 * */
		String account=(String)principals.getPrimaryPrincipal(); //获取用户账号
		log.info("登录成功:"+account);
		AdminUser user=new AdminUser();
		user.setAccount(account);
		user=adminUserService.getAdminUser(user);
		if(user==null){
			return null;
		}
		SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
		Role role=user.getRole();
		if(role==null){
			return null;
		}
		Set<String>roleSet=new HashSet<String>();
		roleSet.add(role.getCode());
		List<Auth>auths=role.getAuths();
		Set<String>authsSet=new HashSet<String>();
		for(Auth a:auths){
			authsSet.add(a.getCode());
		}
		log.info("用户角色:"+roleSet);
		log.info("用户权限:"+authsSet);
		authorizationInfo.setRoles(roleSet);
		authorizationInfo.setStringPermissions(authsSet);
		
		return authorizationInfo;
	}

	/*认证回调函数，登录信息和用户验证信息验证*/
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken usernamepsswordToken=(UsernamePasswordToken) token;
		String account=(String)usernamepsswordToken.getPrincipal(); // 获取用户名
		String password=String.valueOf(usernamepsswordToken.getPassword());
		//根据用户和密码查询用户是否存在
		AdminUser user=new AdminUser();
		user.setAccount(account);
		user.setStatus(1);
		user=adminUserService.getAdminUser(user);
		if(user==null){
			return null;
		}
		/*if(user.getPassword()==null||!user.getPassword().equals(password)){
			return null;
		}*/
		Object principal=usernamepsswordToken.getPrincipal();
        return new SimpleAuthenticationInfo(principal, user.getPassword(), this.getName());
	}

}
