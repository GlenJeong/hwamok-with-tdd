package com.hwamok.auth.service;

import com.hwamok.core.exception.ExceptionCode;
import com.hwamok.core.exception.HwamokException;
import com.hwamok.user.domain.User;
import com.hwamok.user.domain.UserRepository;
import com.hwamok.user.domain.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //

    @Override
    public void login(String login, String password) {
        User user = userRepository
                .findByLoginIdAndUserStatus(login, UserStatus.ACTIVATED)
                .orElseThrow(()-> new HwamokException(ExceptionCode.FAIL_LOGIN_REQUEST));

        // Bcrypt Encoding -> 단방향 암호화
        // Bcrypte 생성할 때 평문을 알고리즘으로 5번 돌린다. --> 암호화된 값(해시)
        // 암호화 안된 것을 평문이라고 한다.
        // password(평문)을 5번 돌렸을 때 암호화가 진행된 패스워드의 해시값과 같은지
        // 해시 값이 같은 지 비교한다. 해시 값이 같다는 것은 동시성을 의미함
        // Bcrypte를 추가해야함
        // passwordEncoder라는 인터페이스에서 Bcrypt를 사용하길 원함
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new HwamokException(ExceptionCode.FAIL_LOGIN_REQUEST);
        }
    }
}
