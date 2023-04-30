package com.example.twitteruserservice.service;

import com.example.twitteruserservice.exception.RequestException;
import com.example.twitteruserservice.model.User;
import com.example.twitteruserservice.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepo;

    @Autowired
    private RabbitTemplate template;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    //get users
    public List<User> getUsers() {
        try {
            return userRepo.findAll();
        } catch (Exception e) {
            throw new RequestException("Cannot get all users");
        }
    }

    //get user by id
    public User getUserById(int id) {
        try {
            return userRepo.findById(id).orElse(null);
        } catch (Exception e) {
            throw new RequestException("Cannot get user by id");
        }
    }

    //post user
    public User saveUser(User user) {
        try {
            userRepo.save(user);
            String json = new ObjectMapper().writeValueAsString(user);
            template.convertAndSend("x.user-service", "saveUser", json);
            return user;
        } catch (Exception e) {
            log.error("Error creating user: " + e.getMessage());
            throw new RequestException("Cannot create user", e);
        }
    }

//    public User saveUser(User user) {
//        try {
//            userRepo.save(user);
//            template.convertAndSend("x.user-service", "saveUser", user);
//            return user;
//        } catch (AmqpException e) {
//            log.error("Error sending message to RabbitMQ: " + e.getMessage());
//            throw new RequestException("Cannot create user", e);
//        } catch (Exception e) {
//            log.error("Error creating user: " + e.getMessage());
//            throw new RequestException("Cannot create user", e);
//        }
//    }


    //delete user
    public String deleteUser(int id) {
        try {
            userRepo.deleteById(id);
            return "User deleted" + id;
        } catch (Exception e) {
            throw new RequestException("Cannot delete user");
        }
    }

    //update user
    public User updateUser(User user) {
        try {
            User existingUser = userRepo.findById(user.getId()).orElse(null);
            assert existingUser != null;
            existingUser.setFirstname(user.getFirstname());
            existingUser.setLastname(user.getLastname());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            return userRepo.save(existingUser);
        } catch (Exception e) {
            throw new RequestException("Cannot edit user");
        }
    }
}