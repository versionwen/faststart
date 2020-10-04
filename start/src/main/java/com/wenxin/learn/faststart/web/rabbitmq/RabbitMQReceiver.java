package com.wenxin.learn.faststart.web.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author version
 * @version 1.0
 * @date 2020/10/4 15:02
 */
@Slf4j
@RabbitListener(queues = "topic.mail")
public class RabbitMQReceiver {
    @RabbitHandler
    public Map receiver(Map<String,Object> MQMessage) {
        log.info("接收到的MQMessage={}",MQMessage.toString());
        return MQMessage;
    }

}
