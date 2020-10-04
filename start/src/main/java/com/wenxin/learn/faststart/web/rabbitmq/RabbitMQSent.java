package com.wenxin.learn.faststart.web.rabbitmq;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author version
 * @version 1.0
 * @date 2020/10/4 15:14
 */
@Slf4j
@Component
public class RabbitMQSent {
    @Autowired
    RabbitTemplate rabbitTemplate;
    /**
    * @Description: 发送消息到消息队列
    * @Param:  Map<String,Object> message 需要发送的消息
    * @return:  boolean 发送成功状态
    * @Author: version
    * @Date: 2020/10/4
    */
    public boolean sendTopicMail(Map<String,Object> message) {
        log.info("sendTopicMail发送的消息为：{}",message.toString());
       rabbitTemplate.convertAndSend("topicExchange", "topic.mail", message);
        return true;
    }

}
