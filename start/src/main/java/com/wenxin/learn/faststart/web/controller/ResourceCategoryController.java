package com.wenxin.learn.faststart.web.controller;

import com.wenxin.learn.faststart.web.api.CommonResult;
import com.wenxin.learn.faststart.web.entity.ResourceCategory;
import com.wenxin.learn.faststart.web.service.ResourceCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author version
 * @version 1.0
 * @date 2020/10/24 17:41
 */
@RestController
@RequestMapping("/resourceCategory")
@Api(tags = "资源分类目录")
public class ResourceCategoryController {
    @Autowired
    private ResourceCategoryService resourceCategoryService;
    @ApiOperation("查询所有后台资源分类")
    @GetMapping(value = "/listAll")
    public CommonResult<List<ResourceCategory>> listAll() {
        List<ResourceCategory> resourceList = resourceCategoryService.listAll();
        return CommonResult.success(resourceList);
    }
    @ApiOperation("添加后台资源分类")
    @PostMapping(value = "/create")
    public CommonResult create(@RequestBody ResourceCategory resourceCategory) {
        boolean success = resourceCategoryService.create(resourceCategory);
        if (success) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }
    @ApiOperation("修改后台资源分类")
    @PostMapping(value = "/update/{id}")
    public CommonResult update(@PathVariable Long id,
                               @RequestBody ResourceCategory resourceCategory) {
        resourceCategory.setId(id);
        boolean success = resourceCategoryService.updateById(resourceCategory);
        if (success) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }
    @ApiOperation("根据ID删除后台资源")
    @PostMapping(value = "/delete/{id}")
    public CommonResult delete(@PathVariable Long id) {
        boolean success = resourceCategoryService.removeById(id);
        if (success) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }
}
