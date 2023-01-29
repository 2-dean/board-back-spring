package com.board.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
                    //spring security 에서 사용자의 정보를 담는 인터페이스. spring security 에서 사용자 정보를 불러오기 위해 구현해야하는 인터페이스

    @Schema(description = "사용자 번호(PK)", nullable = false, hidden = true)
    private Long idx;
    @Schema(description = "아이디", nullable = false)
    private String id;
    @Schema(description = "비밀번호", nullable = false)
    private String password;
    @Schema(description = "이름", nullable = false)
    private String name;
    @Schema(description = "역할", nullable = false)
    private Role role;

    public User(User user) {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(role.getValue()));
        System.out.println(">> Uesr.java >> roles: " + roles.toString());
        return roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() { //username = 계정의 고유한 값
        return this.id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
