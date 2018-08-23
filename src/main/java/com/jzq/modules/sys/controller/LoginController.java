package com.jzq.modules.sys.controller;

import com.google.code.kaptcha.Constants;
import com.jzq.common.result.MsgResult;
import com.jzq.common.shiro.ShiroUtils;
import com.jzq.common.utils.CacheUtils;
import com.jzq.common.utils.ConstUtils;
import com.jzq.common.utils.CookieUtils;
import com.jzq.modules.sys.entity.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    private static Logger log = LoggerFactory.getLogger(LoggerFactory.class);

    /**
     * 登录页面
     *
     * @return
     */
    @RequestMapping(value = "/login")
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
        String rememberMe = CookieUtils.getCookie(request, ConstUtils.REMEMBER_ME);
        String username = CookieUtils.getCookie(request, ConstUtils.USER_NAME);
        if (StringUtils.isNotBlank(rememberMe) && "yes".equals(rememberMe)) {
            model.addAttribute("username", username);
            model.addAttribute("rememberMe", rememberMe);
            model.addAttribute("rememberMe", true);
        } else {
            model.addAttribute("rememberMe", false);
        }
        return "admin/login";
    }

    /**
     * 登录认证
     *
     * @param username
     * @param password
     * @param rememberMe
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    MsgResult doLogin(String username, String password, boolean rememberMe, HttpServletResponse response, HttpServletRequest request) {

        if (rememberMe) {
            CookieUtils.setCookie(response, ConstUtils.REMEMBER_ME, "yes");
        } else {
            CookieUtils.setCookie(response, ConstUtils.REMEMBER_ME, "no");
        }
        try {
           /*
           //CustomFormAuthenticationFilter进行验证码校验
           String exception = (String) request.getAttribute("shiroLoginFailure");
            if(exception!= null && exception.equals("kaptchaValidateFailed")){
                return MsgResult.error("验证码错误");
            }*/
            HttpSession session = request.getSession();
            String validateCode = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
            String randomCode = request.getParameter("randomCode");
            if (randomCode != null && validateCode != null && !randomCode.equalsIgnoreCase(validateCode)) {
                return MsgResult.error("验证码错误");
            }
            UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            return MsgResult.ok();

        } catch (LockedAccountException e) {
            return MsgResult.error("账号已被锁定,请联系管理员");
        } catch (AuthenticationException e) {
            return MsgResult.error("登录名或密码错误");
        } catch (Exception e) {
            return MsgResult.error("系统异常");
        }
    }

    /**
     * 主页面
     *
     * @param model
     * @return
     */
    @RequestMapping({"/index", "/"})
    public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
        UserInfo userInfo = ShiroUtils.getUser();
        if (userInfo == null) {
            return "admin/login";
        }
        HttpSession session = request.getSession();
        session.setAttribute("userInfo", userInfo);
        CookieUtils.setCookie(response, ConstUtils.USER_NAME, userInfo.getUserName());
        //设置bodyClass
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(ConstUtils.BODY_CLASS);
        //设置皮肤
        Object skinName = CacheUtils.getSysCache("skinName");
        if (skinName == null) {
            CacheUtils.putSysCache("skinName", ConstUtils.DEFAULT_SKIN);
            skinName = ConstUtils.DEFAULT_SKIN;
        }
        stringBuffer.append(skinName);
        model.addAttribute("skinName", stringBuffer.toString());
        return "admin/index";
    }

    /**
     * 登出
     *
     * @return
     */
    @GetMapping("/logout")
    String logout() {
        ShiroUtils.logout();
        return "redirect:login";
    }
}
