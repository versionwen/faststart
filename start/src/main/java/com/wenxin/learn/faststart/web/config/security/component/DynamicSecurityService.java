package com.wenxin.learn.faststart.web.config.security.component;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

/**
* @Description: 动态权限相关业务类
* @Author: version
* @Date: 2020/9/25
*/
public interface DynamicSecurityService {
    /**
     * 加载资源ANT通配符和资源对应MAP
     */
    Map<String, ConfigAttribute> loadDataSource();
}
