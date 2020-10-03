package com.wenxin.learn.faststart.web.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.generator.MathGenerator;
import com.wenxin.learn.faststart.web.api.CommonResult;
import com.wenxin.learn.faststart.web.config.RedisConfig;
import com.wenxin.learn.faststart.web.entity.admin.AdminPO;
import com.wenxin.learn.faststart.web.entity.user.LoginUser;
import com.wenxin.learn.faststart.web.utils.IpUtil;
import com.wenxin.learn.faststart.web.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.wenxin.learn.faststart.web.service.AdminService;
import com.wenxin.learn.faststart.web.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 后台用户表 前端控制器
 * </p>
 *
 * @author version
 * @since 2020-09-26
 */
@RestController
@RequestMapping("/admin")
@Slf4j
@Api(tags = "AdminController",value = "后台用户管理")
public class AdminAction {

    @Autowired
    private AdminService adminService;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    /**
    * @Description: 生成验证码
    * @Param:  request 用户的请求头
    * @return:   base64编码的图片
    * @Author: version
    * @Date: 2020/10/3
    */
    @ApiOperation(value = "获取验证码")
    @GetMapping(value = "/getCaptcha")
    public CommonResult getCaptcha(HttpServletRequest request){
        String ipAddr = IpUtil.getIpAddr(request);
        String captcha = adminService.getCaptcha(ipAddr);
        if (captcha != null) {
            return CommonResult.success(captcha);
        }
        else {
            return CommonResult.failed("验证码生成失败");
        }
    }
    @GetMapping("/verifyCaptcha")
    @ApiOperation(value = "验证验证码是否正确")
    public CommonResult verifyCaptcha(HttpServletRequest request,@RequestParam("captcha") String captcha){
        String ipAddr = IpUtil.getIpAddr(request);
        boolean verifyCaptcha = adminService.verifyCaptcha(captcha, ipAddr);
        if(verifyCaptcha == true){
            return CommonResult.success(true);
        }
        else {
            return CommonResult.failed("验证码错误");
        }
    }
    @GetMapping(value = "/")
    public ResponseEntity<Page<Admin>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<Admin> aPage = adminService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Admin> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(adminService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody Admin params) {
        adminService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        adminService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody Admin params) {
        adminService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
    @ApiOperation(value = "管理员登录")
    @PostMapping(value = "/login")
    public CommonResult login(@RequestBody LoginUser loginUser){
        String userName =loginUser.getUserName();
        String userPassword = loginUser.getPassWord();
        log.info("login userName={},password={}",userName,userPassword);
        String token = adminService.login(userName,userPassword);
        if(token!=null) {
            Map<String,String>tokenMap = new HashMap<>();
            tokenMap.put("token",token);
            tokenMap.put("tokenHead",tokenHead);
            return CommonResult.success(tokenMap);
        }
        else {
            return CommonResult.failed("login failed");
        }
    }
    /**
    * @Description: 用户注册
    * @Param: Admin 管理员对象
    * @return: CommonResult 通用返回类型
    * @Author: version
    * @Date: 2020/10/3
    */
    @ApiOperation(value = "用户注册")
    @PostMapping(value = "/register")
    public CommonResult Resign(@RequestBody @Validated AdminPO admin){
          String username = admin.getUsername();
          String password = admin.getPassword();
          String Email = admin.getEmail();
          Boolean registerResult = adminService.Register(username,password,Email);
          if(registerResult == true){
              return CommonResult.success(admin);
          }
          else {
              return CommonResult.failed();
          }
    }
}
