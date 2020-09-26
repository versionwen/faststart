package com.wenxin.learn.faststart.web.mapper;

import com.wenxin.learn.faststart.web.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.wenxin.learn.faststart.web.dto.SelectbyIdResultDto;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author version
 * @since 2020-08-27
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    List<SelectbyIdResultDto> selectbyId(int id);
}
