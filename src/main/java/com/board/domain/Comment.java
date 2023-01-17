package com.board.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Comment {

    private Long idx;           //댓글번호
    private Long boardIdx;      //댓글이 작성된 게시글번호
    private String name;        //작성자
    private String content;     //내용
    private String saveDate;    //작성일자

}
