package com.raj.springsecurity.controllers;

import com.raj.springsecurity.model.MyUser;
import com.raj.springsecurity.repositories.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @Autowired
    private MyUserRepository myUserRepository;

    @PostMapping("/register/user")
    public MyUser createUser(@RequestBody MyUser user){
        return myUserRepository.save(user);
    }
}
