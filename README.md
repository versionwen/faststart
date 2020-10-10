# faststart
[![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0) &ensp; &ensp; [![Build Status](https://travis-ci.org/xialonghua/kotmvp.svg?branch=master)](https://travis-ci.org/xialonghua/kotmvp)   

正如题目所描述这样，这是一款快速搭建web的一个脚手架，在线访问地址：www.faststart.wenxinblog.cn

## 为什么要做这么一个东西
其中自己平时会做一些小的项目，而权限管理是一个系统所必须的一部分，我也在网上看了很多项目脚手架，不是太过于臃肿，就是动不动就是分布式，高并发等，而我只是想做一个很基础的脚手架，我不想我自己做一些小项目就要安装一系列的软件，他要够简单，但是功能要够强大，同时要满足日后的扩展，所以我做出了这一款脚手架。
## 这个项目使用了哪些技术
这款脚手架化繁为简，使用了Springboot和和mysql同时写完了Redis，rocketmq(可扩展)，对于权限部分，采用了spring security来进行基于菜单的动态权限控制，同时使用JWT来进行授权操作，一旦用户进行登录，后续每个请求都将包含JWT，此外，为了加快CURD的开发，引进了mybatis-plus进行curd的简化开发，同时引进mybatis-plus-ui直接进行傻瓜试代码生成。相关技术选型如下表所示
| 技术|版本|说明|
|---|---|---|
|springBoot|2.3.0|容器与基本的MVC框架|
|SpringSecurity|5.3.2|认证和授权框架|
|MyBatis|3.5.4|ORM框架|
|MyBatis-Plus|3.3.2|MyBatis增强工具|
|mybatis-plus-generator-ui|1.3.0|简化CURD工具|
|Swagger-UI|2.9.2|自动文档生产工具|
|Redis|5.0|分布式缓存|
|JWT|0.9.0|JWT登录支持|
|Lombok|1.18.12|简化对象封装工具|
## 环境搭建
这款脚手架其实没有什么需要特别安装环境，最简单的环境就是java和mysql 5.7，当然自己也可以改一下用上mysql8.0，想体验缓存就需要把Redis安装上。
## 开发规范
项目包结构如下：

<pre>├─src
│  ├─main
│  │  ├─java
│  │  │  └─com
│  │  │      └─wenxin
│  │  │          └─learn
│  │  │              └─faststart
│  │  │                  └─web
│  │  │                      ├─api
│  │  │                      ├─config              存放配置相关信息
│  │  │                      │  └─security      存放spring security 相关文件
│  │  │                      │      ├─component    实现了一些spring security相关配置
│  │  │                      │      └─config        sping security相关的一些配置
│  │  │                      ├─controller   这里面是控制层的一些代码
│  │  │                      ├─domain   UserDetails对象的一些封装
│  │  │                      ├─dto        模块的数据传送封装对象
│  │  │                      ├─entity   实体类
│  │  │                      │  └─user
│  │  │                      ├─jwtconfig   jwt配置
│  │  │                      ├─mapper    dao层相关代码
│  │  │                      ├─mq      消息队列相关代码
│  │  │                      ├─service  服务器
│  │  │                      │  └─impl
│  │  │                      └─utils   一些工具文件夹
│  │  └─resources  资源相关文件夹
│  │      └─mapper
</pre>



##  项目运行

安装好环境以后，导入mysql脚本，在resource目录下有一个mail.seting,在这里面配置邮箱和密码
在application-dev.yml中请配置好rabbitmq的地址，用户名，密码，同时请配置好数据库的用户名与密码，在配置好这些以后
直接启动FaststartApplication

## CURD开发流程

本项目引进了mybatis-plus-generator-ui，这个ui能大大简化代码的开发，最强大功能是这个软件能直接生成controller，serviec，mapper，model，mapper.xml代码，使用方法是在test目录下，有一个为GeberatorUIServer的文件，直接运行该文件，然后访问localhost：8068即可

使用该工具生成的代码如下

```java
/**
 * @author version
 * @since 2020-08-27
 */
@RestController
@RequestMapping("/order")
@Slf4j
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
            log.info("从DB中取值");
            Order order = orderService.getById(id);
            if(order != null){
                redisUtils.set("orderId", JSON.toJSONString(order));
                return new ResponseEntity<>(orderService.getById(id), HttpStatus.OK);
            }
        }
        else {
            log.info("从redis缓存中取值");
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

```

## 项目部署问题

这一块目前这个软件还没有完成，后期计划集成docker插件，直接进行docker部署

## 其他要说明的

本项目使用了Swagger-UI接口，访问的接口地址是localhost：8080/swagger-ui.html

调用登录接口获取token进行相关操作

点击Authorize输入token ，进行测试相关的接口就可以了

