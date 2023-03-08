package com.board.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomLogoutHandler implements LogoutHandler {


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("[ LogoutHandler.onLogoutSuccess ] 실행");

        log.info("Header 에 accessToken null 입력");
        response.setHeader("Authorization", null);

        log.info("Cookie refreshToken 만료시키기");
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("refreshToken")) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }

        log.info("[ LogoutHandler.onLogoutSuccess ] END");
    }


}
