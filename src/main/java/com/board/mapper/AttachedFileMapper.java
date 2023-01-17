package com.board.mapper;

import com.board.domain.AttachedFile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttachedFileMapper {

    //파일 업로드
    Long saveFile(AttachedFile file);
    //파일 다운로드
    AttachedFile downloadFile(Long fileIdx);

}
