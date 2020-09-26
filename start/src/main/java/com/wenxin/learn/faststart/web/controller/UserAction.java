package com.wenxin.learn.faststart.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wenxin.learn.faststart.web.entity.orderUser;
import com.wenxin.learn.faststart.web.entity.user.LoginUser;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.wenxin.learn.faststart.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author versionwen
 * @since 2020-09-05
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserAction {


    @Autowired
    private UserService userService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<orderUser>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<orderUser> aPage = userService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<orderUser> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody orderUser params) {
        userService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }
    @PostMapping(value = "/login")
    public ResponseEntity<Object>login(@RequestBody LoginUser loginUser){
        log.info("login success");
        String userName =loginUser.getUserName();
        String userPassword = loginUser.getPassWord();
        String token = userService.login(userName,userPassword);
        if(token!=null) {
            return new ResponseEntity<Object>(token, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<Object>( "你在逗我",HttpStatus.BAD_REQUEST);
        }
    }
    @ApiOperation(value = "根据主键ID删除", notes = "根据主键ID删除~~~~~~~~~~~~~")
    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        userService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody orderUser params) {
        userService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
