package com.example.twitteruserservice.repo;

import com.example.twitteruserservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
}
