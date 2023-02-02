package com.board.service.impl;

import com.board.domain.Role;
import com.board.domain.User;
import com.board.mapper.UserMapper;
import com.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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



    public void logout (String id) {
        //토큰 지우기
    }

}
