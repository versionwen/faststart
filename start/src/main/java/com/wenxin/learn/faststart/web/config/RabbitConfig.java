package com.wenxin.learn.faststart.web.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author version
 * @version 1.0
 * @date 2020/10/4 11:43
 */
@Configuration
public class RabbitConfig {
    //绑定键
    public final static String mail = "topic.mail";
    public final static String sms = "topic.sms";

    @Bean
    public Queue firstQueue() {
        return new Queue(RabbitConfig.mail);
    }

    @Bean
    public Queue secondQueue() {
        return new Queue(RabbitConfig.sms);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("topicExchange");
    }


    //将firstQueue和topicExchange绑定,而且绑定的键值为topic.mail
    //这样只要是消息携带的路由键是topic.mail,才会分发到该队列
    @Bean
    Binding bindingExchangeMessage() {
        return BindingBuilder.bind(firstQueue()).to(exchange()).with(mail);
    }

    //将secondQueue和topicExchange绑定,
    @Bean
    Binding bindingExchangeMessage2() {
        return BindingBuilder.bind(secondQueue()).to(exchange()).with("topic.sms");
    }

}
