package com.wenxin.learn.faststart.web.mapper;

import com.wenxin.learn.faststart.web.entity.Image;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户上传的图片 Mapper 接口
 * </p>
 *
 * @author version
 * @since 2020-10-29
 */
@Mapper
public interface ImageMapper extends BaseMapper<Image> {

}
