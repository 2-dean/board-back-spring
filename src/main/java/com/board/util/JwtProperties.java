package com.board.util;

// .gitignore 에 추가 필요
public interface JwtProperties {

    String ACCESS_SECRET_KEY = "secretkey111";
    String REFRESH_SECRET_KEY = "secretkey222";

    Long ACCESS_EXPIRATION_TIME = 1000L * 60;       // 1분   / 10분 1000 * 60 * 10L;
    Long REFRESH_EXPIRATION_TIME = 1000L * 60 * 10;  // 10분   / 1일 1000 * 60 * 60 * 24L;

    //Redis 설정용
/*    Long REDIS_EXPIRATION_TIME = 3; //5분
    Long ACCESS_EXPIRATION_TIME = 1000L * 30; //5분*/

}
