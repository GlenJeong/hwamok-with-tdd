package com.hwamok.auth.service;

import com.hwamok.core.exception.ExceptionCode;
import com.hwamok.core.exception.HwamokException;
import com.hwamok.security.JwtService;
import com.hwamok.security.JwtType;
import com.hwamok.user.domain.User;
import com.hwamok.user.domain.UserRepository;
import com.hwamok.user.domain.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;
    //

    @Override
    public Pair<String, String> login(String login, String password) {
        User user = userRepository
                .findByLoginIdAndUserStatus(login, UserStatus.ACTIVATED)
                .orElseThrow(()-> new HwamokException(ExceptionCode.FAIL_LOGIN_REQUEST));

        // Bcrypt Encoding -> 단방향 암호화
        // Bcrypte 생성할 때 평문을 알고리즘으로 5번 돌린다. --> 암호화된 값(해시)
        // 암호화 안된 것을 평문이라고 한다.
        // password(평문)을 5번 돌렸을 때 암호화가 진행된 패스워드의 해시값과 같은지
        // 해시 값이 같은 지 비교한다. 해시 값이 같다는 것은 동일성을 의미함
        // Bcrypte를 추가해야함
        // passwordEncoder라는 인터페이스에서 Bcrypt를 사용하길 원함
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new HwamokException(ExceptionCode.FAIL_LOGIN_REQUEST);
        }

        // 여기까지 코드가 실행됐다는 것은 로그인이 완료 되었다는 의미이다.
        // User를 리턴해서 controller에서 session에 담아줌 --> 세션 이재 안씀
        // jwt 토근을 발행해줌으로써 로그인 처리를 완료함

        // 프론트는 jwt 토큰을 가지고 있다가 모든 요청 보낼때 jwt 토큰을 header에 담아서 보냄
        // 서버에서는 헤더에 있는 jwt 토큰을 해독해서 우리 서버의 맞으며 인증된 사용자로 처리

        String accessToken = jwtService.issue(user.getId(), JwtType.ACCESS);
        String refreshToken = jwtService.issue(user.getId(), JwtType.REFRESH);
        System.out.println("accessToken = " + accessToken);
        System.out.println("refreshToken = " + refreshToken);


        // DTO
        // Pair
        return Pair.of(accessToken, refreshToken); //getFirst accessToken

    }
}
