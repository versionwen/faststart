package com.wenxin.learn.faststart.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.wenxin.learn.faststart.web.domain.AdminUserDetails;
import com.wenxin.learn.faststart.web.entity.UmsResource;
import com.wenxin.learn.faststart.web.entity.orderUser;
import com.wenxin.learn.faststart.web.mapper.UserMapper;
import com.wenxin.learn.faststart.web.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wenxin.learn.faststart.web.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author versionwen
 * @since 2020-09-05
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, orderUser> implements UserService, UserDetailsService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Override
    public UserDetails loadUserByUsername(String username){
        QueryWrapper<orderUser>wrapper = new QueryWrapper<>();
        QueryWrapper<orderUser> result = wrapper.eq("username", username);
        //获取用户信息
        orderUser admin = userMapper.selectOne(result);
       if (admin != null) {
//           TODO 这里的授权操作没有实现
           String role = admin.getRole();
           List<GrantedAuthority>authorities = new ArrayList<>();
           authorities.add(new SimpleGrantedAuthority("ROLE_"+role));
           //           orderUser userDetails = new orderUser(admin.getUsername(),admin.getPassword(),"");
           UserDetails userDetails1 = new UserDetails() {
               @Override
               public Collection<GrantedAuthority> getAuthorities() {
                   return authorities;
               }

               @Override
               public String getPassword() {
                   return admin.getPassword();
               }

               @Override
               public String getUsername() {
                   return admin.getUsername();
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
           };
           return  userDetails1;
        }
        throw new UsernameNotFoundException("用户名或密码错误");
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

}
