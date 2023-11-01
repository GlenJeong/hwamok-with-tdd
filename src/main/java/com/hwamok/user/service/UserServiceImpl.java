package com.hwamok.user.service;

import com.hwamok.core.exception.ExceptionCode;
import com.hwamok.core.exception.HwamokException;
import com.hwamok.notice.domain.Notice;
import com.hwamok.notice.domain.NoticeRepository;
import com.hwamok.user.domain.User;
import com.hwamok.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.hwamok.core.exception.ExceptionCode.NOT_FOUND_NOTICE;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final NoticeRepository noticeRepository; //@RequiredArgsConstructor 추가해야 빨간줄 사라짐


    @Override
    public User create(String loginId, String password, String email, String nickname, String name, String userStatus, String birthday) throws Exception {
        System.out.println(" create loginId = " + loginId);
        return userRepository.save(new User(loginId, passwordEncoder.encode(password), email, nickname, name, userStatus, birthday));
    }


    @Override
    public User getUser(long id) {
        return userRepository.findById(id).orElseThrow(()-> new HwamokException(ExceptionCode.NOT_FOUND_USER));
    }

    @Override
    public User updateProfile(String loginId, String password, String email, String nickname, String name, String userStatus, String birthday) throws Exception {
        System.out.println(" updateProfile loginId = ::::::::::::::::" + loginId);
        User user = userRepository.findByLoginId(loginId).orElseThrow(()-> new HwamokException(ExceptionCode.NOT_FOUND_USER));
        System.out.println(" updateProfile user.getLoginId() = " + user.getLoginId());
        userRepository.save(new User(user.getLoginId(), passwordEncoder.encode(password), email, nickname, name, userStatus, birthday));
        return user;
    }

    @Override
    public User updateProfile(long id, String loginId, String password, String email, String nickname, String name, String userStatus, String birthday) throws Exception {
        User user = userRepository.findById(id).orElseThrow(()-> new HwamokException(ExceptionCode.NOT_FOUND_USER));
        user.updateUser(loginId, passwordEncoder.encode(password), email, nickname, name, userStatus, birthday);
        return user;
    }

    @Override
    public void withdraw(long id) throws Exception {
        User user = userRepository.findById(id).orElseThrow(()->new HwamokException(ExceptionCode.NOT_FOUND_USER));
        user.withdraw();
        userRepository.save(user);

    }


}
