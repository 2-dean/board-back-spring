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

    public Map<String, String> createRefreshToken(Map<String, String> refreshTokenInfo){
        String refreshToken = refreshTokenInfo.get("refreshToken");

        Map<String, String> map = new HashMap<>();

        // refreshToken 만료 확인
        boolean expired = jwtUtil.isExpired(refreshToken, JwtProperties.REFRESH_SECRET_KEY);

        if(expired) {
            log.info("refreshToken 만료");
        }
        if(!expired) {
            log.info("refreshToken 유효");
            String id = redisService.getValues(refreshToken);

            String newAccessToken = jwtUtil.createAccessToken(id);
            redisService.setAccessValues(newAccessToken, id);

            map.put("accessToken", newAccessToken);
        }

        return map;

    }
}
