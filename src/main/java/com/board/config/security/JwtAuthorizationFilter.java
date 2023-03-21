package com.board.config.security;

import com.board.domain.User;
import com.board.exception.AppException;
import com.board.exception.ErrorCode;
import com.board.mapper.UserMapper;
import com.board.service.impl.RedisService;
import com.board.util.JwtProperties;
import com.board.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Optional;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    /*
        JWT로 인가를 하기 위한 클래스
        헤더를 통한 인증 시 적용되는 BasicAuthenticationFilter를 상속받는다.
        BasicAuthenticationFilter는 AuthenticationManager를 사용하기 때문에 super를 사용해서 주입해준다.
    */

    private final UserMapper userMapper;
    private final RedisService redisService;
    private final JwtUtil jwtUtil;

    private String accessToken;
    private String refreshToken;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserMapper userMapper, RedisService redisService, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.userMapper = userMapper;
        this.redisService = redisService;
        this.jwtUtil = jwtUtil;
    }

    /*
        1. Access 토큰 만료확인
        2. 만료됐을 경우 Refresh 토큰 만료여부 확인
        3. Refresh 토큰 만료됐을 경우 새로 로그인
        4. Refresh 토큰 유효할 경우 Access 다시 발급
        5. Access 유효할 경우 사용자 정보 SecuritycontextHolder 에 저장
     */

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("========================[ JwtAuthorizationFilter ]========================");
        //TODO 특정 url 은 JwtAuthorization Filter 로 들어오지 않도록 처리하기 config 에서 처리 할 수 있는지 확인해보기, 로그인 되어있으면 회원가입 안되기~!

        //0. 토큰 꺼내기
        try {
            log.info("[ 1. accessToken 꺼내기 ]===============================");
            accessToken = request.getHeader("Authorization").replace(JwtProperties.TOKEN_PREFIX, "");
            log.info("[ accessToken ] : \n {}", accessToken);

            log.info("[ 2. getCookies 가져오기 ] ");
            request.getCookies();

            log.info("[ 3. cookie 에 있는 refreshToken 담기 ] ");
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("refreshToken")) {
                    refreshToken = cookie.getValue();
                    log.info("[ refreshToken ] : \n{} ", refreshToken);
                }
            }

            log.info("[ 4. accessToken 유효성 검사 ]===============================");
            jwtUtil.isExpired(accessToken, JwtProperties.ACCESS_SECRET_KEY);
            // 1-2. Access 유효함 -> 사용자 정보 SecurityContextHolder 에 저장
            log.info("[ 5. accessToken 유효함 ]");




            // 토큰 에서 사용자 정보 가져오기
            String id = jwtUtil.getUserName(accessToken, JwtProperties.ACCESS_SECRET_KEY);
            log.info("[ 6. accessToken 에서 가져온 사용자 ID : {} ]", id);

            log.info("[ 7. Authentication 객체 생성 및 SecurityContextHolder 에 등록 ]");
            if (id != null) {
                User user = userMapper.findUser(id).orElseThrow(() -> new UsernameNotFoundException("해당 id의 User 없음"));
                //TODO catch 문에서 해결해보기
                //User user = userMapper.findUser(id).orElseThrow(() -> new UsernameNotFoundException("해당 id의 User 없음"));
                Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (ExpiredJwtException e) {
            log.error("[ ExpiredJwtException 발생! ] accessToken 만료됨 ");

            log.info("[ 1. refreshToken 유효성 검사 =========================]");
            jwtUtil.isExpired(refreshToken, JwtProperties.REFRESH_SECRET_KEY);
            log.info(" 2. refreshToken 유효함");

            String id = redisService.getValues(refreshToken);
            userMapper.findUser(id).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없음"));

            log.info("========================[ 새 accessToke refresh Token 발급 - START ]========================");

            log.info("[ 3. 새로운 accessToken 발급 및 저장 ]");
            accessToken = jwtUtil.createAccessToken(id);
            log.info("[ 4. NEW Access Token : \n{} ", accessToken);

            response.addHeader("Authorization", accessToken);
            redisService.setAccessValues(accessToken, id);
            log.info("[ 5. Access Token || response header 에 추가 ]");

            log.info("[ 6. 새로운 refreshToken 발급 및 저장 ]");
            refreshToken = jwtUtil.createRefreshToken();
            redisService.setRefreshValues(refreshToken, id);
            log.info("[ 7. NEW refresh Token : \n{} ]" + refreshToken);

            Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
            response.addCookie(refreshCookie);
            log.info("[ 8. NEW refresh Token || response Cookie 에 추가");

            log.info ("========================[ 새 accessToke refresh Token 발급 - END ]========================")

            response.setStatus(HttpStatus.NOT_ACCEPTABLE.value()); //Not Acceptable(접수할 수 없음) === 406
            filterChain.doFilter(request, response);

            log.error("[ !!! refreshToken Expired : 다시로그인필요 =========================]");
            response.setStatus(HttpStatus.FORBIDDEN.value()); // 403
            //logout 호출
            filterChain.doFilter(request, response);

        } catch (NullPointerException e) {
            log.info("[ refresh Token 가져오기 request.getCookies ] >> 쿠키없음");
            response.setStatus(HttpStatus.FORBIDDEN.value());
        }

    }//doFilterInternal()

} // class