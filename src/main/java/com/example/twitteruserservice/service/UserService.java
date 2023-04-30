package com.example.twitteruserservice.service;

import com.example.twitteruserservice.exception.RequestException;
import com.example.twitteruserservice.model.User;
import com.example.twitteruserservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
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

    //post user
    public User saveUser(User user) {
        try {
            return userRepo.save(user);
        } catch (Exception e) {
            throw new RequestException("Cannot create user");
        }
    }

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