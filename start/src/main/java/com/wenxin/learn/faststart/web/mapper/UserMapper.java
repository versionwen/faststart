package com.wenxin.learn.faststart.web.mapper;

import com.wenxin.learn.faststart.web.entity.orderUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author versionwen
 * @since 2020-09-05
 */
@Mapper
public interface UserMapper extends BaseMapper<orderUser> {

}
