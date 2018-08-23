package com.jzq.common.shiro;

import com.jzq.common.security.DesUtils;
import com.jzq.common.utils.PasswordUtils;
import com.jzq.modules.sys.entity.UserInfo;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

public class UserRealm extends AuthorizingRealm {

    private static Logger log = LoggerFactory.getLogger(UserRealm.class);
    /**
     * 角色授权和对应权限添加
     * @param principals
     * @return
     */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取用户名
        String name = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole("admin");
        simpleAuthorizationInfo.addStringPermission("create");
        return simpleAuthorizationInfo;
	}
    /**
     * 用户认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String uername = (String) token.getPrincipal();
		String password = new String((char[]) token.getCredentials());
        log.info("loginName:"+uername);
        log.info("password :"+password);
		// TODO 查询用户信息
        UserInfo user = new UserInfo();
        user.setId(1l);
        user.setUserName(uername);
        user.setName("jzq");
		// 账号不存在
		if (user == null) {
			throw new UnknownAccountException("账号或密码不正确");
		}
        if (!DesUtils.decode(password,"1,2,3").equals("123456")) {
			throw new IncorrectCredentialsException("账号或密码不正确");
		}
		return new SimpleAuthenticationInfo(user, password, getName());
	}

    /**
     * 授权验证方法
     * @param permission
     */
    private void authorizationValidate(Permission permission){
        // 模块授权预留接口
    }

    @Override
    protected void checkPermission(Permission permission, AuthorizationInfo info) {
        authorizationValidate(permission);
        super.checkPermission(permission, info);
    }

    @Override
    protected boolean[] isPermitted(List<Permission> permissions, AuthorizationInfo info) {
        if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
                authorizationValidate(permission);
            }
        }
        return super.isPermitted(permissions, info);
    }

    @Override
    public boolean isPermitted(PrincipalCollection principals, Permission permission) {
        authorizationValidate(permission);
        return super.isPermitted(principals, permission);
    }

    @Override
    protected boolean isPermittedAll(Collection<Permission> permissions, AuthorizationInfo info) {
        if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
                authorizationValidate(permission);
            }
        }
        return super.isPermittedAll(permissions, info);
    }

}
