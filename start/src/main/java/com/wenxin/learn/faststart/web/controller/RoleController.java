package com.wenxin.learn.faststart.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wenxin.learn.faststart.web.api.CommonPage;
import com.wenxin.learn.faststart.web.api.CommonResult;
import com.wenxin.learn.faststart.web.entity.Menu;
import com.wenxin.learn.faststart.web.entity.Resource;
import com.wenxin.learn.faststart.web.entity.Role;
import com.wenxin.learn.faststart.web.entity.UmsResource;
import com.wenxin.learn.faststart.web.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author version
 * @version 1.0
 * @date 2020/10/29 16:29
 */
@RestController
@Api(tags = "后台用户角色管理")
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @ApiOperation("添加角色")
    @PostMapping("/create")
    public CommonResult create(@RequestBody Role role) {
        boolean success = roleService.create(role);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }
    @ApiOperation("修改角色")
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable Long id, @RequestBody Role role) {
        role.setId(id);
        boolean success = roleService.updateById(role);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }
    @ApiOperation("批量删除角色")
    @PostMapping("/delete")
    public CommonResult delete(@RequestParam("ids") List<Long> ids) {
        boolean success = roleService.delete(ids);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }
    @ApiOperation("获取全部角色")
    @GetMapping("/listAll")
    public CommonResult<List<Role>> listAll() {
        List<Role> roleList = roleService.list();
        return CommonResult.success(roleList);
    }
    @ApiOperation("根据角色名称分页获取角色列表")
    @GetMapping("/list")
    public CommonResult<CommonPage<Role>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                  @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<Role> roleList = roleService.list(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(roleList));
    }
    @ApiOperation("修改角色状态")
    @PostMapping(value = "/updateStatus/{id}")
    public CommonResult updateStatus(@PathVariable Long id, @RequestParam(value = "status") Integer status) {
        Role Role = new Role();
        Role.setId(id);
        Role.setStatus(status);
        boolean success = roleService.updateById(Role);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }
    @ApiOperation("获取角色相关菜单")
    @GetMapping(value = "/listMenu/{roleId}")
    public CommonResult<List<Menu>> listMenu(@PathVariable Long roleId) {
        List<Menu> roleList = roleService.listMenu(roleId);
        return CommonResult.success(roleList);
    }
    @ApiOperation("获取角色相关资源")
    @GetMapping(value = "/listResource/{roleId}")
    public CommonResult<List<UmsResource>> listResource(@PathVariable Long roleId) {
        List<UmsResource> roleList = roleService.listResource(roleId);
        return CommonResult.success(roleList);
    }
    @ApiOperation("给角色分配菜单")
    @PostMapping(value = "/allocMenu")
    public CommonResult allocMenu(@RequestParam Long roleId, @RequestParam List<Long> menuIds) {
        int count = roleService.allocMenu(roleId, menuIds);
        return CommonResult.success(count);
    }
    @ApiOperation("给角色分配资源")
    @PostMapping(value = "/allocResource")
    public CommonResult allocResource(@RequestParam Long roleId, @RequestParam List<Long> resourceIds) {
        int count = roleService.allocResource(roleId, resourceIds);
        return CommonResult.success(count);
    }
}
