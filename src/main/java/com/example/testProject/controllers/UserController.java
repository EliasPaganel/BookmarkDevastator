package com.example.testProject.controllers;

import com.example.testProject.domain.Role;
import com.example.testProject.domain.User;
import com.example.testProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String editUserForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String updateUser(@RequestParam("userId") User user,
                             @RequestParam Map<String, String> form,
                             @RequestParam String username) {
        userService.updateUser(user, form, username);
        return "redirect:/user";
    }

    @GetMapping("profile")
    public String getUserProfile(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "profile";
    }

    @PostMapping("profile")
    public String updateUserProfile(@AuthenticationPrincipal User user,
                                    @RequestParam String email,
                                    @RequestParam(required = false) String oldPassword,
                                    @RequestParam(required = false) String newPassword) {

        userService.updateProfile(user, email, oldPassword, newPassword);
        return "redirect:/user/profile";
    }
}
