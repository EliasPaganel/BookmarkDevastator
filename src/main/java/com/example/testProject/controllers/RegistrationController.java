package com.example.testProject.controllers;

import com.example.testProject.domain.Role;
import com.example.testProject.domain.User;
import com.example.testProject.repos.UserRepo;
import com.example.testProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public String addUser(@Valid User user, BindingResult bindingResult, Model model) {
        if(!Objects.equals(user.getPassword(), user.getPassword2())) {
            model.addAttribute("passwordError", "Passwords are different");
        }
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
        }
        for (String key : model.asMap().keySet()) {
            if (key.contains("Error")) {
                return "registration";
            }
        }

        if (!StringUtils.isEmpty(user.getUsername()) && !StringUtils.isEmpty(user.getPassword())) {

            if (!userService.addUser(user)) {
                model.addAttribute("usernameError", "User already exists.");
                return "registration";
            }

            return "redirect:/login";
        }

        model.addAttribute("message", "Fill in all the fields.");
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
