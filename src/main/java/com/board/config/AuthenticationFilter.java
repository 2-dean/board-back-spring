package com.board.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 사용자의 로그인 정보와 함께 인증요청을 하면 AuthenticationFilter가 요청을 가로채
        // UsernamePasswordAuthenticationToken 인증용 객체를 생성한다.

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getParameter("id"),request.getParameter("password"));

        setDetails(request,authenticationToken);

        // 검증을 위해 AuthenticationManager의 인스턴스로 전달
        //AuthenticationManager의 구현체인 ProviderManager에게 생성한 UsernamePasswordToken 객체를 전달한다.
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }

    /* 인증된 사용자 객체 만들기
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userId, null, Collections.singleton(new SimpleGrantedAuthority(role)));

        // 사용자 객체에 토큰 값 입력
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken); // 리퀘스트에 인증 됐다고 사인ㅋ
        filterChain.doFilter(request, response);

        // 토큰발급

        */
}
