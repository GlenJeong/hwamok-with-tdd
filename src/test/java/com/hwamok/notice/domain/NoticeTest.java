package com.hwamok.notice.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.time.Instant;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class NoticeTest {
    // 알트 인설틑 테스트 코딩 자동 완성
    // 함수명도 영어다
    // 함수명은 한글도 괜찮다
    // 띄어쓰기는 _ 사용
    @Test
    void 공지사항_생성_성공() {
        Notice notice = new Notice("제목", "본문");

        // 내가 나를 못 믿기 때문에 확인하고 싶어서
        // System.out.println("notice.getContent() = " + notice.getContent());
        // System.out.println("notice.getTitle() = " + notice.getTitle());


        assertThat(notice.getId()).isNull();
        // Assertions.assertThat(검증을 원하는 값).isEqualTo(기대값)
        assertThat(notice.getTitle()).isEqualTo("제목");
        assertThat(notice.getContent()).isEqualTo("본문");
        assertThat(notice.getCreatedAt()).isNotNull();
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 공지사항_생성_실패__제목이_빈값_혹은_null(String title) { // 익셥션 발생하는 테스트 코딩, 빈값이랑 null 테스트
        assertThatIllegalArgumentException().
                isThrownBy(()->new Notice(title, "본문"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 공지사항_생성_실패__내용이_빈값_혹은_null(String content) { // 익셥션 발생하는 테스트 코딩, 빈값이랑 null 테스트
        assertThatIllegalArgumentException().
                isThrownBy(()->new Notice("제목", content));
    }

}