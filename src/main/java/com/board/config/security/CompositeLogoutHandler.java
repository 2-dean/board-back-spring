package com.board.config.security;

import com.board.util.JwtProperties;
import com.board.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class CompositeLogoutHandler implements LogoutHandler {

    private final JwtUtil jwtUtil;

    private String accessToken;
    private String refreshToken;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 1. 쿠키에서 토큰 값 가져오기
        // 2. 만료시키기


        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("accessToken")) {
                accessToken = cookie.getValue();
            }
            if (cookie.getName().equals("refreshToken")) {
                refreshToken = cookie.getValue();
            }
        }

        if (accessToken != null || jwtUtil.isExpired(accessToken, JwtProperties.ACCESS_SECRET_KEY)) {
        }





    }
}
