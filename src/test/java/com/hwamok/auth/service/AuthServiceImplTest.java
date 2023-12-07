package com.hwamok.auth.service;

import com.hwamok.user.domain.User;
import com.hwamok.user.domain.UserRepository;
import fixtures.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
@Transactional
class AuthServiceImplTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    User user;
    @BeforeEach
    void setUp() throws Exception {
        user = userRepository.save(userRepository.save(UserFixture.createUser("jyb1624", passwordEncoder.encode("q1w2e3r4t5"))));
    }

    @Test
    void 로그인_성공() {

        authService.login("jyb1624", "q1w2e3r4t5");

    }
}