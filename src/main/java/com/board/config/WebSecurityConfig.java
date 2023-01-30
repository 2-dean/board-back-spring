package com.board.config;

import com.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터 체인에 등록
@RequiredArgsConstructor
public class WebSecurityConfig {//SpringSecurity 환경설정 구성 클래스

    private final UserService userService;

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                    .httpBasic().disable()
                    .csrf().disable()   //cross site 기능
                    .cors().and()       //cross site -> 도메인이 다를때 허용해줌?
                    .authorizeRequests()//요청을 인증하겠당
                    .antMatchers("/").permitAll()
                    .antMatchers("/users/**").permitAll()// 언제나 접근가능
                    .antMatchers("/boards/**").authenticated() // boards/** 요청은 인증필요
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //스프링시큐리티가 세션을 생성하지도않고 기존것을 사용하지도 않음 (JWT쓸때)
                .and()
                    // UsernamePasswordAuthenticationFilter 보다 먼저 실행할 jwtFilter 추가
                    .addFilterBefore(new JwtFilter(userService, secretKey), UsernamePasswordAuthenticationFilter.class)
                    .build();
    }

}