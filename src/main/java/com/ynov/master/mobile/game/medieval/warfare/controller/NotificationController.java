package com.ynov.master.mobile.game.medieval.warfare.controller;

import com.google.firebase.messaging.FirebaseMessaging;
import com.ynov.master.mobile.game.medieval.warfare.model.User;
import com.ynov.master.mobile.game.medieval.warfare.service.NotificationService;
import com.ynov.master.mobile.game.medieval.warfare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    FirebaseMessaging fm;

    @Autowired
    UserService userService;

    @Autowired
    NotificationService notificationHandler;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> register(HttpServletRequest req, @RequestBody String token) {
        User usr = userService.whoami(req);
        notificationHandler.susbcribe(usr.getId().toString(),token);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
