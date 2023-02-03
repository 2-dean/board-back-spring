package com.board.util;

// .gitignore 에 추가 필요
public interface JwtProperties {

    String SECRET_KEY = "secretkey";
    Long EXPIRATION_TIME = 1000 * 60L; //10분 1000 * 60 * 10L;
    Long REFRESH_EXPIRATION_TIME = 1000 * 60L * 3; //1일 1000 * 60 * 60 * 24L;


}
