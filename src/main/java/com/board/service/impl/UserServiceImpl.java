package com.board.service.impl;

import com.board.domain.Role;
import com.board.domain.User;
import com.board.mapper.UserMapper;
import com.board.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    //UserDetailsService : spring security 에서 유저의 정보를 가져오는 인터페이스

    @Autowired
    private final UserMapper userMapper;
    @Autowired
    private final BCryptPasswordEncoder encoder;

    @Override
    public String join(User joinUser) {
        // id 중복체크
        if (userMapper.findUser(joinUser.getId()).isPresent()){
            return joinUser.getId() + " 는 이미 존재함";
        }
        System.out.println("joinUser : " + joinUser);

        // password 인코딩, "USER"관한 부여
        User user = User.builder()
                        .id(joinUser.getId())
                        .password(encoder.encode(joinUser.getPassword()))
                        .name(joinUser.getName())
                        .role(Role.ROLE_USER)
                        .build();
        userMapper.save(user);
        return "회원 가입 성공";

    }

    @Override
    public User findUser(String id) {
        return userMapper.findUser(id).get();
    }



    public ResponseEntity<String> logout (HttpServletResponse response, HttpServletRequest request) {
        log.info("UserServiceImpl.logout()실행");

        log.info("Header 에 accessToken null 입력");
        response.setHeader("Authorization", null);

        log.info("Cookie refreshToken 만료시키기");
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("refreshToken")) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
