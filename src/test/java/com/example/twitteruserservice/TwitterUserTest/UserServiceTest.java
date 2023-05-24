package com.example.twitteruserservice.TwitterUserTest;

import com.example.twitteruserservice.model.User;
import com.example.twitteruserservice.repository.UserRepository;
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
    private UserRepository repo;

    @Autowired
    private UserService underTest;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void saveProduct() {

        User prod = new User();
        //when
        underTest.saveUser(prod);
        //then
        verify(repo).save(prod);
    }

    @Test
    public void getProducts() {
        //when
        underTest.getUsers();
        //then
        verify(repo).findAll();
    }

    @Test
    public void getProductById() {
        //when
        underTest.getUserById(1);
        //then
        verify(repo).findById(1);
    }

    @Test
    public void deleteProduct() {
        //when
        underTest.deleteUser(1);
        //then
        verify(repo).deleteById(1);
    }

}
