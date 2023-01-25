package com.board.mapper;

import com.board.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    //회원찾기
    User findUser(User user);

}
