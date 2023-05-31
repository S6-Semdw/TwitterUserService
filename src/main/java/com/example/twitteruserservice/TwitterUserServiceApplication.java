package com.example.twitteruserservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class TwitterUserServiceApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterUserServiceApplication.class);

    public static void main(String[] args) {
        LOGGER.info("Starting Twitter User Service Application");
        SpringApplication.run(TwitterUserServiceApplication.class, args);
        LOGGER.info("Twitter User Service Application started");
    }
}
