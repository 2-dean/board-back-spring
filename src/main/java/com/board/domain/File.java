package com.board.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class File {

    private Long idx;
    String oriFileName;
    String saveFileName;
    String savePath;

}
