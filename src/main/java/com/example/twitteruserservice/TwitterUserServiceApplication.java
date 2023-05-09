package com.example.twitteruserservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TwitterUserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TwitterUserServiceApplication.class, args);
    }
}
