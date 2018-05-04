package com.study.demo.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.study.demo.config.support.RedisCacheManager;
import com.study.demo.config.support.RedisSessionDAO;
import com.study.demo.config.support.UserRealm;
import com.study.demo.constant.GlobalConstant;

@Configuration
public class ShiroConfig {

	@Bean
	public UserRealm getUserRealm() {
		UserRealm userRealm = new UserRealm();
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher(GlobalConstant.HASH_ALGORITHM_NAME);
		credentialsMatcher.setHashIterations(GlobalConstant.HASH_ITERATIONS);
		userRealm.setCredentialsMatcher(credentialsMatcher);
		userRealm.setCachingEnabled(true);
		userRealm.setAuthenticationCacheName(GlobalConstant.SHIR_AUTHENTICATION_CACHE_NAME);
		userRealm.setAuthenticationCachingEnabled(true);
		userRealm.setAuthorizationCacheName(GlobalConstant.SHIR_AUTHORIZATION_CACHE_NAME);
		userRealm.setAuthorizationCachingEnabled(true);
		return userRealm;
	}

	@Bean
	public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public RedisCacheManager redisCacheManager() {
		return new RedisCacheManager();
	}

	@Bean
	public SessionDAO sessionDAO() {
		RedisSessionDAO sessionDAO = new RedisSessionDAO();
		sessionDAO.setActiveSessionsCacheName(GlobalConstant.SHIRO_ACTIVE_SESSIONS_CACHE_NAME);
		return sessionDAO;
	}

	@Bean
	public SessionManager sessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		// 注入sessionDAO
		sessionManager.setSessionDAO(sessionDAO());
		// 设置session全局过期时间为30分钟 单位毫秒
		sessionManager.setGlobalSessionTimeout(1800000);
		// 设置session缓存 采用redis
		sessionManager.setCacheManager(redisCacheManager());
		// 设置session的cookie
		sessionManager.setSessionIdCookie(sessionIdCookie());
		return sessionManager;
	}

	@Bean
	public Cookie sessionIdCookie() {
		SimpleCookie cookie = new SimpleCookie();
		// 设置cookie中sessionid的名字
		cookie.setName("sid");
		return cookie;
	}

	public RememberMeManager rememberMeManager() {
		CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
		rememberMeManager.setCookie(rememberMeCookie());
		// rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
		rememberMeManager.setCipherKey(Base64.decode(GlobalConstant.CIPHERKEY));
		return rememberMeManager;
	}

	public Cookie rememberMeCookie() {
		SimpleCookie cookie = new SimpleCookie("rememberme");
		// 记住我cookie生效时间30天 ,单位秒
		cookie.setMaxAge(2592000);
		return cookie;
	}

	@Bean
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 设置realm.
		securityManager.setRealm(getUserRealm());
		// 自定义session管理 使用redis
		securityManager.setSessionManager(sessionManager());
		// 自定义缓存实现 使用redis
		securityManager.setCacheManager(redisCacheManager());
		// 注入记住我管理器;
		securityManager.setRememberMeManager(rememberMeManager());
		return securityManager;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor() {
		AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
		aasa.setSecurityManager(securityManager());
		return new AuthorizationAttributeSourceAdvisor();
	}

	@Bean
	public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
		daap.setProxyTargetClass(true);
		return daap;
	}

	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
		Map<String, String> filterChainDefinitionMap = new HashMap<>();
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager());
		shiroFilterFactoryBean.setLoginUrl("/api/user/login");
		shiroFilterFactoryBean.setSuccessUrl("/index");
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		// filterChainDefinitionMap.put("/sa/**", "authc");
		// anon表示不拦截
		filterChainDefinitionMap.put("/api/user/login", "anon");
		filterChainDefinitionMap.put("/**", "authc");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

}
