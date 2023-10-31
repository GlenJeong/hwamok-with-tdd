package com.hwamok.user.service;

import com.hwamok.user.domain.User;
import com.hwamok.user.domain.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    public final UserRepository userRepository;


    @Override
    public User create(String loginId, String password, String email, String nickname, String name, String userStatus, String birthday, String accountActive) throws ParseException {
        return userRepository.save(new User(loginId, password, email, nickname, name, userStatus, birthday, accountActive));
    }

    @Override
    public User getUser(String loginId) {
        return userRepository.findByLoginId(loginId).orElseThrow(()-> new IllegalArgumentException());
    }

    @Override
    public User updateProfile(long id, String loginId, String password, String email, String nickname, String name, String userStatus, String birthday, String accountActive) throws ParseException {
        User user = userRepository.findById(id).orElseThrow(()-> new IllegalArgumentException());
        user.updateUser(loginId, password, email, nickname, name, userStatus, birthday, accountActive);
        return user;
    }


}
