package com.example.testProject.services;

import com.example.testProject.domain.Role;
import com.example.testProject.domain.User;
import com.example.testProject.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MailSender mailSender;

    @Value("${current.url}")
    private String hostUrl;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    public Boolean addUser(User user) {
        User userFromDB = userRepo.findByUsername(user.getUsername());
        if (Objects.nonNull(userFromDB)) {
            return false;
        }
        user.setActive(true);
        user.setRoles(Set.of(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        userRepo.save(user);

        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format("Hello, %s\n" +
                    "Welcome to 'Bookmark devastator'." +
                    "Please, visit next link, that confirmation your registration: %sactivate/%s",
                    user.getUsername(),
                    hostUrl,
                    user.getActivationCode());
            mailSender.send(user.getEmail(), "Activation code", message);
        }
        return true;
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
}
