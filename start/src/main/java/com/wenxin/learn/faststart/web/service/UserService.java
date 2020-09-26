package com.wenxin.learn.faststart.web.service;

import com.wenxin.learn.faststart.web.entity.orderUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author versionwen
 * @since 2020-09-05
 */
public interface UserService extends IService<orderUser> {
    /**
     * 获取用户信息
     */
    UserDetails loadUserByUsername(String username);
    /**
    * @Description: 用户登录
    * @Param:  username 用户名
    * @Param：userpassword 用户密码
    * @return:  登录成功以后的token
    * @Author: version
    * @Date: 2020/9/25
    */
    String login(String username,String userpassword);
}
