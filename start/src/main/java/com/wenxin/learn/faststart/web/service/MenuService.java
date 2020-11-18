package com.wenxin.learn.faststart.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wenxin.learn.faststart.web.dto.MenuNode;
import com.wenxin.learn.faststart.web.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 后台菜单表 服务类
 * </p>
 *
 * @author version
 * @since 2020-10-11
 */
public interface MenuService extends IService<Menu> {
    /**
    * @Description: 创建菜单
    * @Param:  Menu 菜单
    * @return:  boolean 创建成功状态
    * @Author: version
    * @Date: 2020/10/11
    */
    boolean create(Menu menu);
  /**
  * @Description: 树形返回菜单列表
  * @Param:  null
  * @return:  List 菜单列表
  * @Author: version
  * @Date: 2020/10/13
  */
  List<MenuNode> treeList();
  /**
  * @Description: 分页查询菜单
  * @Param:
   * parentId 父Id
   * PageSize 页大小
   * PageNum 页数
  * @return:  Page<Menu>
  * @Author: version
  * @Date: 2020/10/17
  */
  public Page<Menu> list(Long parentId,Integer pageSize,Integer pageNum);
  /**
  * @Description: 修改后台菜单
  * @Param:
   * id 菜单id
   * menu 菜单详情
  * @return: boolean
  * @Author: version
  * @Date: 2020/10/21
  */
  boolean update(Long id, Menu umsMenu);
  /**
  * @Description: 修改菜单显示状态
  * @Param:
   * id 菜单id
   * hidden：显示状态 1为显示，0为隐藏
  * @return:  Boolean 是否修改成功
  * @Author: version
  * @Date: 2020/10/21
  */
  public boolean updateHidden(Long id,Integer hidden);
}
