package com.board.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttachedFile {

    private Long idx;           //파일번호
    private String savePath;    //저장경로
    private String oriFileName; //첨부파일명
    private String saveFileName;//시스템저장명


}
