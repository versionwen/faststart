package com.wenxin.learn.faststart.web.config;

import com.wenxin.learn.faststart.web.entity.UmsResource;
import com.wenxin.learn.faststart.web.service.AdminService;
import com.wenxin.learn.faststart.web.service.UserService;
import com.wenxin.learn.faststart.web.service.UmsResourceService;
import com.wenxin.learn.faststart.web.config.security.component.DynamicSecurityService;
import com.wenxin.learn.faststart.web.config.security.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
* @Description: spring security 安全模块
* @Author: version
* @Date: 2020/9/29
*/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class FaststartSecurityConfig extends SecurityConfig {

    @Autowired
    private AdminService adminService;
    @Autowired
    private UmsResourceService resourceService;

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> adminService.loadUserByUsername(username);
    }

    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return new DynamicSecurityService() {
            @Override
            public Map<String, ConfigAttribute> loadDataSource() {
                Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
                List<UmsResource> resourceList = resourceService.list();
                for (UmsResource resource : resourceList) {
                    map.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(resource.getId() + ":" + resource.getName()));
                }
                return map;
            }
        };
    }
}
