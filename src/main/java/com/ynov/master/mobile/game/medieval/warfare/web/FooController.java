package com.ynov.master.mobile.game.medieval.warfare.web;

import com.ynov.master.mobile.game.medieval.warfare.model.Foo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping(value = "/foos")
@Slf4j
public class FooController {

    @GetMapping(value = "/{id}")
    public Foo findOne(@PathVariable Long id) {
        return new Foo(id, randomAlphabetic(4));
    }

    @GetMapping
    public List<Foo> findAll() {
        List<Foo> fooList = new ArrayList<>();
        fooList.add(new Foo(Long.parseLong(randomNumeric(30)), randomAlphabetic(4)));
        fooList.add(new Foo(Long.parseLong(randomNumeric(45)), randomAlphabetic(5)));
        fooList.add(new Foo(Long.parseLong(randomNumeric(60)), randomAlphabetic(6)));
        return fooList;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@RequestBody Foo newFoo) {
        log.info("Foo created");
    }


    private String randomAlphabetic(Integer size)
    {
        byte[] array = new byte[size]; // length is bounded by 7
        new Random().nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
    }

    private String randomNumeric(Integer max)
    {
        return String.valueOf((int) ((Math.random() * (max))));
    }
}
