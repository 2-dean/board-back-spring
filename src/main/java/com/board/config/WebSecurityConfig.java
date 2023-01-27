package com.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터 체인에 등록
public class WebSecurityConfig {
    //SpringSecurity 환경설정 구성 클래스

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                    .csrf().disable()   //cross site 기능
                    .cors().and()       //cross site -> 도메인이 다를때 허용해줌?
                    .authorizeRequests()//요청을 인증하겠당
                    .antMatchers("/users/**").permitAll()// 언제나 접근가능
                    //.antMatchers(HttpMethod.POST, "/boards/**").authenticated() // 모든 post요청과 나머지 요청은 인증필요
                    //antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                    .antMatchers("/boards/**").access("hasRole(ROLE_USER)") // boards/** 요청은 인증필요
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/users/login") // 이 url로 들어오면 userDetailServiceImpl 의 loadUserByUsername 메소드 실행됨
                    .and()
                    .build();
    }

}