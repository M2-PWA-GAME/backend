package com.ynov.master.mobile.game.medieval.warfare.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class HelloworldController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> helloWorld() {
        return ResponseEntity.status(HttpStatus.OK).body("Hello world");
    }

}
