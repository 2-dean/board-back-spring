package com.board.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BoardVO {
    //VO(Value Object) 값 오브젝트로써 값을 위해 쓰입니다. read-Only 특징(사용하는 도중에 변경 불가능하며 오직 읽기만 가능)을 가집니다.

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

    private String savePath;    //저장경로
    private String oriFileName; //첨부파일명
    private String saveFileName;//시스템저장명
}
