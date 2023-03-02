package com.board.controller;

import com.board.service.JwtService;
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
    public ResponseEntity<String> validateRefreshToken(Map<String, String> refreshTokenInfo, HttpServletResponse response) {
        log.info("RefreshController >> refresh Token 발급 로직 실행");

        Map<String, String> result = jwtService.createRefreshToken(refreshTokenInfo);
        if (result.get("accessToken") != null) {
            String accessToken = result.get("accessToken");
            response.addHeader("Authorization", accessToken);

            return new ResponseEntity<>("Refresh 토큰을 통한 Access Token 생성이 완료되었습니다.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Refresh 토큰 만료됨", HttpStatus.UNAUTHORIZED);
        }
    }

}
