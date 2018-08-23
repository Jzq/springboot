package com.jzq.common.config;

import com.jzq.common.shiro.UserRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
	@Bean
    UserRealm userRealm() {
		return new UserRealm();
	}

    /**
     * realm
     *
     * @return
     */
    @Bean(name = "authRealm")
    public UserRealm myAuthRealm(@Qualifier("ehCacheManager") EhCacheManager ehCacheManager) {
        UserRealm myAuthorizingRealm = new UserRealm();
        // 设置缓存管理器 此处暂时关闭shiro的缓存机制，改用CacheUtils手动的维护缓存
//        myAuthorizingRealm.setCacheManager(ehCacheManager);

        return myAuthorizingRealm;
    }

    /**
     * cookie对象;
     *
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        // 这个参数是cookie的名称，对应前端的checkbox 的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        // <!-- 记住我cookie生效时间30天（259200） ,单位秒;-->
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }

    /**
     * 记住我管理器 cookie管理对象;
     *
     * @return
     */
    @Bean(name = "cookieRememberMeManager")
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }

    /**
     * 缓存管理器
     * @return
     */
    @Bean(value="ehCacheManager")
    public EhCacheManager ehCacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:cache/ehcache-shiro.xml");
        return cacheManager;
    }

    @Bean(name = "securityManager")
    public SecurityManager securityManager(@Qualifier("authRealm") UserRealm authRealm,
                                           @Qualifier("cookieRememberMeManager") CookieRememberMeManager cookieRememberMeManager,SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(authRealm);

        // 设置rememberMe管理器
        securityManager.setRememberMeManager(cookieRememberMeManager);

        //设置sessionManager
        securityManager.setSessionManager(sessionManager);

        //设置EhCache-shiro
        securityManager.setCacheManager(ehCacheManager());

        return securityManager;
    }

	@Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager securityManager) {

		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();//获取filters
        //filters.put("authc", new CustomFormAuthenticationFilter());//将自定义 的FormAuthenticationFilter注入shiroFilter中
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		shiroFilterFactoryBean.setLoginUrl("/login");
		shiroFilterFactoryBean.setSuccessUrl( "/index");
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		Map<String, String> filterChainDefinitionMap = new HashMap<>();
        filterChainDefinitionMap.put("/adminLTE/**", "anon");
        filterChainDefinitionMap.put("/common/**", "anon");
        filterChainDefinitionMap.put("/plugins/**", "anon");
        filterChainDefinitionMap.put("/register", "anon");
		filterChainDefinitionMap.put("/kaptcha/**","anon");
		filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/main", "user");
        filterChainDefinitionMap.put("/index", "user");
        filterChainDefinitionMap.put("/", "user");
        filterChainDefinitionMap.put("/login", "anon");
		filterChainDefinitionMap.put("/**", "authc");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	@Bean("lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
		proxyCreator.setProxyTargetClass(true);
		return proxyCreator;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			@Qualifier("securityManager") SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}
	@Bean
    public SessionDAO sessionDAO(){
        return new EnterpriseCacheSessionDAO();
    }
    @Bean
    public SessionManager sessionManager(SessionDAO sessionDAO){
        DefaultWebSessionManager manager = new DefaultWebSessionManager();
        manager.setSessionDAO(sessionDAO);
        manager.setGlobalSessionTimeout(360000);
        manager.setSessionValidationInterval(360000);
        return  manager;
    }
}
