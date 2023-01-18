package com.board.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class Board {

    @Schema(description = "게시글 번호(PK)", nullable = false, hidden = true)
    private Long idx;

    @Schema(description = "제목", defaultValue = "", allowableValues = {})
    private String title;

    @Schema(description = "내용", defaultValue = "", allowableValues = {})
    private String content;

    @Schema(description = "작성자", defaultValue = "", allowableValues = {}, example = "홍길동")
    private String name;

    @Schema(hidden = true)
    private String saveDate;

    @Schema(hidden = true)
    private String modifyDate;

    @Schema(hidden = true)
    private Long fileIdx;


}
