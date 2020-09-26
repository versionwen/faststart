package com.wenxin.learn.faststart.web.service.impl;

import com.wenxin.learn.faststart.web.entity.Order;
import com.wenxin.learn.faststart.web.mapper.OrderMapper;
import com.wenxin.learn.faststart.web.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author version
 * @since 2020-08-27
 */
@Service("OrderService")
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
