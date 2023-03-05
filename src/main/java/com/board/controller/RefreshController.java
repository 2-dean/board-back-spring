package com.board.controller;

import com.board.service.RefreshService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RefreshController {

    private final RefreshService refreshService;

    @PostMapping("/refresh")
    public ResponseEntity<Boolean> validateRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("RefreshController >> refresh Token 발급 로직 실행");
        ObjectMapper objectMapper = new ObjectMapper();
       //String refreshToken = objectMapper.readValue(request.getInputStream(), String.class);

        String refreshToken = request.getHeader("refresh");
        log.info("refreshToken : " + refreshToken);

        Map<String, String> result = refreshService.createRefreshToken(refreshToken);

        if (result.get("accessToken") != null) {
            log.info("[ /refresh ] refreshToken 유효 > accessToken 발급");
            String accessToken = result.get("accessToken");

            // 새로운 accessToken 으로 교체
            response.setHeader("Authorization", accessToken);

            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            log.info("[ /refresh ] refreshToken 만료 > 로그인 다시");
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
    }

}
