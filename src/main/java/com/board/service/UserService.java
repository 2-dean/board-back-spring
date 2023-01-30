package com.board.service;

import com.board.domain.User;

import java.util.Map;

public interface UserService {
    //회원가입
    String join(User user);
    //로그인
    Map<String, String> login(User user);
    //회원정보
    User findUser(String id);


}
