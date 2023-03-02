package com.board.config.security;

import com.board.domain.User;
import com.board.service.impl.RedisService;
import com.board.util.JwtProperties;
import com.board.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.time.Duration;
import java.util.Date;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;


// 로그인 시도 -> 인증된 사용자로 등록하기 -> 인증된 사용자면 JWT 발행하기
@RequiredArgsConstructor
@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    //JwtAuthenticationFilter

    private final CustomAuthProvider customAuthProvider; //스프링 시큐리티 인증 수행방식 정의 API
    private final RedisService redisService;
    private final JwtUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 사용자의 로그인 정보와 함께 인증요청을 하면 해당 필터로 진입함
        log.info("========================[ AuthenticationFilter.attemptAuthentication ]========================");

        // SecurityContextHolder 에 저장할 Authentication 객체(보통 UsernamePasswordAuthenticationToken 으로 생성)만든다
        ObjectMapper objectMapper = new ObjectMapper();
        User user;

        try {
            user = objectMapper.readValue(request.getInputStream(), User.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getId(), user.getPassword());
        System.out.println("authenticationToken 객체 : " + authenticationToken.toString());

        // 스프링 시큐리티 filter 사용시 AuthenticationManager 를 이용해 SecurityContextHolder 에 저장함
        // AuthenticationManager의 구현체인 ProviderManager 에게 생성한 UsernamePasswordToken 객체를 전달해서 인증한다(SecurityContextHolder 에 저장)
        Authentication authenticate = customAuthProvider.authenticate(authenticationToken);
        System.out.println("authenticate  : " + authenticate.toString());

        return authenticate;
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authenticate)
            throws IOException, ServletException {
        // 인증에 성공할 시 인증된 Account의 정보를 통해 JWT Token을 만들고 cookie 에 저장
        // 헤더(Authentication 헤더)에 포함
        log.info("========================[ AuthenticationFilter.successfulAuthentication 실행]========================");
        log.info("========================[>> 토큰 발행 ]========================");

            log.info("authenticate : {}", authenticate.toString());

            User user = (User) authenticate.getPrincipal();
            String id = user.getId();
            log.info("토큰 발행할 user 정보 : {}", id);

            String accessToken = jwtUtil.createAccessToken(id);
            String refreshToken = jwtUtil.createRefreshToken();

            //db에 refresh token 저장
            redisService.setAccessValues(accessToken, id);
            log.info("accessToken [Redis] 저장 완료 > {}", accessToken);
            redisService.setRefreshValues(refreshToken, id);
            log.info("refreshToken [Redis] 저장 완료 > {}", refreshToken);


            //TODO AccessToken header에 저장
            response.addHeader("Authorization", accessToken);
            log.info("accessToken Header 에 저장");
            //Cookie accessCookie = new Cookie("accessToken", accessToken);
            //accessCookie.setMaxAge(JwtProperties.ACCESS_COOKIE_EXPIRATION_TIME);
            //response.addCookie(accessCookie);

            // refreshToken 쿠키에 저장
            Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
            refreshCookie.setMaxAge(JwtProperties.REFRESH_COOKIE_EXPIRATION_TIME);
            response.addCookie(refreshCookie);
            log.info("refreshToken 쿠키에 저장");


        }




}
