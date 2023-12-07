package com.hwamok.security;

import com.hwamok.core.exception.ExceptionCode;
import com.hwamok.core.exception.HwamokException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    // OncePerRequestFilter 한번 요청에 대해 한번만 실행하는 필터
    // HTTP 요청이 올 때마다 실행되며, JWT 토큰을 검증하고 해당 토큰에 대한 사용자를
    // Spring Security의 Authentication 객체로 변환하여 컨텍스트에 저장하는 역할
    private final JwtService jwtService;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // HTTP 요청마다 한 번씩 호출됨
        // HttpServletRequest가 요청에 관한 모든 정보를 알고 있다.
        // accessToken 토큰을 가져와야함 -> 어디서?  request header에서 가져옴
        // accessToken에서 Id값을 가져옴
        // Spring Security에서 session 인증된에 객체를 넣어줌
        //

        String token = jwtService.resolveToken(request); // HTTP 요청의 헤더에서 JWT 토큰을 가져옴

        if(token != null) { // 인증하는 단계, 토큰이 존재하는 지 확인
            if (jwtService.validate(token)) { // 토큰의 유효성을 검증
                response.sendError(HttpStatus.UNAUTHORIZED.value(), ExceptionCode.ACCESS_DNEIED.getMessage());
                // throw new HwamokException(ExceptionCode.ACCESS_DNEIED);
                // 토큰이 유효하지 않으면 HttpStatus.UNAUTHORIZED와 함께 예외 메시지를 응답으로 전송하고 필터 체인을 종료
                return; //토큰이 유효하지 않을 때, UNAUTHORIZED(401) 에러를 반환하고 해당 필터에서 더 이상 작업을 진행하지 않도록 하는 역할
            }
            Long id = jwtService.getId(token);
            System.out.println("id = " + id);
            // 토큰이 유효하다면, jwtService.getId(token)를 사용하여 토큰에서 추출한 사용자 ID를 얻어옴

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(id, "", Collections.emptyList());
            // UsernamePasswordAuthenticationToken을 생성

            // authenticationManager.authenticate(...)를 호출하여 사용자를 인증, authenticationManager는 Spring Security에서 제공하는 인증 매니저
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            // Spring Security 컨텍스트에 사용자 정보
        }

        //AuthenticationProvider 는 한 줄로 정의 하면 db에서 가져온 정보와 input 된 정보가 비교되서
        // 체크되는 로직이 포함되어있는 인터페이스 라고 할 수 있다.
        //AuthenticationProvider를 상속하면 authenticate 메소드를 오버라이드 할 수 있다.
        //authenticate 메소드의 경우 Authentication Manager에서 넘겨준 인증객체(Authentication)를 가지고 메소드를 만든다.



        filterChain.doFilter(request, response); // 다음 필터로 넘김
    } // OncePerRequestFilter 비교적 간단함
    // 모든 작업이 완료되면 filterChain.doFilter(request, response)를 호출하여 다음 필터로 제어를 전달

}



// Filter -> UsernamePasswordFilter -> 인증, 인가 완료
// 인증 인가 되었다면 표시를 해줘야 한다.
// UsernamePasswordFilter를 지나갈 때 로그인을 시도한 유저가
// AccessToken이 발급이 된다면
// 다음 요청부터는 AccessToken을 헤더에 담아서 요청을 보냄
// AccessToken을 해독해서 Id값을 가져와야함
// UsernamePasswordFilter를 지날 때 ID값을 userRepository에서 검색해서
// User 객체로 Security에게 이 객체는 로그인한 객체라고 알려줘야 한다.
// Security는 SecurityContextHolder안에 Authentication이라는 Sessoin과 비슷한 저장 공간이 있는 데 여기에 Id 값을 넣어줘야함
