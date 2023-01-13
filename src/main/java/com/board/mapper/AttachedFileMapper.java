package com.board.mapper;

import com.board.domain.AttachedFile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttachedFileMapper {

    Long saveFile(AttachedFile file);

}
