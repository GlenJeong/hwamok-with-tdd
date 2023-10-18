package com.hwamok.notice.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest // Jpa에 필요한 bean들을 사용 할 수 있다. 우리는 JpaRepository를 사용, 톰캣이 구동되지 않아 더 빠름
@AutoConfigureTestDatabase(replace = NONE)
class NoticeRepositoryTest {
    @Autowired
    private NoticeRepository noticeRepository;
    @Test
    void 공지사항_저장_성공() {
       Notice notice =
               noticeRepository.save(new Notice("제목", "본문"));

        Assertions.assertThat(notice.getId()).isNotNull();
    }

    @Test
    void 공지사항_저장_실패__제목이_11글자_이상() {
        Notice notice =
                noticeRepository.save(new Notice("제목제목제목제목제목제", "본문"));

        Assertions.assertThat(notice.getId()).isNotNull();
    }
    
    // content 50글자로 테스트하기
    // 싱클홀 패턴
    // controller -> service -> repository => 계층 코드 아키텍쳐
    // 도메인으로 확장되어 가는 코드 => 클린 코드 아키텍쳐
}