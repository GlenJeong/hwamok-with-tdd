package com.hwamok.notice.domain;

import org.aspectj.weaver.ast.Not;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.time.Instant;

import static com.hwamok.fixtures.NoticeFixture.createNotice;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class NoticeTest {
    // 알트 인설틑 테스트 코딩 자동 완성
    // 함수명도 영어다
    // 함수명은 한글도 괜찮다
    // 띄어쓰기는 _ 사용
    @Test
    void 공지사항_생성_성공() {
        // Fixture 만들기, 반복되는 것을 메서드로 만들어서 사용하는 것.
        // Notice notice = new Notice("제목", "본문");
        Notice notice = new Notice("제목", "본문"); // createNotice와 동일



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
                isThrownBy(()->createNotice(title, "본문"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 공지사항_생성_실패__내용이_빈값_혹은_null(String content) { // 익셥션 발생하는 테스트 코딩, 빈값이랑 null 테스트
        assertThatIllegalArgumentException().
                isThrownBy(()->createNotice("제목", content));
    }

    @Test
    void 공지사항_수정_성공() {
        Notice notice = createNotice();

        notice.update("수정된 제목", "수정된 본문");

        assertThat(notice.getTitle()).isEqualTo("수정된 제목");
        assertThat(notice.getContent()).isEqualTo("수정된 본문");

    }

    @ParameterizedTest
    @NullAndEmptySource
    void 공지사항_수정_실패_제목이_빈값_null(String title) {
        Notice notice = createNotice();

        assertThatIllegalArgumentException()
                .isThrownBy(() -> notice.update(title, "수정된 본문"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 공지사항_수정_실패_내용이_빈값_null(String content) {
        Notice notice = createNotice();

        assertThatIllegalArgumentException()
                .isThrownBy(() -> notice.update("수정된 제목", content));
    }

    @Test
    void 공지사항_수정_실패_제목이_11글자_이상() {
        Notice notice = createNotice();

        assertThatIllegalArgumentException()
                .isThrownBy(() -> notice.update("제목제목제목제목제목제", "수정된 본문"));
    }

    @Test
    void 공지사항_수정_실패_내용이_50글자_이상() {
        Notice notice = createNotice();

        assertThatIllegalArgumentException()
                .isThrownBy(() -> notice.update("수정된 제목", "수정된본문수정된본문수정된본문수정된본문수정된본문수정된본문수정된본문수정된본문수정된본문수정된본문본"));
    }
}