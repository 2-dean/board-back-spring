package com.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {
    @GetMapping("/login")
    public String login (){
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login (@RequestBody String username, @RequestBody String password){
        if (username.equals("username") && password.equals("password")) {
        }
        return "redirect:/hello";
    }

    //http://localhost:8080/login
}
