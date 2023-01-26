package com.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig { //SpringSecurity 환경설정 구성 클래스


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


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //HTTP 인증, 인가 담당, 필터를 통해 인증방식과 인증절차에 대해 등록하며 설정을 담당

        //
        http.httpBasic().disable();

        // 서버에 인증정보를 저장하지 않기때문에 csrf 사용하지 않음
        http.csrf().disable();

        // form 기반의 로그인에 대해 비활성화
        //http.formLogin().disable();

        // session
        http.sessionManagement();

        http.authorizeRequests()
            .antMatchers("/api/v1/users/join", "/api/v1/users/login").permitAll()
            .antMatchers(HttpMethod.POST, "/api/vi/**").authenticated();

        return http.build();
      /*  // /, /home 페이지는 모두 허용(인증 필요없음), 다른 페이지는 인증된 사용자만 허용
        http
                .authorizeRequests()
                .antMatchers("/", "/home")
                .permitAll()
                .anyRequest().authenticated();

        // login 설정
        http
                .formLogin((form) -> form
                        .loginPage("/login")            // GET요청
                        .permitAll()
                        .loginProcessingUrl("/auth")    // POST요청
                        .usernameParameter("id")     // login에 필요한 id값 설정 default는 username
                        .passwordParameter("password")  // login에 필요한 password 값 설정 default가 password
                        .defaultSuccessUrl("/hello")    // login 성공하면 /로 redirect
                );

        // logout 설정
        http
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")); //로그아웃 성공하면 / 로 redirect

        return http.build();*/
    }


}