package com.board.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Date;

@RequiredArgsConstructor
public class JwtUtil{ // JWT 생성, 디코딩

    // 토큰 만들기
    public static String createAuthJwtToken(Authentication authentication) {
        Claims claims = Jwts.claims();
        claims.put("id", authentication.getName());

        Date now = new Date(System.currentTimeMillis());

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)  // 만든날짜
                .setExpiration(new Date(now.getTime() + JwtProperties.EXPIRATION_TIME))   // 만료일자
                .signWith(SignatureAlgorithm.HS256, JwtProperties.SECRET_KEY)             // HS256 알고리즘 이용해서 서명됨
                .compact();
        return accessToken;
    }

  /*  // token을 받아서 claim 만들고 > user 객체 생성해서 Authentication 객체 반환하기
    public Authentication getAuthentication(String token) {

        String id = Jwts.parser()
                        .setSigningKey(JwtProperties.SECRET_KEY)
                        .parseClaimsJws(token)
                        .getBody().get("id", String.class);

        UserDetails userDetails = UserDeta

        return new UsernamePasswordAuthenticationToken()

    }*/

    // token 만료 확인
    public static boolean isExpired(String token) {
        return Jwts.parser().setSigningKey(JwtProperties.SECRET_KEY).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }



}
