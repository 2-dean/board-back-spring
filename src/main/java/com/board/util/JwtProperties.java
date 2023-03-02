package com.board.util;

// .gitignore 에 추가 필요
public interface JwtProperties {

    String TOKEN_PREFIX = "Bearer ";

    String ACCESS_SECRET_KEY = "secretkey111";
    String REFRESH_SECRET_KEY = "secretkey222";

    // DB 저장 만료시간 - 밀리 초
    Long ACCESS_EXPIRATION_TIME = 1000L * 60;       //1분   / 10분 1000 * 60 * 10L;
    Long REFRESH_EXPIRATION_TIME = 1000L * 60 * 5;  //3분   / 1일 1000 * 60 * 60 * 24L;

    // 쿠키 만료시간 - 초
    int ACCESS_COOKIE_EXPIRATION_TIME = 60;         //1분
    int REFRESH_COOKIE_EXPIRATION_TIME = 60 * 5;    //3분

}
