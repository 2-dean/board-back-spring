package com.board.config.security;

import com.board.exception.AppException;
import com.board.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
@AllArgsConstructor
public class CustomAuthProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info(">> CustomAuthProvider.authentication 실행  : " + authentication);
        String id = authentication.getName();
        String password = (String) authentication.getCredentials();

        log.info("DB 에 사용자 id 있는지 확인"); // service 에서 예외처리 함
        UserDetails userDetails = userDetailsService.loadUserByUsername(id);

        log.info("비밀번호 일치여부 확인 할 userDetails : \n{}", userDetails);

        try {
            log.info("입력받은 비밀번호 일치여부 확인");
            encoder.matches(password, userDetails.getPassword());
        } catch (NullPointerException e) {
            log.error("비밀번호 일치하지 않음");
            throw new AppException(ErrorCode.INVALID_PASSWORD, "비밀번호가 일치하지 않음");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());


    }

    @Override
    public boolean supports(Class<?> authentication) {
        //TODO 이거 뭐지
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }


}
