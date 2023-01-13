package com.board.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Comment {

    private Long idx;
    private Long boardIdx;
    private String name;
    private String content;
    private String saveDate;

}
