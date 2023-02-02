package com.board.config;

import com.board.domain.User;
import com.board.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


// 로그인 시도 -> 인증된 사용자로 등록하기 -> 인증된 사용자면 JWT 발행하기
@RequiredArgsConstructor
@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    //JwtAuthenticationFilter

    private final AuthenticationManager authenticationManager; //스프링 시큐리티 인증 수행방식 정의 API
    private final JwtUtil jwtUtil;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 사용자의 로그인 정보와 함께 인증요청을 하면 해당 필터로 진입함
        System.out.println(">> AuthenticationFilter.attemptAuthentication 실행");
        // SecurityContextHolder 에 저장할 Authentication 객체(보통 UsernamePasswordAuthenticationToken 으로 생성)만든다
        ObjectMapper objectMapper = new ObjectMapper();
        User user;

        try {
            user = objectMapper.readValue(request.getInputStream(), User.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        System.out.println("request > id :  " + user.getId() + ", pwd : " + user.getPassword());

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getId(), user.getPassword());
        System.out.println("authenticationToken 객체 : " + authenticationToken.toString());

        // 스프링 시큐리티 filter 사용시 AuthenticationManager를 이용해 SecurityContextHolder에 저장함
        // AuthenticationManager의 구현체인 ProviderManager에게 생성한 UsernamePasswordToken 객체를 전달해서 인증한다(SecurityContextHolder 에 저장)
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        System.out.println("authenticate  : " + authenticate.toString());

        return authenticate;
    }

    @Override // 인증 성공시 토큰 발급
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        //인증에 성공할 시 인증된 Account의 정보를 통해 JWT Token을 만들고 헤더(Authentication 헤더)에 포함
        System.out.println(">> AuthenticationFilter.successfulAuthentication 실행");

        User user = (User) authResult.getPrincipal();

        String jwt = jwtUtil.createAuthJwtToken(user.getId());
        System.out.println("jwt 생성 : " + jwt);

        response.addCookie(new Cookie("jwt", jwt));
    }

}
