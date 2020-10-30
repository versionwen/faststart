package com.wenxin.learn.faststart.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wenxin.learn.faststart.web.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author version
 * @version 1.0
 * @date 2020/10/29 10:19
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    /**
    * @Description:  获取所有的角色
    * @Param:  adminId  管理员ID
    * @return:  List Role列表
    * @Author: version
    * @Date: 2020/10/29
    */
    List<Role>getRoleList(@Param("adminId") Long adminId);
}
