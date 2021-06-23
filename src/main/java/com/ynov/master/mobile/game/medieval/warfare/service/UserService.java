package com.ynov.master.mobile.game.medieval.warfare.service;

import com.ynov.master.mobile.game.medieval.warfare.exception.CustomException;
import com.ynov.master.mobile.game.medieval.warfare.model.Role;
import com.ynov.master.mobile.game.medieval.warfare.model.User;
import com.ynov.master.mobile.game.medieval.warfare.repository.UserRepository;
import com.ynov.master.mobile.game.medieval.warfare.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    NotificationService notificationHandler;

    public String signin(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            User user = userRepository.findByUsername(username);
            return jwtTokenProvider.createToken(user);
        } catch (AuthenticationException e) {
            log.error("Invalid username/password supplied");
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public String signup(User user) {

        if (!userRepository.existsByUsername(user.getUsername())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            if (user.getRoles() == null) {
                user.setRoles(List.of(Role.ROLE_CLIENT));
            }
            user.setGames(Collections.emptyList());
            userRepository.save(user);
            User createdUser = userRepository.findByUsername(user.getUsername());
            return jwtTokenProvider.createToken(createdUser);
        } else {
            log.error("Username is already in use");
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public boolean hasUserByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public void delete(String username) {
        userRepository.deleteByUsername(username);
    }

    public User search(String id) {
        User user = userRepository.findById(id);
        if (user == null) {
            log.error("The user doesn't exist");
            throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        return user;
    }

    public User whoami(HttpServletRequest req) {
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req));
        log.debug("Retrieve information about: " + username);
        return userRepository.findByUsername(username);
    }

    public String refresh(String username) {
        User user = userRepository.findByUsername(username);
        return jwtTokenProvider.createToken(user);
    }

}
