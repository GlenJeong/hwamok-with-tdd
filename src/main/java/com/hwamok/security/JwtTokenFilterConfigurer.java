package com.hwamok.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtTokenFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtTokenFilter jwtFilter;
    // ctrl o
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class );
    } // UsernamePasswordAuthenticationFilter.class 보다 jwtFilter를 먼저 실행
    // HTTP 요청이 처리되기 전에 JwtTokenFilter가 실행되어 JWT 토큰을 확인하고,
    // 사용자를 인증하여 Spring Security 컨텍스트에 저장
}


// JwtTokenFilter를 Spring Security 필터 체인에 등록하여 JWT 토큰 검증 및 사용자 인증이 필터 체인에서 이루어지도록 하는 것