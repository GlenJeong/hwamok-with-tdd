package com.hwamok.user.service;

import com.hwamok.notice.domain.NoticeRepository;
import com.hwamok.notice.service.NoticeService;
import com.hwamok.user.domain.User;
import com.hwamok.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class UserServiceImplTest {
    @Autowired
    private NoticeService noticeService;

    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void 사용자_생성_성공() throws Exception {
        Calendar date2 = Calendar.getInstance();
        date2.set(2023, 6, 20);// 100일 미만 Active
        long loginDate = date2.getTimeInMillis()/1000;

       User user = userRepository.save(new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", "정인범11", loginDate, "1988-02-26"));

    }
}