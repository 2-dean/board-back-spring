package com.board.controller;

import com.board.domain.User;
import com.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private String resultMessage;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody User user) {
        System.out.println("/join 요청들어옴 user : " + user);
        User newUser = User.builder()
                        .id(user.getId())
                        .password(user.getPassword())
                        .name(user.getName())
                        .build();
        resultMessage = userService.join(newUser);
        System.out.println("result : " + resultMessage);
        return ResponseEntity.ok().body(resultMessage);
    }

    @RequestMapping("/")
    public void successLogin () {
        System.out.println(">> 로그인 성공");
    }
    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody User user, HttpServletRequest request) {
        System.out.println("/Login 요청들어옴 user : " + user);
        HttpSession session = request.getSession();
        System.out.println("session : " + session);
        resultMessage = userService.login(user);
        return ResponseEntity.ok().body(resultMessage);
    }

}
