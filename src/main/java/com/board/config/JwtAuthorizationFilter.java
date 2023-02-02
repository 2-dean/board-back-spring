package com.board.config;

import com.board.domain.User;
import com.board.mapper.UserMapper;
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
    // 인증된 사용자의 토큰 확인 필터
    /*
    JWT로 인가를 하기 위한 클래스
    헤더를 통한 인증 시 적용되는 BasicAuthenticationFilter를 상속받는다.
    BasicAuthenticationFilter는 AuthenticationManager를 사용하기 때문에 super를 사용해서 주입해준다.
    * */

    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserMapper userMapper, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.userMapper = userMapper;
        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // token 꺼내기
        Cookie[] cookies = request.getCookies();
        String jwt = "";

        if(cookies != null) {
            for (Cookie cookie : cookies){
                if(cookie.getName().equals("jwt")) {
                    jwt = cookie.getValue();
                }
            }
        }

        log.info("jwt : {}", jwt);

        // token 소유여부
        if(jwt == null) {
            log.error("jwt 가 없습니다.");
            filterChain.doFilter(request,response);
            return;
        }


        // token 만료여부 확인
        if(JwtUtil.isExpired(jwt)) {
            log.error("token이 만료되었음");
            filterChain.doFilter(request, response);
            /*
                refresh token 발행
            */
            return;
        }

        // token 에서 사용자 정보 가져오기
        String id = jwtUtil.getUserName(jwt);
        log.info("사용자 아이디 가져오기 : {}" , id);

        if(id != null) {
            User user = userMapper.findUser(id).orElseThrow(() -> new UsernameNotFoundException("해당 id의 User 없음"));
            Authentication authentication = new UsernamePasswordAuthenticationToken(user, null,
                    user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);

    }


}
