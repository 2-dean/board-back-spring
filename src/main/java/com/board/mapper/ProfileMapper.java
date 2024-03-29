package com.board.mapper;

import com.board.model.ProfileModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProfileMapper {

    // 프로필 등록
    @Insert("INSERT INTO profile (profileName, profileURL) VALUES (#{profileName}, #{profileURL})")
    void saveProfile(ProfileModel profileModel);
}
