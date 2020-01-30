package com.example.testProject.services;

import com.example.testProject.domain.Role;
import com.example.testProject.domain.User;
import com.example.testProject.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Value("${current.url}")
    private String hostUrl;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if(Objects.isNull(user)) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public Boolean addUser(User user) {
        User userFromDB = userRepo.findByUsername(user.getUsername());
        if (Objects.nonNull(userFromDB)) {
            return false;
        }
        user.setActive(true);
        user.setRoles(Set.of(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);

        formattingAndSendingMessage(user);
        return true;
    }

    private void formattingAndSendingMessage(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format("Hello, %s\n" +
                            "Welcome to 'Bookmark devastator'." +
                            "Please, visit next link: %sactivate/%s",
                    user.getUsername(),
                    hostUrl,
                    user.getActivationCode());
            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }

    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);
        if (Objects.isNull(user)) {
            return false;
        }

        user.setActivationCode(null);
        userRepo.save(user);
        return true;
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public void updateUser(User user, Map<String, String> form, String username) {
        user.setUsername(username);

        Set<String> allRoles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (allRoles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepo.save(user);
    }

    public void updateProfile(User user, String email, String oldPassword, String newPassword) {
        if (!StringUtils.isEmpty(email) && !Objects.equals(email, user.getEmail())) {
            user.setEmail(email);
            user.setActivationCode(UUID.randomUUID().toString());
            formattingAndSendingMessage(user);
        }

        if (!StringUtils.isEmpty(oldPassword) && !StringUtils.isEmpty(newPassword)
                && passwordEncoder.matches(oldPassword, user.getPassword())
                && !Objects.equals(oldPassword, newPassword)) {
            user.setPassword(passwordEncoder.encode(newPassword));
        }

        userRepo.save(user);
    }
}
