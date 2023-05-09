package com.example.twitteruserservice.repository;

import com.example.twitteruserservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {


}