package com.board.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttachedFile {

    private Long idx;
    private String oriFileName;
    private String saveFileName;
    private String savePath;

}
