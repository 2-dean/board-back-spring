package com.board.dto;

import com.board.domain.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;


@Data //도메인 클래스(DTO 포함)에 getter/setter/생성자/toString/equals/hashCode 메소드 자동생성
@Builder
public class UserDTO {
//DTO(Data Transfer Object)는 계층 간 (Web Layer  Service Layer) 데이터 교환을 위한 객체로서, api 패키지 하위에 dto 패키지를 생성한다


    private Long idx;
    @Schema(description = "아이디", nullable = false)
    private String id;
    @Schema(description = "비밀번호", nullable = false)
    private String password;
    @Schema(description = "이름", nullable = false)
    private String name;
    @Schema(description = "역할", nullable = false)
    private Role role;
}
