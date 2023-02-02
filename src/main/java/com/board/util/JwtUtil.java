package com.board.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtUtil{ // JWT 생성, 디코딩

    // 토큰 만들기
    public static String createAuthJwtToken(String id) {
        Claims claims = Jwts.claims();
        claims.put("id", id);

        Date now = new Date(System.currentTimeMillis());

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)  // 만든날짜
                .setExpiration(new Date(now.getTime() + JwtProperties.EXPIRATION_TIME))   // 만료일자
                .signWith(SignatureAlgorithm.HS256, JwtProperties.SECRET_KEY)             // HS256 알고리즘 이용해서 서명됨
                .compact();

        System.out.println("accessToken : " + accessToken.toString() );
        return accessToken;
    }

    public static String createRefreshToken() {
        Claims claims = Jwts.claims();
        Date now = new Date(System.currentTimeMillis());

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)  // 만든날짜
                .setExpiration(new Date(now.getTime() + JwtProperties.REFRESH_EXPIRATION_TIME))   // 만료일자
                .signWith(SignatureAlgorithm.HS256, JwtProperties.SECRET_KEY)             // HS256 알고리즘 이용해서 서명됨
                .compact();
        return refreshToken;

    }


    /* 토큰을 기반으로 사용자 정보를 반환 해주는 메서드
     *
     * @param token String : 토큰
     * @return String : 사용자 정보
     */
    public static String getUserName(String token) {
        System.out.println("jwt 인증 : " + token);
        return Jwts.parser()
                    .setSigningKey(JwtProperties.SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody().get("id", String.class);
    }

    // token 만료 확인
    public static boolean isExpired(String token) {
        return Jwts.parser().setSigningKey(JwtProperties.SECRET_KEY).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }



}
