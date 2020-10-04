package com.wenxin.learn.faststart.web.rabbitmq;

import cn.hutool.extra.mail.MailUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author version
 * @version 1.0
 * @date 2020/10/4 15:02
 */
@Component
@Slf4j
@RabbitListener(queues = "topic.mail")
public class RabbitMQReceiver {
    @RabbitHandler
    public void receiver(String MQMessage) {
        try {
            Map<Object,Object> map = (Map)JSON.parse(MQMessage);
            log.info("接收到的MQMessage={}",MQMessage.toString());
            String email =String.valueOf( map.get("email"));
            log.info("email={}",email);
            String code = String.valueOf(map.get("code"));
            log.info("code={}",code);
           MailUtil.send(email, "这是来自faststart的验证信息", "您的验证码为"+code+",验证码15分钟有效", false);

        }catch (Exception e){
            log.error("rabbitMQ消费消息出现错误，错误信息为：{}",e);
        }

    }

}
