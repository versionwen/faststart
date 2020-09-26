package com.wenxin.learn.faststart.web.controller;

import com.wenxin.learn.faststart.web.entity.user.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.wenxin.learn.faststart.web.service.AdminService;
import com.wenxin.learn.faststart.web.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;

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
public class AdminAction {

    @Autowired
    private AdminService adminService;

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
    public ResponseEntity<Object>login(@RequestBody LoginUser loginUser){
        log.info("login success");
        String userName =loginUser.getUserName();
        String userPassword = loginUser.getPassWord();
        String token = adminService.login(userName,userPassword);
        if(token!=null) {
            return new ResponseEntity<Object>(token, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<Object>( "你在逗我",HttpStatus.BAD_REQUEST);
        }
    }
}
