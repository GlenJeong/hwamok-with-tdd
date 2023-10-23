package com.hwamok.notice.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest // Jpa에 필요한 bean들을 사용 할 수 있다. 우리는 JpaRepository를 사용, 톰캣이 구동되지 않아 더 빠름
// Transactional를 포함하고 있음
@AutoConfigureTestDatabase(replace = NONE) // Test DB를 구성할 때 유용함, 테스트 시 DB를 테스트 DB로 대체 할 수 있다.
        // Replace: 자동 구성된 test DB 가 존재하는 DataSource bean 을 대체할 것인지 아닌지에 대해 제어한다.
        // NONE: DataSource 를 대체하지 않음
        //replace=AutoConfigureTestDatabase.Replace가 디폴트로 설정되어 있어, 설정해놓은 DB가 아닌 in-memory DB를 활용해서 테스트가 실행된다.
        //EmbeddedDatabaseConnection 클래스를 보면 H2, DERBY, HSQL, HSQLDB 중 사용 가능한 in-memory DB에 자동으로 컨넥션을 설정하는 것을 확인할 수 있다.
        //replace=AutoConfigureTestDatabase.NONE으로 값을 덮어 씌우면 설정해놓은 DB를 테스트에 사용할 수 있다.
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
        // 1. Repository를 IOC 컨테이너에서 주입 받음
        // 2. 엔티티 생성 --> 자바코드에서 에러
        // 3. Repository save --> Data...Exception
        // DB는 정말 비싼 자원
        // 한번 insert -> 요청 1번 connection 비용

        // 예상하지 못한 에러 --> 익셉션을 핸들링해서 모니터링 구축, 로그를 남겨서 ==> 모니터링 시스템 나중에 구축해봄
        // 유지보수의 영역에 들어감
        // 익셉션을 핸들링만 잘해도 실력 인정 받음

//        Notice notice =
//                noticeRepository.save(new Notice("제목제목제목제목제목제", "본문"));
//
//        assertThat(notice.getId()).isNotNull();

           Assertions.assertThatIllegalArgumentException()
                    .isThrownBy(()-> noticeRepository.save(new Notice("제목제목제목제목제목제", "본문")));

    }

    //    assertThatExceptionOfType(DataIntegrityViolationException.class)
    //            .isThrownBy(()-> noticeRepository.save(new Notice("제목제목제목제목제목제", "본문")));

    //   Assertions.assertThatIllegalArgumentException()
    //            .isThrownBy(()-> noticeRepository.save(new Notice("제목제목제목제목제목제", "본문")));

    // content 50글자로 테스트하기
    // 싱클홀 패턴
    // controller -> service -> repository => 계층 코드 아키텍쳐
    // 도메인으로 확장되어 가는 코드 => 클린 코드 아키텍쳐
}