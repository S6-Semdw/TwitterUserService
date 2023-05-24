package com.example.twitteruserservice.controller;

import com.example.twitteruserservice.config.AuthenticationResponse;
import com.example.twitteruserservice.config.RegisterRequest;
import com.example.twitteruserservice.exception.RequestException;
import com.example.twitteruserservice.model.User;
import com.example.twitteruserservice.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/")
@CrossOrigin(origins="http://localhost:3000")
@Controller
@RestController
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) throws JsonProcessingException {
        return ResponseEntity.ok(service.register(request));
    }

    //Get tweets
    @GetMapping(value = "/users")
    public List<User> findAllUsers() {
        try {
            return service.getUsers();
        } catch (Exception e) {
            throw new RequestException("Cannot get all users");
        }
    }

    @GetMapping(value = "/user/{id}")
    public User findUserById(@PathVariable int id) {
        try {
            return service.getUserById(id);
        } catch (Exception e) {
            throw new RequestException("Cannot get user by id");
        }
    }

    @DeleteMapping(value = "/delete/{email}")
    public String deleteUserByEmail(@PathVariable String email) {
        try {
            return service.deleteUserByEmail(email);
        } catch (Exception e) {
            throw new RequestException("Cannot delete user");
        }
    }

    @PutMapping(value = "/update")
    public User updateUser(@RequestBody User user) {
        try {
            return service.updateUser(user);
        }
        catch (Exception e) {
            throw new RequestException("Cannot update user");
        }
    }
}