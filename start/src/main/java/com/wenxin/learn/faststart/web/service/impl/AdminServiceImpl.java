package com.wenxin.learn.faststart.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wenxin.learn.faststart.web.domain.AdminUserDetails;
import com.wenxin.learn.faststart.web.entity.Admin;
import com.wenxin.learn.faststart.web.entity.UmsResource;
import com.wenxin.learn.faststart.web.mapper.AdminMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wenxin.learn.faststart.web.mapper.UmsResourceMapper;
import com.wenxin.learn.faststart.web.service.AdminService;
import com.wenxin.learn.faststart.web.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
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
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService,UserDetails {
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UmsResourceMapper resourceMapper;
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
