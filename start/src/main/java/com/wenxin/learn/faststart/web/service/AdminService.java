package com.wenxin.learn.faststart.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wenxin.learn.faststart.web.dto.UpdateAdminPasswordParam;
import com.wenxin.learn.faststart.web.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wenxin.learn.faststart.web.entity.Role;
import com.wenxin.learn.faststart.web.entity.UmsResource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author version
 * @since 2020-09-26
 */
public interface AdminService extends IService<Admin> {
 /**
  * @Description: 管理员登录
  * @Param: username 用户名
  * @Param: password 密码
  * @return: token 登录成功以后的token
  * @Author: version
  * @Date: 2020/9/26
  */
 String login(String username, String userpassword);

 /**
  * @Description: 按用户名查询
  * @Param: username 用户名
  * @return: 查询结果
  * @Author: version
  * @Date: 2020/9/26
  */
 UserDetails loadUserByUsername(String username);

 /**
  * @Description: 用户注册
  * @Param: username 用户名
  * password 密码
  * Email 邮箱
  * @return: 注册成功状态
  * @Author: version
  * @Date: 2020/10/3
  */
 boolean Register(String username, String password, String Email);

 /**
  * @Description: 获取验证码
  * @Param: request 用户的ip
  * @return: String 验证码图片的base64编码
  * @Author: version
  * @Date: 2020/10/3
  */
 String getCaptcha(String request);

 /**
  * @Description: 验证验证码
  * @Param: captcha 用户输入的验证码
  * ipAddr  用户的IP地址
  * @return: boolean 用户输入是否正确
  * @Author: version
  * @Date: 2020/10/3
  */
 boolean verifyCaptcha(String captcha, String uuid);

 /**
  * @Description: 刷新用户token
  * @Param:  oldToken 用户的旧token
  * @return: token 新的用户token
  * @Author: version
  * @Date: 2020/10/8
  */
 String refreshToken(String oldToken);
 /**
 * @Description: 添加进入登录日志
 * @Param:  username 用户名
 * @return:  void
 * @Author: version
 * @Date: 2020/10/10
 */
  void insertLoginLog(String username);
  /**
  * @Description: 更新用户信息
  * @Param:
   * id 用户id
   * admin 用户信息
  * @return:
  * @Author: version
  * @Date: 2020/10/10
  */
  boolean update(Long id,Admin admin);

 /**
  * 根据用户名或昵称分页查询用户
  */
 Page<Admin> list(String keyword, Integer pageSize, Integer pageNum);

 /**
  * 删除指定用户
  */
 boolean delete(Long id);

 /**
  * 修改用户角色关系
  */
 @Transactional
 int updateRole(Long adminId, List<Long> roleIds);

 /**
  * 获取用户对于角色
  */
 List<Role> getRoleList(Long adminId);

 /**
  * 获取指定用户的可访问资源
  */
 List<UmsResource> getResourceList(Long adminId);

 /**
  * 修改密码
  */
 int updatePassword(UpdateAdminPasswordParam updatePasswordParam);
 /**
  * 根据用户名获取后台管理员
  */
 Admin getAdminByUsername(String username);
}