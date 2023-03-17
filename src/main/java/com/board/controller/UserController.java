package com.board.controller;

import com.board.domain.User;
import com.board.dto.BoardDTO;
import com.board.service.UserService;
import com.board.dto.UserDTO;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private String resultMessage;


    // 로그인 사용자 정보 보내주기
    @PostMapping("/{userId}")
    public ResponseEntity<UserDTO> userInfo(@PathVariable String userId) {
        log.info("userInfo 요청 userId : {}", userId);

        User findUser = userService.findUser(userId);
        log.info("findUser : {}", findUser.toString());

        UserDTO userDTO = new UserDTO();
        userDTO.setIdx(findUser.getIdx());
        userDTO.setId(findUser.getId());
        userDTO.setName(findUser.getName());
        userDTO.setRole(findUser.getRole());

        log.info("보내줄 USER 정보 : {}", userDTO );
        return ResponseEntity.ok(userDTO);
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

    //@PostMapping("/logout")
    public ResponseEntity<String> logout (HttpServletResponse response, HttpServletRequest request) {
        log.info(">> 로그아웃 요청 들어옴");
        return userService.logout(response, request);
    }

}
