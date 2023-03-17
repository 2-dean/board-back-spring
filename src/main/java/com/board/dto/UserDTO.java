package com.board.dto;

import com.board.domain.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserDTO {
    /*계층 간 데이터 교환을 하기 위해 사용하는 객체
     * 유저가 자신의 브라우저에서 데이터를 입력하여 form에 있는 데이터를 DTO에 넣어서 전송
     * 해당 DTO를 받은 서버가 DAO를 이용하여 데이터베이스로 데이터를 집어넣습니다
     * */

    // USER 정보 조회용
    @Schema(description = "idx", nullable = false)
    private Long idx;

    @Schema(description = "아이디", nullable = false)
    private String id;

    @Schema(description = "이름", nullable = false)
    private String name;

    @Schema(description = "역할", nullable = false)
    private Role role;
}
