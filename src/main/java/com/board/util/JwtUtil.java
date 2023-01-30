package com.board.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil{ // JWT 생성


    // token 에서 id 꺼내기
    public static String getUserId(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("id", String.class);
    }

    // token 만료 확인
    public static boolean isExpired(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }


    // 토큰생성
    public static Map<String, String> createJwt(String id, String secretKey, long expiredTime){
        Claims claims = Jwts.claims();
        claims.put("id", id);
        Date now = new Date(System.currentTimeMillis());

        //Access token
        String accessToken = Jwts.builder()
                                .setClaims(claims)
                                .setIssuedAt(now)                                       // 만든날짜
                                .setExpiration(new Date(now.getTime() + expiredTime))   // 만료일자
                                .signWith(SignatureAlgorithm.HS256, secretKey)          // HS256 알고리즘 이용해서 서명됨
                                .compact();

        //refresh 토큰생성
        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)                                                   // 만든날짜
                .setExpiration(new Date(now.getTime() + expiredTime * 24 * 5 ))     // 만료일자
                .signWith(SignatureAlgorithm.HS256, secretKey)                      // HS256 알고리즘 이용해서 서명됨
                .compact();
        
        Map<String, String> token = new HashMap<>();
        token.put("accessToken", accessToken);
        token.put("refreshToken", refreshToken);

        return token;
    }



}
