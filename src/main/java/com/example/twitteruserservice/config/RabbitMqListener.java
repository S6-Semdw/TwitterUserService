package com.example.twitteruserservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqListener {

    private final ObjectMapper objectMapper;

    public RabbitMqListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private static final Logger log = LoggerFactory.getLogger(RabbitMqListener.class);

    @RabbitListener(queues = "q.authenticate")
    public void receiveMessage(String jwtToken) {
        System.out.println("Received JWT token: " + jwtToken); //receive the JWT
    }

}


