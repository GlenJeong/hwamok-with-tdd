package com.hwamok.user.service;


import com.hwamok.notice.domain.Notice;
import com.hwamok.user.domain.User;

import java.text.ParseException;
import java.util.Date;
import java.util.Calendar;

public interface UserService {

    User create(String loginId, String password, String email, String nickname, String name, String userStatus, String birthday) throws Exception;

    User getUser(String loginId);

    User updateProfile(long id, String loginId, String password, String email, String nickname, String name, String userStatus, String birthday) throws Exception;

    void withdraw(String loginId);
}
