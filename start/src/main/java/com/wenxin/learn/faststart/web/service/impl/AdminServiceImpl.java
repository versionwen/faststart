package com.wenxin.learn.faststart.web.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wenxin.learn.faststart.web.domain.AdminUserDetails;
import com.wenxin.learn.faststart.web.dto.UpdateAdminPasswordParam;
import com.wenxin.learn.faststart.web.entity.*;
import com.wenxin.learn.faststart.web.mapper.AdminMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wenxin.learn.faststart.web.mapper.LoginLogMapper;
import com.wenxin.learn.faststart.web.mapper.RoleMapper;
import com.wenxin.learn.faststart.web.mapper.UmsResourceMapper;
import com.wenxin.learn.faststart.web.service.AdminRoleRelationService;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
    @Autowired
    private LoginLogMapper loginLogMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AdminRoleRelationService adminRoleRelationService;
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
        String uuid = request;
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 100, 4, 4);
        log.info("获取到的UUID为:{}",uuid);
//        captcha.setGenerator(new MathGenerator());
        String result = "data:image/png;base64," + captcha.getImageBase64();
        log.info("captcha={}", captcha.getCode().toUpperCase());
        redisUtils.set(uuid, captcha.getCode().toUpperCase(), 60L);
        return result;

    }

    @Override
    public boolean verifyCaptcha(String captcha,String uuid) {
        String rightCaptcha =(String)redisUtils.get(uuid);
        log.info("rightCaptcha={}",rightCaptcha);
        if(rightCaptcha == null||captcha == null){
            return false;
        }
        if(captcha.toUpperCase().equals(rightCaptcha)){
            return true ;
        }
        else {
            return false;
        }
    }

    @Override
    public String refreshToken(String oldToken) {

        return jwtTokenUtil.refreshHeadToken(oldToken);

    }

    @Override
    public void insertLoginLog(String username) {
        QueryWrapper<Admin>wrapper = new QueryWrapper<>();
        QueryWrapper<Admin> user = wrapper.eq("username", username);
        Admin userLog = adminMapper.selectOne(user);
        if(user == null){
            return;
        }
        LoginLog loginLog = new LoginLog();
        loginLog.setAdminId(userLog.getId());
        loginLog.setCreateTime(new Date());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String ua = request.getHeader("User-Agent");
        loginLog.setUserAgent(ua);
        loginLog.setIp(request.getRemoteAddr());
        loginLogMapper.insert(loginLog);
    }

    @Override
    public boolean update(Long id, Admin admin) {
        admin.setId(id);
        Admin rawAdmin = getById(id);
        if(rawAdmin.getPassword().equals(admin.getPassword())){
            //密码相同，不用修改
            admin.setPassword(null);
        }
        else {
            if(StrUtil.isEmpty(admin.getPassword())){
                admin.setPassword(null);
            }
            else {
                admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            }
        }
        boolean success = updateById(admin);
        return success;
    }

    @Override
    public Page<Admin> list(String keyword, Integer pageSize, Integer pageNum) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public int updateRole(Long adminId, List<Long> roleIds) {
        int count = roleIds == null ? 0 : roleIds.size();
        //先删除原来的关系
        QueryWrapper<AdminRoleRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(AdminRoleRelation::getAdminId,adminId);
        adminRoleRelationService.remove(wrapper);
        //建立新关系
        if (!CollectionUtils.isEmpty(roleIds)) {
            List<AdminRoleRelation> list = new ArrayList<>();
            for (Long roleId : roleIds) {
                AdminRoleRelation roleRelation = new AdminRoleRelation();
                roleRelation.setAdminId(adminId);
                roleRelation.setRoleId(roleId);
                list.add(roleRelation);
            }
            adminRoleRelationService.saveBatch(list);
        }
        return count;
    }

    @Override
    public List<Role> getRoleList(Long adminId) {
        return roleMapper.getRoleList(adminId);
    }

    @Override
    public List<UmsResource> getResourceList(Long adminId) {
        List<UmsResource> resourceList;
        resourceList = resourceMapper.getResourceList(adminId);
        return resourceList;
    }

    @Override
    public int updatePassword(UpdateAdminPasswordParam param) {
        if(StrUtil.isEmpty(param.getUsername())
                ||StrUtil.isEmpty(param.getOldPassword())
                ||StrUtil.isEmpty(param.getNewPassword())){
            return -1;
        }
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Admin::getUsername,param.getUsername());
        List<Admin> adminList = list(wrapper);
        if(CollUtil.isEmpty(adminList)){
            return -2;
        }
        Admin umsAdmin = adminList.get(0);
        if(!passwordEncoder.matches(param.getOldPassword(),umsAdmin.getPassword())){
            return -3;
        }
        umsAdmin.setPassword(passwordEncoder.encode(param.getNewPassword()));
        updateById(umsAdmin);
        return 1;
    }

    @Override
    public Admin getAdminByUsername(String username) {
        QueryWrapper<Admin>wrapper =new QueryWrapper<>();
        wrapper.lambda().eq(Admin::getUsername,username);
        Admin admin;
        List<Admin> adminList = list(wrapper);
        if (adminList != null && adminList.size() > 0) {
            admin = adminList.get(0);
            return admin;
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
