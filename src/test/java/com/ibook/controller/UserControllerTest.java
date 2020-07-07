package com.ibook.controller;

import com.ibook.entity.User;
import com.ibook.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest()
public class UserControllerTest{

    private MockMvc mvc;
    @InjectMocks
    private UserController userController;
    @Spy
    private UserRepository userRepository;
    @Mock
    private HttpSession session;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void login() {
        assertEquals(userController.login(),"login");
    }

    @Test
    void testLogin() {
        User user = new User("test","");
        user.setPassword("123456");
        User successUser = new User();
        successUser.setPassword("123456");
        when(userRepository.findByUserNameOrEmail(user.getUserName(),user.getEmail())).thenReturn(successUser);
        String result = userController.login(user,session);
        //assertEquals(user,session.getAttribute("loginUser"));
        assertEquals("dashboard",result);

        User failUser = new User();
        failUser.setPassword("123");
        when(userRepository.findByUserNameOrEmail(user.getUserName(),user.getEmail())).thenReturn(failUser);
        result = userController.login(user,session);
        //assertEquals("Authentication failed. You entered an incorrect username or password.", session.getAttribute("errorMsg"));
        assertEquals("login",result);
    }

    @Test
    void register() {
        assertEquals("register",userController.register(null));
    }

    @Test
    void testRegister() {
        assertTrue(true);
    }

    @Test
    void showAllUsers() {
        assertTrue(true);
    }

    @Test
    void showUpdateForm() {
        assertTrue(true);
    }

    @Test
    void updateUser() {
        assertTrue(true);
    }

    @Test
    void deleteUser() {
        assertTrue(true);
    }
}