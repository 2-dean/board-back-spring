package com.board.mapper;

import com.board.domain.User;
import io.jsonwebtoken.Claims;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    //Id 중복 체크
    Optional<User> findUser(String id);
    //회원 등록
    int save(User user);
    //토큰 등록
    void setJwt(String token);



}
