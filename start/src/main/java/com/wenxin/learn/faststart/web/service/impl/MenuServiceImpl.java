package com.wenxin.learn.faststart.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wenxin.learn.faststart.web.dto.MenuNode;
import com.wenxin.learn.faststart.web.entity.Menu;
import com.wenxin.learn.faststart.web.mapper.MenuMapper;
import com.wenxin.learn.faststart.web.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 后台菜单表 服务实现类
 * </p>
 *
 * @author version
 * @since 2020-10-11
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public boolean create(Menu menu) {
        menu.setCreateTime(new Date());
        updateLevel(menu);
        return save(menu);
    }
    @Override
    public boolean update(Long id,Menu menu){
        menu.setId(id);
        updateLevel(menu);
        return updateById(menu);
    }
    @Override
    public List<MenuNode> treeList() {
        List<Menu> menuList = list();
        List<MenuNode> result = menuList.stream()
                .filter(menu -> menu.getParentId().equals(0L))
                .map(menu -> covertMenuNode(menu, menuList)).collect(Collectors.toList());
        return result;
    }

    @Override
    public Page<Menu> list(Long parentId, Integer pageSize, Integer pageNum) {
        Page<Menu> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Menu::getParentId,parentId).orderByDesc(Menu::getSort);
        return page(page,wrapper);
    }

    @Override
    public boolean updateHidden(Long id, Integer hidden) {
        Menu menu = new Menu();
        menu.setId(id);
        menu.setHidden(hidden);
        return updateById(menu);
    }

    /**
     * 将Menu转化为MenuNode并设置children属性
     */
   private MenuNode covertMenuNode(Menu menu,List<Menu> menuList){
        MenuNode node = new MenuNode();
       BeanUtils.copyProperties(menu,node);
       List<MenuNode>children = menuList.stream().filter(subMenu -> subMenu.getParentId().equals(menu.getId())).map(subMenu -> covertMenuNode(subMenu, menuList)).collect(Collectors.toList());
       node.setChildren(children);
       return node;
   }

    /**
    * @Description: 修改菜单层级
    * @Param:  menu 菜单
    * @return:  null
    * @Author: version
    * @Date: 2020/10/11
    */
    private void updateLevel(Menu menu){
        if(menu.getParentId() == 0){
            //本身是顶级菜单
            menu.setLevel(0);
        }else {
            //存在父菜单按照父菜单leve设置
            Menu parentMenu = getById(menu.getParentId());
            if(parentMenu != null){
                menu.setLevel(parentMenu.getLevel()+1);
            }else {
                menu.setLevel(0);
            }
        }
    }
}
