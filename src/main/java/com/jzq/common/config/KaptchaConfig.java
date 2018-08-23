package com.jzq.common.config;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.servlet.KaptchaServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class KaptchaConfig {

    @Bean
    public ServletRegistrationBean kaptchaServlet(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new KaptchaServlet());
        registrationBean.addUrlMappings("/kaptcha/validateCode.jpg");
        registrationBean.addInitParameter(Constants.KAPTCHA_SESSION_CONFIG_KEY, Constants.KAPTCHA_SESSION_KEY);
        //宽度
        registrationBean.addInitParameter(Constants.KAPTCHA_IMAGE_WIDTH,"80");
        //高度
        registrationBean.addInitParameter(Constants.KAPTCHA_IMAGE_HEIGHT,"34");
        //字体大小
        registrationBean.addInitParameter(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE,"28");
        //无边框
        registrationBean.addInitParameter(Constants.KAPTCHA_BORDER,"no");
        //字体
        registrationBean.addInitParameter(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES,"宋体");
        //文字颜色
        registrationBean.addInitParameter(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR,"blue");
        //长度
        registrationBean.addInitParameter(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "4");
        //字符间距
        registrationBean.addInitParameter(Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE, "2");
        //干扰线
        registrationBean.addInitParameter(Constants.KAPTCHA_NOISE_COLOR,"black");
        //图片样式
        registrationBean.addInitParameter(Constants.KAPTCHA_OBSCURIFICATOR_IMPL,"com.google.code.kaptcha.impl.ShadowGimpy");


        return registrationBean;
    }

}
