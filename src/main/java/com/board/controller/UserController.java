package com.board.controller;

import com.board.domain.User;
import com.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody String id, @RequestBody String password) {
        User user = User.builder()
                        .id(id)
                        .password(password)
                        .build();
        System.out.println("/join 요청들어옴 user : " + user);
        String resultMessage = userService.join(user);
        System.out.println("join : " + resultMessage);
        return ResponseEntity.ok().body(resultMessage);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody User user) {
        System.out.println("/Login 요청들어옴 user : " + user);
        userService.login(user);
        return ResponseEntity.ok().body("token");
    }

}
