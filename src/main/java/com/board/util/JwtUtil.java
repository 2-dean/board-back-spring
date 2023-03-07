package com.board.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtUtil{ // JWT 생성, 디코딩, 만료

    private Date now = new Date(System.currentTimeMillis());
    private String token;

    // Access Token 생성
    public String createAccessToken(String id) {
        return create(id, "accessToken");
    }
    // Refresh Token 생성
    public String createRefreshToken() {
        return create(null, "refreshToken");
    }

    /**
     * 토큰과 key 를 받아서 사용자 정보를 반환
     *
     * @param token
     * @param key
     * @return
     */
    public String getUserName(String token, String key) {
        log.info("[ JwtUtil.getUserName 실행 ]========================");
        log.info("[토큰] : \n {} " , token);
        log.info("[key] : \n {}", key);
        return Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody()
                    .get("id", String.class);
    }

    /**
     * 토큰 만료 확인
     *
     * @param token
     * @return
     */
    public void isExpired(String token, String key) throws ExpiredJwtException {
        log.info("[ JwUtil.isExpired ]========================");
        log.info("[ 만료 확인할 token : \n{}", token);

            Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token);

    }

    /** 토큰 생성
     *
     * @param id
     * @param subject
     * @return "token 값
     */
    private String create(String id, String subject) {
        if (subject.equals("accessToken") ){
            log.info("[ access token 생성할 id ] : {} ", id);
            token = Jwts.builder()
                    .setHeaderParam("type", "jwt")      //header 설정
                    .claim("id", id)                        //payload 설정 - claim 정보 포함
                    .setSubject(subject)
                    .setIssuedAt(now)
                    .setExpiration(new Date(System.currentTimeMillis() + JwtProperties.ACCESS_EXPIRATION_TIME))   // 만료일자
                    .signWith(SignatureAlgorithm.HS256, JwtProperties.ACCESS_SECRET_KEY)             // HS256 알고리즘 이용, secret key 이용 암호화
                    .compact();
            log.info("[ 생성된 token ] : \n{}", token);
            token = (JwtProperties.TOKEN_PREFIX + token);
            log.info("[ 생성된 Bearer Token ] : \n{} ", token);
        }
        if (subject.equals("refreshToken")) {
            token = Jwts.builder()
                    .setHeaderParam("type","jwt")
                    .claim("id", id)
                    .setSubject(subject)
                    .setIssuedAt(now)
                    .setExpiration(new Date(System.currentTimeMillis() + JwtProperties.REFRESH_EXPIRATION_TIME))
                    .signWith(SignatureAlgorithm.HS256, JwtProperties.REFRESH_SECRET_KEY)
                    .compact();
        }

        return token;
    }




}
