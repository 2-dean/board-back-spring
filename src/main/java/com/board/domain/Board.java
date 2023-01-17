package com.board.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class Board {

    private Long idx;           //게시글번호
    private String title;       //제목
    private String content;     //내용
    private String name;        //작성자
    private String saveDate;    //작성일자
    private String modifyDate;  //수정일자
    private Long fileIdx;       //제목


}
