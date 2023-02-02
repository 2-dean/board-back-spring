package com.board.mapper;

import com.board.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RefreshTokenMapper {
    void saveRefreshToken(User user);

}
