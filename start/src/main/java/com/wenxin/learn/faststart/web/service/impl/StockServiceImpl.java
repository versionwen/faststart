package com.wenxin.learn.faststart.web.service.impl;

import com.wenxin.learn.faststart.web.entity.Stock;
import com.wenxin.learn.faststart.web.mapper.StockMapper;
import com.wenxin.learn.faststart.web.service.StockService;
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
@Service("StockService")
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements StockService {

}
