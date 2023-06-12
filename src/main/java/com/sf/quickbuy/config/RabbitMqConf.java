package com.sf.quickbuy.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConf {
    public static final String QUEUE_NAME = "buyRecordQ";
    public static final String EXCHANGE_NAME = "myExchange";

    @Bean("buyRecordQ")
    public Queue getQueue(){
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    @Bean("myExchange")
    public TopicExchange getExchange(){
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding queueBinding(Queue queue, TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("key");
    }


}
