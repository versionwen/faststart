package com.wenxin.learn.faststart.web.service;

import com.wenxin.learn.faststart.web.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author version
 * @since 2020-09-26
 */
public interface AdminService extends IService<Admin>  {
/**
* @Description: 管理员登录
* @Param:  username 用户名
*@Param:   password 密码
* @return:  token 登录成功以后的token
* @Author: version
* @Date: 2020/9/26
*/
String login(String username,String userpassword);
/**
* @Description: 按用户名查询
* @Param:  username 用户名
* @return:  查询结果
* @Author: version
* @Date: 2020/9/26
*/
UserDetails loadUserByUsername(String username);
}