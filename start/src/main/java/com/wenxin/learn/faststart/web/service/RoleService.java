package com.wenxin.learn.faststart.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wenxin.learn.faststart.web.entity.Menu;
import com.wenxin.learn.faststart.web.entity.Resource;
import com.wenxin.learn.faststart.web.entity.Role;
import com.wenxin.learn.faststart.web.entity.UmsResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author version
 * @version 1.0
 * @date 2020/10/29 10:22
 */
public interface RoleService extends IService<Role> {
    /**
    * @Description:  添加角色
    * @Param:  Role 类型
    * @return:  Boolean 是否添加成功
    * @Author: version
    * @Date: 2020/10/29
    */
    boolean create(Role role);
    /**
    * @Description: 批量删除角色
    * @Param:  List ids 批量删除角色
    * @return:  Boolean 是否删除成功
    * @Author: version
    * @Date: 2020/10/29
    */
    boolean delete(List<Long> ids);
    /**
    * @Description:  分页获取角色列表
    * @return:  角色列表List
    * @Author: version
    * @Date: 2020/10/29
    */
    Page<Role>list(String keyword,Integer pageSize,Integer pageNum);
    /**
    * @Description: 根据管理员ID获取对应菜单
    * @Param:
    * @return:
    * @Author: version
    * @Date: 2020/10/29
    */
    List<Menu> getMenuList(Long adminId);
    /**
    * @Description: 获取角色相关菜单
    * @Param:
    * @return:
    * @Author: version
    * @Date: 2020/10/29
    */
    List<Menu>listMenu(Long roleId);
    /**
    * @Description: 获取角色相关资源
    * @Author: version
    * @Date: 2020/10/29
    */
    List<UmsResource>listResource(Long roleId);
    /**
    * @Description: 给角色分配菜单
    * @Param:
    * @return:
    * @Author: version
    * @Date: 2020/10/29
    */
    @Transactional
    int allocMenu(Long roleId,List<Long>menuIds);
    /**
    * @Description: 给角色分配资源
    * @Author: version
    * @Date: 2020/10/29
    */
    @Transactional
    int allocResource(Long roleId,List<Long> resourceIds);
}
