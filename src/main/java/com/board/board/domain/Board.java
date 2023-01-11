package com.board.board.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;


@Getter
@Setter
@ToString
public class Board {

    private Long idx;
    private String title;
    private String content;
    private String name;
    private Date saveDate;
    private Date modifyDate;

}
