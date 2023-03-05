package com.board.service;

import com.board.service.impl.RedisService;
import com.board.util.JwtProperties;
import com.board.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshService {

    private final JwtUtil jwtUtil;
    private final RedisService redisService;

    public Map<String, String>  createRefreshToken(String refreshTokenInfo){
        log.info("[RefreshService] createRefreshToken() 시작 ===================");
        String refreshToken = refreshTokenInfo;
        log.info("[RefreshService] 검증할 refreshToken : {}", refreshToken);

        // 리턴값 담는용
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
