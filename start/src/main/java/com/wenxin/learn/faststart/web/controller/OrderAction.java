package com.wenxin.learn.faststart.web.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.wenxin.learn.faststart.web.service.OrderService;
import com.wenxin.learn.faststart.web.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import com.wenxin.learn.faststart.web.utils.RedisUtils;
/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author version
 * @since 2020-08-27
 */
@RestController
@RequestMapping("/order")
public class OrderAction {


    @Autowired
    private OrderService orderService;
    @Autowired
    private RedisUtils redisUtils;
    @GetMapping(value = "/")//得到分页列表
    @PreAuthorize("hasAnyRole('admin')")
    public ResponseEntity<Page<Order>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<Order> aPage = orderService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")//按照id查询
    public ResponseEntity<Order> getById(@PathVariable("id") String id) {
        String strJson = (String)redisUtils.get("orderId");
        if(strJson == null){
            System.out.println("从DB中取值");
            Order order = orderService.getById(id);
            if(order != null){
                redisUtils.set("orderId", JSON.toJSONString(order));
                return new ResponseEntity<>(orderService.getById(id), HttpStatus.OK);
            }
        }
        else {
            System.out.println("从redis缓存中取值");
            return new ResponseEntity<>(JSON.parseObject(strJson,Order.class),HttpStatus.OK);
        }
        return new ResponseEntity<>(orderService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")//新增用户
    public ResponseEntity<Object> create(@RequestBody Order params) {
        orderService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        orderService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody Order params) {
        orderService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
