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

    @Test
    void 공지사항_생성_성공() {
        Notice notice = noticeService.create("123", "12345");

        Assertions.assertThat(notice.getId()).isNotNull();
//        Assertions.assertThat(notice.getTitle()).isEqualTo("123");
//        Assertions.assertThat(notice.getContent()).isEqualTo("1234");
//        Assertions.assertThat(notice.getCreatedAt()).isNotNull();
    }
}