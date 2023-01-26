package com.board.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
                    //spring security를 이용하기 위한 객체로 사용(userDTO, userDetailsDTO로 따로 만들어야할지?)

    @Schema(description = "사용자 번호(PK)", nullable = false, hidden = true)
    private Long idx;
    @Schema(description = "아이디", nullable = false)
    private String id;
    @Schema(description = "비밀번호", nullable = false)
    private String password;
    @Schema(description = "이름", nullable = false)
    private String name;
    @Schema(description = "역할", nullable = false)
    private Long role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
