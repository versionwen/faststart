package com.wenxin.learn.faststart.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wenxin.learn.faststart.web.entity.ResourceCategory;

import java.util.List;

/**
 * @author version
 * @version 1.0
 * @date 2020/10/24 17:26
 */
public interface ResourceCategoryService extends IService<ResourceCategory> {
    /**
    * @Description: 获取所有的资源分类
    * @Param:  null
    * @return:  List 所有资源分类
    * @Author: version
    * @Date: 2020/10/24
    */
    List<ResourceCategory>listAll();
    /**
    * @Description: 创建资源分类
    * @Param:  ResourceCategory对象
    * @return:  boolean
    * @Author: version
    * @Date: 2020/10/24
    */
    boolean create(ResourceCategory resourceCategory);
}
