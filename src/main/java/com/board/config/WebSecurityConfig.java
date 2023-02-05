package com.board.config;

import com.board.mapper.UserMapper;
import com.board.config.security.AuthenticationFilter;
import com.board.config.security.CustomAuthProvider;
import com.board.config.security.JwtAuthorizationFilter;
import com.board.service.impl.RedisService;
import com.board.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터 체인에 등록
@RequiredArgsConstructor
public class WebSecurityConfig {
//SpringSecurity 환경설정 구성 클래스

    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;
    private final RedisService redisService;
    private final BCryptPasswordEncoder encoder;
    private final UserDetailsService userDetailsService;

    @Bean
    AuthenticationConfiguration authenticationConfiguration () {
        return new AuthenticationConfiguration();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new CustomAuthProvider(userDetailsService, encoder);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        return http
                .csrf().disable()       //cross site 기능

                .formLogin().disable()  // formLogin 대신 Jwt를 사용
                .httpBasic().disable()  // httpBasic 방식 대신 Jwt를 사용
                .cors()                 //cross site -> 도메인이 다를때 허용해줌?

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 스프링시큐리티가 세션을 생성하지도않고 기존것을 사용하지도 않음 (JWT쓸때)

                .and()
                .authorizeRequests()    //요청에 따른 인가 설정
                .antMatchers("/").permitAll()
                .antMatchers("/users/**").permitAll()       // 언제나 접근가능
                .antMatchers("/boards/**").authenticated()  // boards/** 요청은 인증필요

                .and()
                .addFilter(new AuthenticationFilter((CustomAuthProvider) authenticationProvider(), redisService, jwtUtil))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(authenticationConfiguration()), userMapper, redisService, jwtUtil))

                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .deleteCookies("accessToken", "refreshToken")
                .and()
                .build();
    }



}