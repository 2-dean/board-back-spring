package com.board.service;

import com.board.domain.User;

import java.util.Optional;

public interface UserService {
    //회원가입
    String join(User user);
    //로그인
    String login(User user);

}
