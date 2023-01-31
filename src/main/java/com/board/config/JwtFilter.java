package com.board.config;

import com.board.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//로그인관련 로직작성
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends UsernamePasswordAuthenticationFilter { // 성공/ 실패 메소드 써보기
    // 인증된 사용자의 토큰 확인 필터
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override //인증 시도?
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getParameter("id"),request.getParameter("password"));

        //AuthenticationManager는 인증에 성공하면 Authentication 인스턴스를 리턴
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        return authentication;
    }

    /*@Override // 인증 성공시 토큰 발급
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
                                                                                                                throws IOException, ServletException {
        //인증에 성공할 시 인증된 Account의 정보를 통해 JWT Token을 만들고 헤더(Authentication 헤더)에 포함
        User user = (User) authResult.getPrincipal();
        String jwt = jwtUtil.createAuthJwtToken(user.getUsername());
        System.out.println("jwt : " + jwt);
        response.addCookie(new Cookie("jwt", jwt));
    }*/







    //@Override // 그냥 서블릿필터쓰는거임
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // token 꺼내기
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authorization : {}", authorization);

        // token 소유여부
        if(authorization == null || !authorization.startsWith("Bearer ")) {
            log.error("authorization 을 잘못 보냈습니다.");
            filterChain.doFilter(request,response);
            return;
        }

        // token 꺼내기
        String token = authorization.split(" ")[1];

        // token 만료여부 확인
        if(JwtUtil.isExpired(token)) {
            log.error("token이 만료되었음");
            filterChain.doFilter(request, response);
            /*
                refresh token 발행
            */
            return;
        }



    }


}
