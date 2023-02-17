package com.board.controller;

import com.board.domain.User;
import com.board.dto.UserDTO;
import com.board.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private String resultMessage;


    //TODO 로그인 사용자 정보 보내주기
    @PostMapping
    public ResponseEntity<User> userInfo(@RequestBody User user) {
        log.info("user: {}", user.toString());
        log.info("userInfo 요청 userId : {}", user.getId());
        User findUser = userService.findUser(user.getId());
        log.info("findUser : {}", findUser.toString());

        return ResponseEntity.ok(findUser);
    }


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


/*   @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody User user, HttpServletRequest request) {
        System.out.println("/Login 요청들어옴 user : " + user);

        userDetailsService.loadUserByUsername(user.getId());

        return ResponseEntity.ok().body("로그인 요청함 ");
    }*/

    @RequestMapping("/logout")
    public void logout () {
        System.out.println(">> 로그아웃 요청");
        // accessToken 삭제?
    }

}
