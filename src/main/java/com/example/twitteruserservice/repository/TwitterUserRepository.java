package com.example.twitteruserservice.repository;

import com.example.twitteruserservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TwitterUserRepository extends MongoRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    void deleteByEmail(String email);
}