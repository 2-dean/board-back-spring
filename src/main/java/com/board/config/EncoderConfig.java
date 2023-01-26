package com.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class EncoderConfig {
    //비밀번호를 인코딩해서 저장
    //SecurityConfig, EncorderConfig는 꼭 다른 클래스로 저장! 순환참조 문제?가 생길 수 있음

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
