package com.jzq.common.shiro;

import com.jzq.modules.sys.entity.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * Shrio相关工具类
 */
public class ShiroUtils {
	public static Subject getSubjct() {
		return SecurityUtils.getSubject();
	}
	public static UserInfo getUser() {
		return (UserInfo)getSubjct().getPrincipal();
	}
	public static Long getUserId() {
		return getUser().getId();
	}
	public static void logout() {
		getSubjct().logout();
	}

}
