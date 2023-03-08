package com.board.service;

import com.board.domain.User;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {
    //회원정보
    User findUser(String id);

    //회원가입
    String join(User user);

    //logout
    ResponseEntity<String> logout(HttpServletResponse response, HttpServletRequest request);

    //ResponseEntity<String> login(User user);

}
