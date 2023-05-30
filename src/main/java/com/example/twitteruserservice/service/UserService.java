package com.example.twitteruserservice.service;

import com.example.twitteruserservice.config.AuthenticationResponse;
import com.example.twitteruserservice.config.JwtService;
import com.example.twitteruserservice.config.RegisterRequest;
import com.example.twitteruserservice.exception.RequestException;
import com.example.twitteruserservice.model.Role;
import com.example.twitteruserservice.model.User;
import com.example.twitteruserservice.repository.TwitterUserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final TwitterUserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    private RabbitTemplate template;

    public UserService(TwitterUserRepository userRepo, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public AuthenticationResponse register(RegisterRequest request) throws JsonProcessingException {
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        String json = new ObjectMapper().writeValueAsString(user);
        template.convertAndSend("x.user-service", "registerUser", json);
        userRepo.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }

    //get users
    public List<User> getUsers() {
        try {
            return userRepo.findAll();
        } catch (Exception e) {
            throw new RequestException("Cannot get all users");
        }
    }

    //get user by email
    public User getUserByEmail(String email) {
        try {
            template.convertAndSend("x.get-user", "getUser", email);
            template.convertAndSend("x.get-tweetUser", "getTweetUser", email);
            return userRepo.findByEmail(email).orElse(null);
        } catch (Exception e) {
            throw new RequestException("Failed to get user by email: " + e.getMessage(), e);
        }
    }



    public String deleteUserByEmail(String email) {
        try {
            // Find the user by email
            Optional<User> user = userRepo.findByEmail(email);

            if (user.isPresent()) {
                userRepo.delete(user.get());
                String json = new ObjectMapper().writeValueAsString(user.get()); // Extract User object using user.get()
                template.convertAndSend("x.delete.user-service", "userDelete", json);
                template.convertAndSend("x.user-delete", "deleteUser", json);
                return "User deleted: " + email;
            } else {
                return "User not found with email: " + email;
            }
        } catch (Exception e) {
            throw new RequestException("Cannot delete user");
        }
    }

    //update user
    public User updateUser(User user) {
        try {
            User existingUser = userRepo.findById(Integer.valueOf(user.getId())).orElse(null);
            assert existingUser != null;
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            return userRepo.save(existingUser);
        } catch (Exception e) {
            throw new RequestException("Cannot edit user");
        }
    }
}