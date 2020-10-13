package com.wenxin.learn.faststart.web.controller;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.wenxin.learn.faststart.web.api.CommonResult;;
import com.wenxin.learn.faststart.web.entity.admin.AdminPO;
import com.wenxin.learn.faststart.web.entity.user.LoginUser;
import com.wenxin.learn.faststart.web.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
    private RedisUtils redisUtils;

    @Autowired
    private AdminService adminService;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Autowired
    RabbitTemplate rabbitTemplate;
    /**
    * @Description: 生成验证码
    * @Param:  request 用户的请求头
    * @return:   base64编码的图片
    * @Author: version
    * @Date: 2020/10/3
    */
    @ApiOperation(value = "获取验证码")
    @GetMapping(value = "/getCaptcha")
    public CommonResult getCaptcha(){
        //生成请求者需要的UUID
        String uuid = IdUtil.randomUUID();
        String captcha = adminService.getCaptcha(uuid);
        HashMap<String,String>captchaMap = new HashMap<>();
        if (captcha != null) {
            captchaMap.put(uuid,captcha);
            return CommonResult.success(captchaMap);
        }
        else {
            return CommonResult.failed("验证码生成失败");
        }
    }
    @GetMapping("/verifyCaptcha")
    @ApiOperation(value = "验证验证码是否正确")
    public CommonResult verifyCaptcha(@RequestParam("uuid") String uuid,@RequestParam("captcha") String captcha){
        boolean verifyCaptcha = adminService.verifyCaptcha(captcha, uuid);
        if(verifyCaptcha ){
            return CommonResult.success(true);
        }
        else {
            return CommonResult.failed("验证码错误");
        }
    }
    @GetMapping("/sentVerifyEmail")
    @ApiOperation(value = "验证管理员用户邮箱")
    public CommonResult sentVerifyEmail(@RequestParam("email") String email){
        Map<String,Object> map = new HashMap<>();
        int code = (int)((Math.random()*9+1)*100000);
        map.put("email",email);
        map.put("code",code);
        try {
            String message = JSON.toJSONString(map);
            rabbitTemplate.convertAndSend("topicExchange", "topic.mail",message);
            String uuid = IdUtil.randomUUID();
            redisUtils.set(uuid+":"+email,String.valueOf(code),15*60L);
            return CommonResult.success(uuid);
        }catch (Exception e){
            log.error("验证管理员邮箱出现错误，错误详细为:",e);
            return CommonResult.failed("发送邮件失败");
        }


    }
    @PostMapping("/verifyemail")
    @ApiOperation(value = "验证管理员邮箱")
    public CommonResult verifyemail(@RequestBody Map<Object,Object>map){
        String email = String.valueOf(map.get("email"));
        String uuid = String.valueOf(map.get("uuid"));
        String code = String.valueOf(map.get("code"));
        log.info("email={},code={},uuid={}",email,code,uuid);
        if(code == null||"".equals(code) || "null".equals(code)){
            return CommonResult.failed("验证码为空，验证失败");
        }
        String key = uuid+":"+email;
        String rightCode = String.valueOf(redisUtils.get(key));
        log.info("rightcode={}",rightCode);
        if(rightCode.equals(code)){
            return CommonResult.success(true);
        }
        else {
            return CommonResult.failed("邮箱验证失败");
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
            adminService.insertLoginLog(userName);
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
          if(registerResult){
              return CommonResult.success(admin);
          }
          else {
              return CommonResult.failed();
          }
    }
    @ApiOperation("修改指定用户信息")
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable Long id,@RequestBody Admin admin){
        boolean success = adminService.update(id, admin);
        if(success){
            return CommonResult.success("修改成功");
        }
        return CommonResult.failed("修改个人信息出错");
    }
    @ApiOperation(value = "刷新用户token")
    @PostMapping(value = "/refreshtoken")
    public CommonResult refreshToken(HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        log.info("获取到的token为：{}",token);
        String refreshToken = adminService.refreshToken(token);
        if(refreshToken == null){
            return CommonResult.failed("token已失效");
        }
        Map<String,String>tokenResult = new HashMap<>();
        tokenResult.put("token",refreshToken);
        tokenResult.put("tokenHead",tokenHead);
        return CommonResult.success(tokenResult);
    }
}
