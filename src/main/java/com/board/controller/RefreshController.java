package com.board.controller;

import com.board.service.JwtService;
import com.board.util.JwtProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RefreshController {

    private final JwtService jwtService;

    @PostMapping("/refresh")
    public ResponseEntity<Boolean> validateRefreshToken(Map<String, String> refreshTokenInfo, HttpServletResponse response) {
        log.info("RefreshController >> refresh Token 발급 로직 실행");

        Map<String, String> result = jwtService.createRefreshToken(refreshTokenInfo);

        if (result.get("accessToken") != null) {
            log.info("[ /refresh ] refreshToken 유효 > accessToken 발급");
            String accessToken = result.get("accessToken");

            // 새로운 accessToken 으로 교체
            response.setHeader("Authorization", accessToken);
            response.setHeader("expireTime", String.valueOf(JwtProperties.ACCESS_EXPIRATION_TIME));

            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            log.info("[ /refresh ] refreshToken 만료 > 로그인 다시");
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
    }

}
