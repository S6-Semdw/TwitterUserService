package com.example.twitteruserservice.service;

import com.example.twitteruserservice.exception.RequestException;
import com.example.twitteruserservice.model.User;
import com.example.twitteruserservice.repo.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    //get users
    public List<User> getUsers() {
        try {
            return userRepo.findAll();
        }
        catch (Exception e) {
            throw new RequestException("Cannot get all tweets");
        }
    }

    //get user by id
    public User getUserById (int id) {
        try {
            return userRepo.findById(id).orElse(null);
        }
        catch (Exception e) {
            throw new RequestException("Cannot get tweet by id");
        }
    }

    //post user
    public User saveUser(User user) {
        try {
            return userRepo.save(user);
        }
        catch (Exception e) {
            throw new RequestException("Tweet cannot be created");
        }
    }

    //delete user
    public String deleteUser(int id) {
        try {
            userRepo.deleteById(id);
            return "User deleted" + id;
        }
        catch (Exception e) {
            throw new RequestException("Cannot delete user");
        }
    }

    //update user
    public User updateTweet(User user) {
        try {
            User existingUser=userRepo.findById(user.getId()).orElse(null);
            existingUser.setName(user.getName());
            return userRepo.save(existingUser);
        }
        catch (Exception e) {
            throw new RequestException("Cannot edit user");
        }
    }
}
