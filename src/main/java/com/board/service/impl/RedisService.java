package com.board.service.impl;

import com.board.util.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate redisTemplate;

    // 데이터 넣기
    public void setValues(String id, String refreshToken){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(id, refreshToken, Duration.ofMinutes(JwtProperties.REDIS_EXPIRATION_TIME));
    }
    public void setAccessValues(String id, String access){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(id, access, JwtProperties.ACCESS_EXPIRATION_TIME);
    }
    // 데이터 가져오기
    public String getValues(String refreshToken){
        System.out.println(">>> Redis 에서 가져올 token key : " + refreshToken);
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(refreshToken);
    }
}
