package com.example.twitteruserservice.service;

import com.example.twitteruserservice.config.AuthenticationResponse;
import com.example.twitteruserservice.config.JwtService;
import com.example.twitteruserservice.config.RegisterRequest;
import com.example.twitteruserservice.exception.RequestException;
import com.example.twitteruserservice.model.Role;
import com.example.twitteruserservice.model.User;
import com.example.twitteruserservice.repository.UserRepository;
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

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    private RabbitTemplate template;

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder, JwtService jwtService) {
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

    //get user by id
    public User getUserById(int id) {
        try {
            return userRepo.findById(id).orElse(null);
        } catch (Exception e) {
            throw new RequestException("Cannot get user by id");
        }
    }

    // Delete user by email
//    public String deleteUserByEmail(String email) {
//        try {
//            // Delete the user by email
//            userRepo.deleteByEmail(email);
//            return "User deleted: " + email;
//        } catch (Exception e) {
//            throw new RequestException("Cannot delete user");
//        }
//    }

    public String deleteUserByEmail(String email) {
        try {
            // Find the user by email
            Optional<User> user = userRepo.findByEmail(email);

            if (user.isPresent()) {
                userRepo.delete(user.get());
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
            User existingUser = userRepo.findById(user.getId()).orElse(null);
            assert existingUser != null;
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            return userRepo.save(existingUser);
        } catch (Exception e) {
            throw new RequestException("Cannot edit user");
        }
    }
}