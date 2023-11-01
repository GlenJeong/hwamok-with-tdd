package com.hwamok.notice.service;

import com.hwamok.core.exception.HwamokException;
import com.hwamok.core.exception.HwamokExceptionTest;
import com.hwamok.notice.domain.Notice;
import com.hwamok.notice.domain.NoticeRepository;
import org.assertj.core.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.hwamok.core.exception.ExceptionCode.*;
import static fixtures.NoticeFixture.createNotice;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@SpringBootTest(webEnvironment = RANDOM_PORT)
// 통합 테스트를 제공하는 가장 기본적인 테스트 애노테이션으로
// 애플리케이션이 실행 될 때의 설정을 임의로 바꿀수도 있고 여러 단위 테스트를 하나의 통합 테스트로 수행 할 수도 있다.
// 해당 애노테이션의 컴포넌트 스캔 범위는 Bean 전체이다. 즉 애플리케이션이 실행할 당시 스캔되는 범위와 동일하다.
// 그렇기에 최대한 실제와 유사한 환경에서 테스트를 할 수 있다는 장점이 있다.
// 하지만, 이 말은 반대로 애플리케이션의 모든 설정을 가져오기 때문에 애플리케이션의 범위가 넓을수록 테스트가 느려질 수 밖에 없고,
// 이는 단위테스트의 의미를 희석하기에 단위테스트에 적절하지는 않다.
// webEnvironment
// WebEnvironment.MOCK : 기본적으로 설정되는 기본 설정으로 내장 톰캣이 구동되지 않는다.
// WebEnvironment.RANDOM_PORT: 포트가 랜덤으로 지정되어 상용 앱에서 구동되는 것처럼 내장 톰캣이 구동된다.
// WebEnvironment.DEFINED_PORT: 정의된 포트로 내장톰캣이 구동된다.
// WebEnvironment.NONE: WebEnvironment.NONE으로 구동된다

class NoticeServiceImplTest {

    @Autowired// @SpringBootTest(webEnvironment = RANDOM_PORT) 사용해야 빨간줄이 사라진다. 그 이유는
    // @Autowired를 사용할 때, 해당 필드가 Spring 컨텍스트에서 주입될 것이라고 기대한다.
    // 그러나 이 컨텍스트는 @SpringBootTest에 의해 설정되므로 webEnvironment를 RANDOM_PORT로 설정하지 않으면
    // 랜덤 포트에서 실행되는 내장 톰캣을 활성화하지 않게 되며, 따라서 @Autowired 필드에 의존성을 주입할 수 없게 된다.
    // 이것이 @Autowired에 빨간 줄이 생기는 이유이다.
    private NoticeService noticeService;

    @Autowired
    private NoticeRepository noticeRepository;

    @Test
    void 공지사항_생성_성공() {
        Notice notice = noticeRepository.save(new Notice("제목", "본문"));

        Assertions.assertThat(notice.getId()).isNotNull();
        Assertions.assertThat(notice.getTitle()).isEqualTo("제목");
        Assertions.assertThat(notice.getContent()).isEqualTo("본문");
        Assertions.assertThat(notice.getCreatedAt()).isNotNull();
    }

    @Test
    void 공지사항_생성_실패_제목이_빈값() {
//        assertThatHwamokException(REQUIRED_PARAMETER)
//                .isThrownBy(()-> noticeRepository.save(new Notice("", "본문")));
        // 내가 원하는 커스텀 익셉션이 발생했는 지 정확히 확인할 수 있다.

        // Notice Entity에 아래 코드를 추가
        // HwamokException.validate(Strings.isNotBlank(title), ExceptionCode.REQUIRED_PARAMETER);


    }

    @Test
    void 공지사항_생성_실패_제목이_빈값_() {
//        Notice notice = noticeRepository.save(new Notice("", "본문"));
//        assertThat(notice.getId()).isNotNull();

        assertThatIllegalArgumentException().isThrownBy(()->noticeRepository.save(createNotice("", "본문")));
    }

    @Test
    void 공지사항_단건_조회_성공() {
        // 공지사항을 조회하고 싶음
        Notice notice = noticeRepository.save(createNotice());

        Notice foundNotice = noticeRepository.findById(notice.getId()).orElseThrow();

        Assertions.assertThat(foundNotice.getId()).isEqualTo(notice.getId());
    }

    @Test
    void 공지사항_단건_조회_실패_공지사항이_존재하지_않음() {
       Notice notice = noticeRepository.save(createNotice());

//        Notice foundNoticeId = noticeRepository.findById(-1L).orElseThrow();
//
//        assertThat(foundNoticeId.getId()).isEqualTo(notice.getId());
        HwamokExceptionTest.assertThatHwamokException(NOT_FOUND_NOTICE)
                .isThrownBy(() -> noticeRepository.findById(-1L).orElseThrow(() -> new HwamokException(NOT_FOUND_NOTICE)));
        // -1L 절대 존재하지 않는 값을 넣어서 실패의 조건을 만든다.
    }

    @Test
    void 공지사항_수정_성공() {
        Notice notice = noticeRepository.save(createNotice());

        Notice foundNotice = noticeRepository.findById(notice.getId()).orElseThrow();

        foundNotice = noticeService.update(foundNotice.getId(), "수정된 제목", "수정된 내용");

        assertThat(foundNotice.getTitle()).isEqualTo("수정된 제목");
        assertThat(foundNotice.getContent()).isEqualTo("수정된 내용");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 공지사항_수정_실패__제목이_빈값_혹은_null(String title) {
        Notice notice = noticeRepository.save(createNotice());

        Notice foundNotice = noticeRepository.findById(notice.getId()).orElseThrow();

        assertThatIllegalArgumentException().isThrownBy(() -> noticeService.update(foundNotice.getId(),title,"수정된 내용"));

    }

    @ParameterizedTest
    @NullAndEmptySource
    void 공지사항_수정_실패__내용이_빈값_혹은_null(String content) {
        Notice notice = noticeRepository.save(createNotice());

        Notice foundNotice = noticeRepository.findById(notice.getId()).orElseThrow();

        assertThatIllegalArgumentException().isThrownBy(() -> noticeService.update(foundNotice.getId(),"수정된 제목",content));
    }

    @Test
    void 공지사항_수정_실패_제목이_11글자_이상() {
        Notice notice = noticeRepository.save(createNotice());

        Notice foundNotice = noticeRepository.findById(notice.getId()).orElseThrow();

        assertThatIllegalArgumentException()
                .isThrownBy(() -> noticeService.update(foundNotice.getId(), "수정된제목수정된제목수", "수정된 본문"));
    }

    @Test
    void 공지사항_수정_실패_내용이_50글자_이상() {
        Notice notice = noticeRepository.save(createNotice());

        Notice foundNotice = noticeRepository.findById(notice.getId()).orElseThrow();

        assertThatIllegalArgumentException()
                .isThrownBy(() -> noticeService.update(foundNotice.getId(), "수정된 제목", "수정된본문수정된본문수정된본문수정된본문수정된본문수정된본문수정된본문수정된본문수정된본문수정된본문본"));
    }
}

// 공지사항 수정 실패를 성공하게 만들기