package com.board.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.netty.handler.codec.base64.Base64Encoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtUtil{ // JWT 생성, 디코딩

    public String createAuthToken(String id) {
        return create(id, "authToken");
    }

    public String createRefreshToken() {
        return create(null, "refresh");
    }

    private String create(String id, String subject) {
        Date now = new Date(System.currentTimeMillis());
        String token = null;
        
        if (subject.equals("authToken") ){
            token = Jwts.builder()
                                .setHeaderParam("type", "jwt")       //header 설정
                                .claim("id", id)                          //payload 설정 - claim 정보 포함
                                .setSubject(subject)
                                .setIssuedAt(now)
                                .setExpiration(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))   // 만료일자
                                .signWith(SignatureAlgorithm.HS256, JwtProperties.ACCESS_SECRET_KEY)             // HS256 알고리즘 이용, secret key 이용 암호화
                                .compact();
        } 
        if (subject.equals("refresh")) {
            token = Jwts.builder()
                                .setHeaderParam("type","jwt")       //header 설정
                                .setSubject(subject)
                                .setIssuedAt(now)
                                .setExpiration(new Date(JwtProperties.REDIS_EXPIRATION_TIME))   // 만료일자
                                .signWith(SignatureAlgorithm.HS256, JwtProperties.REFRESH_SECRET_KEY)             // HS256 알고리즘 이용, secret key 이용 암호화
                                .compact();
        }

        return token;
    }




    /* 토큰을 기반으로 사용자 정보를 반환 해주는 메서드
     *
     * @param token String : 토큰
     * @return String : 사용자 정보
     */
    public String getUserName(String token, String key) {
        System.out.println("jwt 인증 : " + token);
        return Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody().get("id", String.class);
    }

    // token 만료 확인
    public boolean isExpired(String token, String key) { //TODO key두개로 하기
        System.out.println("isExpired: " + token);

        try {
            Jwts.parser().setSigningKey(key)
                         .parseClaimsJws(token);
            return false;
        } catch (ExpiredJwtException e) {
            log.info("JwtUtil.isExpired > 토큰 만료");
            return true;
            //throw new AppException(ErrorCode.USERNAME_NOT_FOUND, "jwt만료됨");
        }

    }



}
