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
    public User create(String loginId, String password, String email, String nickname, String name, long loginDate, String birthday) throws ParseException {
        return userRepository.save(new User(loginId, password, email, nickname, name, loginDate, birthday));
    }
}
