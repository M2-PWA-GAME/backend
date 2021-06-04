package com.ynov.master.mobile.game.medieval.warfare.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
public class DefaultController {

    @GetMapping("/")
    public String greeting() {
        return "redirect:/swagger-ui.html";
    }

}
