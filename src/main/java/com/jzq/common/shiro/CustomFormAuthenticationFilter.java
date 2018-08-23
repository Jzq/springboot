package com.jzq.common.shiro;

import com.google.code.kaptcha.Constants;
import com.jzq.common.utils.ConstUtils;
import com.jzq.common.utils.CookieUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 这个filter放开的话不能进行Subject subject = SecurityUtils.getSubject();subject.login(token);
 * 原因待调查
 */
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // 验证码的校验
        HttpSession session = httpServletRequest.getSession();
        // 取出验证码
        String validateCode = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        // 取出页面的验证码
        // 输入的验证和session中的验证进行对比
        String randomCode = httpServletRequest.getParameter("randomCode");
        if (randomCode != null && validateCode != null && !randomCode.equalsIgnoreCase(validateCode)) {
            // 如果校验失败，将验证码错误失败信息，通过shiroLoginFailure设置到request中
            httpServletRequest.setAttribute("shiroLoginFailure", "kaptchaValidateFailed");//自定义登录异常
            // 拒绝访问，不再校验账号和密码
            return true;
        }
        return super.onAccessDenied(request, response);
    }

}
