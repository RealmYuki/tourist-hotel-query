package com.ltcyu.hotel.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.ltcyu.hotel.constants.MqConstants.*;

/**
 * ClassName: MqConfig
 * Description:
 * {@code @Author} 苏羽晨
 * {@code @Create} 2023/10/20 15:31
 * {@code @Version}  1.0
 */
@Configuration
public class MqConfig {
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(HOTEL_EXCHANGE,true,false);
    }

    @Bean
    public Queue insertQueue(){
        return new Queue(HOTEL_INSERT_QUEUE,true);
    }

    @Bean
    public Queue deleteQueue(){
        return new Queue(HOTEL_DELETE_QUEUE,true);
    }

    @Bean
    public Binding insertQueueBinding(){
        return BindingBuilder.bind(insertQueue()).to(topicExchange()).with(HOTEL_INSERT_KEY);
    }

    @Bean
    public Binding deleteQueueBinding(){
        return BindingBuilder.bind(deleteQueue()).to(topicExchange()).with(HOTEL_DELETE_KEY);
    }
}
