package com.board.service;

import com.board.domain.User;

public interface UserService {
    //회원정보
    User findUser(String id);

    //회원가입
    String join(User user);

    //ResponseEntity<String> login(User user);

}
