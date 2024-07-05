package com.raj.springsecurity.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContentController {

    @GetMapping("/home")
    public String handleHome(){
        return "home";
    }

    @GetMapping("/admin/home")
    public String handleAdminHome(){
        return "Admin_Home";
    }

    @GetMapping("/user/home")
    public String handleUserHome(){
        return "User_Home";
    }

   /* @GetMapping("/login")
    public String handleLogin(){
        return "custom_login";
    }*/
}
