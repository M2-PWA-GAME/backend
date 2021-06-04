package com.ynov.master.mobile.game.medieval.warfare.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
public class DefaultController {

    @GetMapping("/")
    public ResponseEntity<Void> greeting() {
        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT).header("Location","/swagger-ui.html").build();
    }

}
