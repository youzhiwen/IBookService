package com.ibook.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.ibook.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.ibook.entity.User;
import com.ibook.repository.UserRepository;

import java.util.Date;

/**
 * @author Stephen You
 */
@Controller
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@ModelAttribute(value = "user") User user,
                        HttpSession session) {

        User loginUser = userRepository.findByUserNameOrEmail(user.getUserName(), user.getEmail());
        if (loginUser != null && loginUser.getPassword().equals(MD5Util.encrypt(user.getPassword()))) {
            session.setAttribute("loginUser", loginUser);
        } else {
            session.setAttribute("errorMsg", "Authentication failed. You entered an incorrect username or password.");
            return "login";
        }
        return "dashboard";
    }

    @GetMapping("/register")
    public String register(User user) {
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid User user, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            return "register";
        }
        User registerUser = userRepository.findByUserNameOrEmail(user.getUserName(), user.getEmail());
        if (registerUser != null) {
            model.addAttribute("errorMessage", "User exist");
            return "register";
        }
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setPassword(MD5Util.encrypt(user.getPassword()));
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        session.setAttribute("loginUser", user);
        return "dashboard";
    }

    @GetMapping("/")
    public String showAllUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "login";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {

        return "update-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {

            return "update-user";
        }

        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        return "index";
    }
}
