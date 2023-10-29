package com.hwamok.user.service;


import com.hwamok.user.domain.User;

import java.text.ParseException;
import java.util.Date;
import java.util.Calendar;

public interface UserService {

    User create(String loginId, String password, String email, String nickname, String name, long loginDate, String birthday) throws ParseException;
}
