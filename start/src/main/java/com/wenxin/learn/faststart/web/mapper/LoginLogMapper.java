package com.wenxin.learn.faststart.web.mapper;

import com.wenxin.learn.faststart.web.entity.LoginLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 后台用户登录日志表 Mapper 接口
 * </p>
 *
 * @author version
 * @since 2020-10-10
 */
@Mapper
public interface LoginLogMapper extends BaseMapper<LoginLog> {

}
