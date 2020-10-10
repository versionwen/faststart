package com.wenxin.learn.faststart.web.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author version
 * @version 1.0
 * @date 2020/10/4 11:43
 */
@Configuration
@Slf4j
public class RabbitConfig {
    //绑定键
    public final static String mail = "topic.mail";
    public final static String sms = "topic.sms";

    @Bean
    public Queue mailQueue() {
        return new Queue(RabbitConfig.mail);
    }

    @Bean
    public Queue smsQueue() {
        return new Queue(RabbitConfig.sms);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("topicExchange");
    }


    //将mailQueue和topicExchange绑定,而且绑定的键值为topic.mail
    //这样只要是消息携带的路由键是topic.mail,才会分发到该队列
    @Bean
    Binding bindingExchangeMessagemail() {
        return BindingBuilder.bind(mailQueue()).to(exchange()).with(mail);
    }

    //将smsQueue和topicExchange绑定,
    @Bean
    Binding bindingExchangeMessagesms() {
        return BindingBuilder.bind(smsQueue()).to(exchange()).with(sms);
    }

}
