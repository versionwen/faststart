package com.wenxin.learn.faststart.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wenxin.learn.faststart.web.entity.ResourceCategory;
import com.wenxin.learn.faststart.web.mapper.ResourceCategoryMapper;
import com.wenxin.learn.faststart.web.service.ResourceCategoryService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author version
 * @version 1.0
 * @date 2020/10/24 17:34
 */
@Service
public class ResourceCategoryImpl extends ServiceImpl<ResourceCategoryMapper,ResourceCategory> implements ResourceCategoryService {
    @Override
    public List<ResourceCategory> listAll() {
        QueryWrapper<ResourceCategory>wrapper =new QueryWrapper<>();
        wrapper.lambda().orderByDesc(ResourceCategory::getSort);
        return list(wrapper);
    }

    @Override
    public boolean create(ResourceCategory resourceCategory) {
        resourceCategory.setCreateTime(new Date());
        return save(resourceCategory);
    }
}
