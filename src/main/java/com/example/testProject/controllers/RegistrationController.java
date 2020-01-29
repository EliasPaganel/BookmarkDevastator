package com.example.testProject.controllers;

import com.example.testProject.domain.Role;
import com.example.testProject.domain.User;
import com.example.testProject.repos.UserRepo;
import com.example.testProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static java.lang.Boolean.TRUE;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
        if (!StringUtils.isEmpty(user.getUsername()) && !StringUtils.isEmpty(user.getPassword())) {

            if (!userService.addUser(user)) {
                model.put("message", "User already exists.");
                return "registration";
            }

            return "redirect:/login";
        }

        model.put("message", "Fill in all the fields.");
        return "registration";
    }

    @GetMapping("activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);
        if(TRUE.equals(isActivated)) {
            model.addAttribute("message", "User successfully activated.");
        } else {
            model.addAttribute("message", "Activation code is not found.");
        }
        return "login";
    }
}
