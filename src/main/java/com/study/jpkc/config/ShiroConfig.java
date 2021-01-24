package com.study.jpkc.config;

import com.study.jpkc.shiro.AccountRealm;
import com.study.jpkc.shiro.JwtFilter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author isharlan.hu@gmail.com
 * @date 2020/12/19 20:41
 * @desc Shiro配置
 */
@Configuration
public class ShiroConfig {

    @Bean
    public FilterRegistrationBean delegatingFilterProxy(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }


    @Bean
    public DefaultWebSecurityManager securityManager(AccountRealm realm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        //关闭shiro的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);
        manager.setRealm(realm);
        return manager;
    }


    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager, JwtFilter jwtFilter) {
        ShiroFilterFactoryBean filterFactory = new ShiroFilterFactoryBean();
        //配置安全管理器
        filterFactory.setSecurityManager(securityManager);
        //配置JWT过滤器
        Map<String, Filter> filterMap = new HashMap<>(1);
        filterMap.put("jwt", jwtFilter);
        filterFactory.setFilters(filterMap);
        //定义过滤规则，所有请求使用JWTFilter
        Map<String, String> filterRuleMap = new HashMap<>(1);
        filterRuleMap.put("/druid/*", "anon");
        filterRuleMap.put("/swagger-ui/*", "anon");
        filterRuleMap.put("/**", "jwt");
        filterFactory.setFilterChainDefinitionMap(filterRuleMap);
        return filterFactory;
    }


    /**
     * 以下为开启注解支持
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator autoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        autoProxyCreator.setProxyTargetClass(true);
        return autoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}
