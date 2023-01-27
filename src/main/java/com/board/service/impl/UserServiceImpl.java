package com.board.service.impl;

import com.board.domain.Role;
import com.board.domain.User;
import com.board.mapper.UserMapper;
import com.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

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
    public String login(User user) {
        //인증
        // id 없음
        if (!userMapper.findUser(user.getId()).isPresent()){
            return "등록되지 않은 사용자";
        }
        User selectUser = userMapper.findUser(user.getId()).get();

        // password 틀림
        if (!encoder.matches(user.getPassword(), selectUser.getPassword())){
                    //입력받은 Password를 인코딩해서 저장소의 인코딩된 비밀번호와 비교
            return "비밀번호 틀림";
        }

        // 이상없으면 로그인
        return "로그인 성공";
    }



}
