package com.example.twitteruserservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqListener {

    private final ObjectMapper objectMapper;
    private final RabbitTemplate template;

    public RabbitMqListener(ObjectMapper objectMapper, RabbitTemplate template) {
        this.objectMapper = objectMapper;
        this.template = template;
    }

    private static final Logger log = LoggerFactory.getLogger(RabbitMqListener.class);

    @RabbitListener(queues = "q.authenticate")
    public void receiveMessage(String jwtToken) {
        System.out.println("Received JWT token: " + jwtToken); //receive the JWT

        template.convertAndSend("x.user-service", "token", jwtToken);
    }

}


