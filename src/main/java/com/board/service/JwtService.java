package com.board.service;

import com.board.service.impl.RedisService;
import com.board.util.JwtProperties;
import com.board.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtUtil jwtUtil;
    private final RedisService redisService;

    public Map<String, String> createRefreshToken(Map<String, String>  refreshTokenInfo){
        String refreshToken = refreshTokenInfo.get("refreshToken");
        String id = refreshTokenInfo.get("id");

        Map<String, String> map = new HashMap<>();


        boolean expired = jwtUtil.isExpired(refreshToken, JwtProperties.REFRESH_SECRET_KEY);

        if(expired) {
            log.info("refreshToken 만료");

            map.put("errortype", "Forbidden");
            map.put("status", "402");
            map.put("message", "Refresh 토큰이 만료되었습니다. 로그인이 필요합니다.");
        }
        if(!expired) {
            log.info("refreshToken 유효");

            String createdAccessToken = jwtUtil.createAccessToken(id);
            redisService.setAccessValues(createdAccessToken, id);

            map.put("status", "200");
            map.put("message", "Refresh 토큰을 통한 Access Token 생성이 완료되었습니다.");
            map.put("accessToken", createdAccessToken);
        }

        return map;

    }
}
