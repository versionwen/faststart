package com.wenxin.learn.faststart.web.mapper;

import com.wenxin.learn.faststart.web.entity.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 后台用户表 Mapper 接口
 * </p>
 *
 * @author version
 * @since 2020-09-26
 */
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

}
