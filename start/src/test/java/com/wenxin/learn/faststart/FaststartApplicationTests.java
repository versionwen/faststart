package com.wenxin.learn.faststart;

import cn.hutool.extra.mail.MailUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Assert;
//import com.wenxin.learn.faststart.web.config.JmsConfig;
import com.wenxin.learn.faststart.web.dto.SelectbyIdResultDto;
import com.wenxin.learn.faststart.web.entity.Admin;
import com.wenxin.learn.faststart.web.entity.orderUser;
import com.wenxin.learn.faststart.web.mapper.AdminMapper;
import com.wenxin.learn.faststart.web.mapper.OrderMapper;
//import com.wenxin.learn.faststart.web.mq.Producer;
import com.wenxin.learn.faststart.web.rabbitmq.RabbitMQSent;
import com.wenxin.learn.faststart.web.service.AdminService;
import com.wenxin.learn.faststart.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.client.exception.MQBrokerException;
//import org.apache.rocketmq.client.exception.MQClientException;
//import org.apache.rocketmq.client.producer.SendResult;
//import org.apache.rocketmq.common.message.Message;
//import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SpringBootTest
@Slf4j
class FaststartApplicationTests {
    @Autowired
    UserService userService;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    AdminService adminService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    private List<String> mesList;
    @Test
    void contextLoads() {
    }
    @Test
    void userTest(){

    }
    @Test
    void mailTest(){
//        MailUtil.send("1218156401@qq.com", "测试", "邮件来自faststart测试", false);
//        Map<String,Object>map =new HashMap<>();
//        map.put("wen","xin");
//        map.put("uuid","12234345");
////        JSONObject jsonObject = new JSONObject(map);
////        String messageT = jsonObject.toJSONString();
//        RabbitMQSent rabbitMQSent =new RabbitMQSent();
//       boolean b = rabbitMQSent.sendTopicMail(map);
//       System.out.println(b);
//        rabbitTemplate.convertAndSend("topicExchange", "topic.mail", messageT);
    }
//    @Test
//    void otherTest() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
//        mesList = new ArrayList<>();
//        mesList.add("小小");
//        mesList.add("爸爸");
//        mesList.add("妈妈");
//        mesList.add("爷爷");
//        mesList.add("奶奶");
//        //总共发送五次消息
//        for (String s : mesList) {
//            //创建生产信息
//            Message message = new Message(JmsConfig.TOPIC, "testtag", ("小小一家人的称谓:" + s).getBytes());
//            //发送
//            SendResult sendResult = producer.getProducer().send(message);
//            System.out.println("输出生产者信息={}="+sendResult);
//        }
//    }
}
//
