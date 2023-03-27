package com.example.twitteruserservice.controller;


import com.example.twitteruserservice.exception.RequestException;
import com.example.twitteruserservice.model.User;
import com.example.twitteruserservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/")
@CrossOrigin(origins="http://localhost:3000")
@RestController
public class UserController {

    @Autowired
    private UserService service;

    //Get tweets
    @GetMapping(value = "/users")
    public List<User> findAllUsers() {
        try
        {
            return service.getUsers();
        }
        catch (Exception e)
        {
            throw new RequestException("Cannot get all users");
        }
    }

    @PostMapping(value ="/adduser")
    public User addTweet(@RequestBody User user) {
        try {
            return service.saveUser(user);
        }
        catch (Exception e)
        {
            throw new RequestException("Not able to add user");
        }
    }

    //Get tweet
    @GetMapping(value = "/user/{id}")
    public User findUserById(@PathVariable int id) {
        try {
            return service.getUserById(id);
        }
        catch (Exception e) {
            throw new RequestException("Cannot get user by id");
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        try {
            return service.deleteUser(id);
        }
        catch (Exception e) {
            throw new RequestException("Cannot delete user");
        }
    }
}
