package com.board.security;

import com.board.domain.User;
import com.board.mapper.UserMapper;
import com.board.service.impl.RedisService;
import com.board.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    /*
        JWT로 인가를 하기 위한 클래스
        헤더를 통한 인증 시 적용되는 BasicAuthenticationFilter를 상속받는다.
        BasicAuthenticationFilter는 AuthenticationManager를 사용하기 때문에 super를 사용해서 주입해준다.
    */

    private final UserMapper userMapper;
    private final RedisService RedisService;
    private RedisService redisService;
    private final JwtUtil jwtUtil;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserMapper userMapper, RedisService RedisService, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.userMapper = userMapper;
        this.RedisService = RedisService;
        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("JWT 검증 로직 시작 ========================");

        // token 꺼내기
        Cookie[] cookies = request.getCookies();
        String refreshToken = "";

        if(cookies != null) {
            for (Cookie cookie : cookies){
                if(cookie.getName().equals("refreshToken")) {
                    refreshToken = cookie.getValue();
                }
            }
        }

        log.info("[Cookie 에 저장 된 refreshToken] {}", refreshToken);

        // token 소유여부
        if(refreshToken == null) {
            log.error("refreshToken 없습니다.");
            filterChain.doFilter(request,response);
            return;
        }

        log.info("refreshToken 소유함");

        // token 만료여부 확인
        if(jwtUtil.isExpired(refreshToken)) {
            log.error("refreshToken 이 만료되었음");
            filterChain.doFilter(request, response);
            return;
        }

        log.info("JWT 만료되지 않음");

        // 유효한 token 에서 사용자 정보 가져오기
        String id = redisService.getValues(refreshToken);

        //String id = jwtUtil.getUserName(refreshToken);
        log.info("token 에서 가져온 사용자 ID : {}" , id);

        // Authentication 객체 생성 및 SecurityContextHolder에 등록
        if(id != null) {
            User user = userMapper.findUser(id).orElseThrow(() -> new UsernameNotFoundException("해당 id의 User 없음"));
            Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);

    }


}
