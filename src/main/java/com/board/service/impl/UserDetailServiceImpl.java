package com.board.service.impl;

import com.board.domain.User;
import com.board.exception.AppException;
import com.board.exception.ErrorCode;
import com.board.mapper.UserMapper;
import com.sun.deploy.security.UserDeclinedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    //UserDetailsService : spring security 에서 유저의 정보를 가져오는 인터페이스

    @Autowired
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        //Spring Security에서 AutenticationManager가 authenticate()를 통해서 인증을 할 때,
        //지정된 repository에서 인증 대상 객체를 찾아서 Principal 형태로 반환
        User user = userMapper.findUser(id)
                                .orElseThrow(() -> new UserDeclinedException("[ " + id + " ] 는 없는 아이디입니다."));
        return user;
    }



}
