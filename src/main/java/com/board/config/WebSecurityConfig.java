package com.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터 체인에 등록
public class WebSecurityConfig {
    //SpringSecurity 환경설정 구성 클래스

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                    .csrf().disable()   //cross site 기능
                    .cors().and()       //cross site -> 도메인이 다를때 허용해줌?
                    .httpBasic(withDefaults())
                    .authorizeRequests()//요청을 인증하겠당
                    .antMatchers("/").permitAll()
                    .antMatchers("/users/**").permitAll()// 언제나 접근가능
                    .antMatchers("/boards/**").access("hasRole('ROLE_USER')") // boards/** 요청은 인증필요
                .and()
                    .formLogin()// form 로그인 요청하면 loadUserByUsername 메소드 실행됨
                    //.loginPage("/login")
                    //.loginProcessingUrl("/users/login")
                    .defaultSuccessUrl("/")
                .and()
                    .logout()
                    //.logoutSuccessUrl("/")
                    .deleteCookies("JSESSIONID")
                .and()
                    .build();
    }

}