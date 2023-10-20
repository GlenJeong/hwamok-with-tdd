package com.hwamok.notice.service;

import com.hwamok.notice.domain.Notice;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.assertj.core.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class NoticeServiceImplTest {

    @Autowired
    private NoticeService noticeService;

    @Test
    void 공지사항_생성_성공() {
        Notice notice = noticeService.create("123", "12345");

        Assertions.assertThat(notice.getId()).isNotNull();
//        Assertions.assertThat(notice.getTitle()).isEqualTo("123");
//        Assertions.assertThat(notice.getContent()).isEqualTo("1234");
//        Assertions.assertThat(notice.getCreatedAt()).isNotNull();
    }
}