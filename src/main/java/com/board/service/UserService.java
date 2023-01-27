package com.board.service;

import com.board.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    //회원가입
    String join(User user);
    //로그인
    String login(User user);

}
