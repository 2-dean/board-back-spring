package com.board.config;

import com.board.domain.User;
import com.board.service.UserService;
import com.board.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // token 꺼내기
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authorization : {}", authorization);

        // token 없으면 block
        if(authorization == null || !authorization.startsWith("Bearer ")) {
            log.error("authorization 을 잘못 보냈습니다.");
            filterChain.doFilter(request,response);
            return;
        }

        // token 꺼내기
        String token = authorization.split(" ")[1];

        // token 만료여부 확인
        if(JwtUtil.isExpired(token, secretKey)) {
            log.error("token이 만료되었음");
            filterChain.doFilter(request, response);

            return;
        }

        // token 에서 id 꺼내기
        String userId = JwtUtil.getUserId(token, secretKey);
        log.info("userId {}", userId);

        // user 정보 가져오기
        User user = userService.findUser(userId);
        // 권한
        String role = user.getRole().getValue();


        // 권한부여
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userId, null, Collections.singleton(new SimpleGrantedAuthority(role)));

        // detail
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken); // 리퀘스트에 인증 됐다고 사인ㅋ
        filterChain.doFilter(request, response);


    }


}
