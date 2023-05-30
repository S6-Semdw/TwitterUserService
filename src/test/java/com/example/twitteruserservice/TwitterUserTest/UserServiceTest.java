package com.example.twitteruserservice.TwitterUserTest;

import com.example.twitteruserservice.model.User;
import com.example.twitteruserservice.repository.TwitterUserRepository;
import com.example.twitteruserservice.service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.verify;

@DataJpaTest
public class UserServiceTest {

    @Mock
    private TwitterUserRepository repo;

    @Autowired
    private UserService underTest;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getProducts() {
        //when
        underTest.getUsers();
        //then
        verify(repo).findAll();
    }

}
