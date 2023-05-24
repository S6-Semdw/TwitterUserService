package com.example.twitteruserservice.TwitterUserTest;

import com.example.twitteruserservice.controller.UserController;
import com.example.twitteruserservice.model.User;
import com.example.twitteruserservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.put;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class IntegrationTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;


    @Test
    public void getAllUsers() throws Exception {
        List<User> users = new ArrayList<>();
        when(userService.getUsers()).thenReturn(users);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void deleteUser() throws Exception {
        mockMvc.perform(delete("/api/delete/{id}", 1))
                .andExpect(status().isOk())
                .andDo(print());

        verify(userService).deleteUser(1);
    }

    @Test
    public void getUserById() throws Exception {
        User user = new User();
        user.setId(1);
        user.setEmail("john@example.com");
        user.setPassword("ww");

        when(userService.getUserById(1)).thenReturn(user);

        mockMvc.perform(get("/api/user/{id}", 1))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
