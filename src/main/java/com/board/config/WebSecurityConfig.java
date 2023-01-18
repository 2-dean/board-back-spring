package com.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 권한에 따라 허용하는 url 설정

        // /, /home 페이지는 모두 허용(인증 필요없음), 다른 페이지는 인증된 사용자만 허용
        http
                .authorizeRequests()
                .antMatchers("/", "/home")
                .permitAll()
                .anyRequest().authenticated();

        // login 설정
        http
                .formLogin((form) -> form
                        .loginPage("/login")            // GET요청
                        //.permitAll()
                        .loginProcessingUrl("/auth")    // POST요청
                        .usernameParameter("username")     // login에 필요한 id값 설정 default는 username
                        .passwordParameter("password")  // login에 필요한 password 값 설정 default가 password
                        .defaultSuccessUrl("/hello")    // login 성공하면 /로 redirect
                );

        // logout 설정
        http
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")); //로그아웃 성공하면 / 로 redirect

        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService() { //user만들기?
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER") //user, admin 세팅
                        .build();

        return new InMemoryUserDetailsManager(user);  //메모리에 사용자 저장소 -> 나중에 db로 변경
    }
}