package com.example.twitteruserservice.config;

import com.example.twitteruserservice.model.Tweet;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.List;

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

        template.convertAndSend("x.user-token", "token", jwtToken);
    }

    @RabbitListener(queues = "sendUser")
    public void getMessage(String email) {
        try {
            System.out.println(email); //receive the email

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @RabbitListener(queues = "sendTweetUser")
    public void receiveTweet(String payload, @Header("email") String email) throws Exception {
        try {
            // Deserialize the JSON payload to a list of tweets
            List<Tweet> tweets = objectMapper.readValue(payload, new TypeReference<List<Tweet>>() {});

            // Process the email and tweets as desired
            System.out.println("Email: " + email);
            for (Tweet tweet : tweets) {
                System.out.println("Tweet: " + tweet.getText());
            }

        } catch (Exception e) {
            throw new Exception("Failed to process the received message: " + e.getMessage(), e);
        }
    }

}


