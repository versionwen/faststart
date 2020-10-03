package com.wenxin.learn.faststart.web.service;

import com.wenxin.learn.faststart.web.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;

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
/**
* @Description: 用户注册
* @Param:
 * username 用户名
 * password 密码
 * Email 邮箱
* @return:  注册成功状态
* @Author: version
* @Date: 2020/10/3
*/
 boolean Register(String username,String password,String Email);
/**
* @Description: 获取验证码
* @Param:  request 用户的ip
* @return:  String 验证码图片的base64编码
* @Author: version
* @Date: 2020/10/3
*/
String getCaptcha(String request);
/**
* @Description: 验证验证码
* @Param:
 * captcha 用户输入的验证码
 * ipAddr  用户的IP地址
* @return:  boolean 用户输入是否正确
* @Author:  version
* @Date: 2020/10/3
*/
boolean verifyCaptcha(String captcha,String ipAddr);
}