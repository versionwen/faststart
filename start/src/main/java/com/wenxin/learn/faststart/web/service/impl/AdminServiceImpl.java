package com.wenxin.learn.faststart.web.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.generator.MathGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wenxin.learn.faststart.web.domain.AdminUserDetails;
import com.wenxin.learn.faststart.web.entity.Admin;
import com.wenxin.learn.faststart.web.entity.UmsResource;
import com.wenxin.learn.faststart.web.mapper.AdminMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wenxin.learn.faststart.web.mapper.UmsResourceMapper;
import com.wenxin.learn.faststart.web.service.AdminService;
import com.wenxin.learn.faststart.web.utils.IpUtil;
import com.wenxin.learn.faststart.web.utils.JwtTokenUtil;
import com.wenxin.learn.faststart.web.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author version
 * @since 2020-09-26
 */
@Service
@Slf4j
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService,UserDetails {
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UmsResourceMapper resourceMapper;
    @Autowired
    private RedisUtils redisUtils;
    @Override
    public UserDetails loadUserByUsername(String username){
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        QueryWrapper<Admin> result = wrapper.eq("username", username);
        //获取用户信息
        Admin admin = adminMapper.selectOne(result);
        if (admin != null) {
            List<UmsResource> resourceList = resourceMapper.getResourceList(admin.getId());
            return new AdminUserDetails(admin,resourceList);
            }
        return null;
    }

    @Override
    public boolean Register(String username, String password, String Email) {
        Admin admin = new Admin();
        admin.setUsername(username);
        String passwordEncode = passwordEncoder.encode(password);
        admin.setPassword(passwordEncode);
        admin.setEmail(Email);
        admin.setStatus(1);
        admin.setCreateTime(new Date());
        //查询是否有重名用户
        QueryWrapper<Admin>wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Admin::getUsername,admin.getUsername());
        List<Admin>adminList = list(wrapper);
        if(adminList.size() > 0){
            return false ;
        }
        try {
            adminMapper.insert(admin);
        }
        catch (Exception e){
            log.error("插入数据库出现错误，错误提示：{}",e);
            return false;
        }
        return true;
    }

    @Override
    public String getCaptcha(String request) {
        String ipAddr = request;
       //对本地ip进行替换
        if ("0:0:0:0:0:0:0:1".equals(ipAddr)){
            ipAddr = "127.0.0.1";
        }
        log.info("获取到的ip地址为:{}",ipAddr);
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 100, 4, 4);
        String result = "data:image/png;base64,"+captcha.getImageBase64();
        log.info("captcha={}",captcha.getCode());
        redisUtils.set(ipAddr,captcha.getCode(),60L);
        return result;
    }

    @Override
    public boolean verifyCaptcha(String captcha,String ipAddr) {
        if ("0:0:0:0:0:0:0:1".equals(ipAddr)){
            ipAddr = "127.0.0.1";
        }
        String rightCaptcha =(String)redisUtils.get(ipAddr);
        log.info("rightCaptcha={}",rightCaptcha);
        if(rightCaptcha == null||captcha == null){
            return false;
        }
        if(captcha.equals(rightCaptcha)){
            return true ;
        }
        else {
            return false;
        }
    }


    @Override
    public String login(String username, String userpassword) {
        String token = null;
        try{
            UserDetails userDetails =loadUserByUsername(username);
            if(!passwordEncoder.matches(userpassword,userDetails.getPassword())){
                log.error("用户密码错误");
            }
            token = jwtTokenUtil.generateToken(userDetails);
        }catch (Exception e){
            log.error("出现错误{}",e);
        }
        return token;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
