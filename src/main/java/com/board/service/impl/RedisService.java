package com.board.service.impl;

import com.board.util.JwtProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {
    private final RedisTemplate redisTemplate;

    // 데이터 넣기
    public void setAccessValues(String accessToken, String id){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(accessToken, id, Duration.ofMillis(JwtProperties.ACCESS_EXPIRATION_TIME));
    }
    //
    public void setRefreshValues(String refreshToken, String id){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(refreshToken, id, Duration.ofMillis(JwtProperties.REFRESH_EXPIRATION_TIME));
    }
    // 데이터 가져오기
    public String getValues(String token){
        log.info(">>> Redis 에서 가져올 token : " + token);
        ValueOperations<String, String> values = redisTemplate.opsForValue();

        String id = values.get(token);
        log.info(">>>>>>>> redis 에서 가져온 id : " + id);
        return id;
    }
}
