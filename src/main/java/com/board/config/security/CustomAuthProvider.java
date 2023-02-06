package com.board.config.security;

import com.board.exception.AppException;
import com.board.exception.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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
            //여기서 appException 발생 시키는데 500에러로 끝남 >> 예외처리 필요
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        //TODO 이거 뭐지
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
