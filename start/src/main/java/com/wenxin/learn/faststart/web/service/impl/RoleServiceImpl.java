package com.wenxin.learn.faststart.web.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wenxin.learn.faststart.web.entity.*;
import com.wenxin.learn.faststart.web.mapper.MenuMapper;
import com.wenxin.learn.faststart.web.mapper.RoleMapper;
import com.wenxin.learn.faststart.web.mapper.UmsResourceMapper;
import com.wenxin.learn.faststart.web.service.RoleMenuRelationService;
import com.wenxin.learn.faststart.web.service.RoleResourceRelationService;
import com.wenxin.learn.faststart.web.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author version
 * @version 1.0
 * @date 2020/10/29 11:07
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>implements RoleService {

    @Autowired
    private RoleMenuRelationService roleMenuRelationService;
    @Autowired
    private RoleResourceRelationService roleResourceRelationService;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private UmsResourceMapper resourceMapper;
    @Override
    public boolean create(Role role) {
        role.setCreateTime(new Date());
        role.setAdminCount(0);
        role.setSort(0);
        return save(role);
    }

    @Override
    public boolean delete(List<Long> ids) {
        boolean success = removeByIds(ids);
        return success;
    }

    @Override
    public Page<Role> list(String keyword, Integer pageSize, Integer pageNum) {
        Page<Role>page =new Page<>(pageNum,pageSize);
        QueryWrapper<Role>wrapper =new QueryWrapper<>();
        LambdaQueryWrapper<Role>lamda = wrapper.lambda();
        if(StrUtil.isAllNotEmpty(keyword)){
            lamda.like(Role::getName,keyword);
        }
        return page(page,wrapper);
    }

    @Override
    public List<Menu> getMenuList(Long adminId) {

        return  menuMapper.getMenuList(adminId);
    }

    @Override
    public List<Menu> listMenu(Long roleId) {

        return menuMapper.getMenuListByRoleId(roleId);
    }

    @Override
    public List<UmsResource> listResource(Long roleId) {

        return resourceMapper.getResourceListByRoleId(roleId);
    }

    @Override
    public int allocMenu(Long roleId, List<Long> menuIds) {
        QueryWrapper<RoleMenuRelation>wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(RoleMenuRelation::getRoleId,roleId);
        roleMenuRelationService.remove(wrapper);
        List<RoleMenuRelation>relationList = new ArrayList<>();
        for(Long menuId: menuIds){
            RoleMenuRelation relation =new RoleMenuRelation();
            relation.setRoleId(roleId);
            relation.setMenuId(menuId);
            relationList.add(relation);
        }
        roleMenuRelationService.saveBatch(relationList);
        return menuIds.size();
    }

    @Override
    public int allocResource(Long roleId, List<Long> resourceIds) {
        QueryWrapper<RoleResourceRelation>wrapper =new QueryWrapper<>();
        wrapper.lambda().eq(RoleResourceRelation::getResourceId,roleId);
        roleResourceRelationService.remove(wrapper);
        List<RoleResourceRelation>relationList = new ArrayList<>();
        for(Long resourceId:resourceIds){
            RoleResourceRelation relation = new RoleResourceRelation();
            relation.setRoleId(roleId);
            relation.setResourceId(resourceId);
            relationList.add(relation);
        }
        roleResourceRelationService.saveBatch(relationList);
        return resourceIds.size();
    }
}
