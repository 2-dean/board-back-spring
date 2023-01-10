package com.board.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Reply {

    private Long idx;
    private Long bordIdx;
    private String name;
    private String content;
    private String saveDate;

}
