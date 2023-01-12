package com.board.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class Board {

    private Long idx;
    private String title;
    private String content;
    private String name;
    private String saveDate;
    private String modifyDate;

}
