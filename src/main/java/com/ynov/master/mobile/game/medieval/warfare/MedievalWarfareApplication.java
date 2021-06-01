package com.ynov.master.mobile.game.medieval.warfare;

import com.ynov.master.mobile.game.medieval.warfare.model.Role;
import com.ynov.master.mobile.game.medieval.warfare.model.User;
import com.ynov.master.mobile.game.medieval.warfare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Collections;

@SpringBootApplication
public class MedievalWarfareApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MedievalWarfareApplication.class, args);
    }

    @Autowired
    UserService userService;

    @Override
    public void run(String... params) throws Exception {
        if (!userService.hasUserByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setEmail("admin@email.com");
            admin.setRoles(new ArrayList<Role>(Collections.singletonList(Role.ROLE_ADMIN)));

            userService.signup(admin);
        }
    }

}
