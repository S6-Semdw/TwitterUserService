package com.example.twitteruserservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Bean
    public Declarables createUser(){
        return new Declarables(
                new FanoutExchange("x.user-service"),
                new Queue("q.userRegister" ),
                new Queue("q.userUpdate" ),
                new Queue("q.saveUser" ),
                new Queue("q.userDelete" ),
                new Binding("q.userRegister", Binding.DestinationType.QUEUE, "x.user-service", "userRegister", null),
                new Binding("q.userUpdate", Binding.DestinationType.QUEUE, "x.user-service", "userUpdate", null),
                new Binding("q.saveUser", Binding.DestinationType.QUEUE, "x.user-service", "saveUser", null),
                new Binding("q.userDelete", Binding.DestinationType.QUEUE, "x.user-service", "userDelete", null));
    }
}

