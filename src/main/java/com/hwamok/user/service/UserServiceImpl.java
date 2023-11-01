package com.hwamok.user.service;

import com.hwamok.core.exception.ExceptionCode;
import com.hwamok.core.exception.HwamokException;
import com.hwamok.user.domain.User;
import com.hwamok.user.domain.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    public final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User create(String loginId, String password, String email, String nickname, String name, String userStatus, String birthday) throws Exception {
        return userRepository.save(new User(loginId, passwordEncoder.encode(password), email, nickname, name, userStatus, birthday));
    }

    @Override
    public User getUser(String loginId) {
        return userRepository.findByLoginId(loginId).orElseThrow(()-> new HwamokException(ExceptionCode.NOT_FOUND_USER));
    }

    @Override
    public User updateProfile(long id, String loginId, String password, String email, String nickname, String name, String userStatus, String birthday) throws Exception {
        User user = userRepository.findById(id).orElseThrow(()-> new HwamokException(ExceptionCode.NOT_FOUND_USER));
        user.updateUser(loginId, password, email, nickname, name, userStatus, birthday);
        return user;
    }

    @Override
    public void withdraw(String loginId) {
        User user = userRepository.findByLoginId(loginId).orElseThrow(()->new HwamokException(ExceptionCode.NOT_FOUND_USER));
        userRepository.delete(user);

    }


}
