package com.board.service.impl;

import com.board.domain.Role;
import com.board.domain.User;
import com.board.mapper.UserMapper;
import com.board.service.UserService;
import com.board.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    //UserDetailsService : spring security 에서 유저의 정보를 가져오는 인터페이스

    @Autowired
    private final UserMapper userMapper;
    @Autowired
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret.key}")
    private String secretkey;
    private Long expireTime = 1000 * 60 * 60L; //1시간

 /*   @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        //세션방식이라 사용안하게될듯?
        System.out.println(">> loadUserByUsername() 실행");

        Optional<User> user = userMapper.findUser(id);

        if(user.isPresent()){
            System.out.println("user = " + user);
            JwtUtil.createJwt(id, secretkey, expireTime);
            return user.get();
        }
        return null;
    }
 */

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

    @Override
    public Map<String, String> login(User user) {
        //인증
        Map<String, String> result = new HashMap<>();

        // id 없음
        if (!userMapper.findUser(user.getId()).isPresent()){
            result.put("reulst", "등록되지 않은 사용자");
            return result;
        }
        User selectUser = userMapper.findUser(user.getId()).get();

        // password 틀림
        if (!encoder.matches(user.getPassword(), selectUser.getPassword())){
                    //입력받은 Password를 인코딩해서 저장소의 인코딩된 비밀번호와 비교
            result.put("reulst", "비밀번호 일치하지 않음");
            return result;
        }

        // 이상없으면 토큰 발행
        return JwtUtil.createJwt(user.getId(), secretkey, expireTime);
    }



    public void logout (String id) {
        //토큰 지우기
    }

}
