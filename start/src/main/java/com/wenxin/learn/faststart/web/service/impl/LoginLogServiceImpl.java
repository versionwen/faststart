package com.wenxin.learn.faststart.web.service.impl;

import com.wenxin.learn.faststart.web.entity.LoginLog;
import com.wenxin.learn.faststart.web.mapper.LoginLogMapper;
import com.wenxin.learn.faststart.web.service.LoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户登录日志表 服务实现类
 * </p>
 *
 * @author version
 * @since 2020-10-10
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {

}
