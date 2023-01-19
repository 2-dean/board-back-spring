package com.board.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/vi/users")
public class UserController {


    @PostMapping("/login")
    public ResponseEntity<String> login () {
        return ResponseEntity.ok().body("token");
    }

}
