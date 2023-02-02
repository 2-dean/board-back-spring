package com.board.security;

import com.board.exception.AppException;
import com.board.exception.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@AllArgsConstructor
public class CustomAuthProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println(">> CustomAuthProvider.authentication 실행  : " + authentication);
        String id = authentication.getName();
        String password = (String) authentication.getCredentials();

        UserDetails userDetails = userDetailsService.loadUserByUsername(id);

        // 비밀번호 확인
        if(!encoder.matches(password, userDetails.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, "비밀번호가 일치하지 않음");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}