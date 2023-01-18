package com.board.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Comment {
    @Schema(description = "댓글 번호(PK)", nullable = false, hidden = true)
    private Long idx;

    @Schema(description = "댓글이 작성된 게시글 번호", hidden = true)
    private Long boardIdx;

    @Schema(description = "작성자")
    private String name;

    @Schema(description = "내용")
    private String content;

    @Schema(description = "작성일자", hidden = true)
    private String saveDate;

}

