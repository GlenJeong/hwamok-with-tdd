package com.hwamok.api;

import com.epages.restdocs.apispec.ResourceDocumentation;
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goyounha11.docs.DocsUtil;
import com.hwamok.api.dto.auth.LoginDto;
import com.hwamok.api.dto.notice.NoticeCreateDto;
import com.hwamok.api.dto.notice.NoticeUpdateDto;
import com.hwamok.notice.domain.Notice;
import com.hwamok.notice.domain.NoticeRepository;
import com.hwamok.user.domain.User;
import com.hwamok.user.domain.UserRepository;
import fixtures.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static fixtures.NoticeFixture.createNotice;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// 시나리오 테스트
// API제공 - Endpoint --> localhost:8080/notice
// request -> c -> s -> r -> response
// 가짜 요청 -> MocMVC(가짜 요청을 해주는 라이브러리) - spring의 기본(공식? )
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc // mock 테스트시 필요함
    // Spring.test.mockmvc의 설정을 로드하면서 MockMvc의 의존성을 자동으로 주입
    // MockMvc 클래스는 REST API 테스트를 할 수 있는 클래스
    // @MockBean 테스트할 클래스에서 주입 받고 있는 객체에 대한 가짜 객체를 생성해주는 어노테이션
    // 해당 객체는 실제 행위를 하지 않음
    // given() 메서드를 활요하여 가짜 객체의 동작에 대해 정의하여 사용할 수 있음
@AutoConfigureRestDocs
@Transactional
class NoticeControllerTest {

    @Autowired
    private MockMvc mockMvc; // @AutoConfigureMockMvc => Spring.test.mockmvc의 설정을 로드하면서 MockMvc의 의존성을 자동으로 주입
    // MockMvc는 웹 어플리케이션을 애플리케이션 서버에 배포하지 않고 테스트용 MVC환경을 만들어 요청 및 전송, 응답기능을 제공해주는 유틸리티 클래스다.
    // 내가 컨트롤러 테스트를 하고싶을 때 실제 서버에 구현한 애플리케이션을 올리지 않고(실제 서블릿 컨테이너를 사용하지 않고)
    // 테스트용으로 시뮬레이션하여 MVC가 되도록 도와주는 클래스다
    // 실제 객체와 비슷하지만 테스트에 필요한 기능만 가지는 가짜 객체를 만들어서 애플리케이션 서버에 배포하지 않고도
    // 스프링 MVC 동작을 재현할 수 있는 클래스를 의미
    @Autowired
    private ObjectMapper objectMapper; // JSON Jackson 라이브러리 ObjectMapper 클래스
    // JSON 형식을 사용할 때, 응답들을 직렬화하고 요청들을 역직렬화 할 때 사용하는 기술
    // Jackson은 JSON 데이터 구조를 처리해주는 라이브러리
    // "키 :값" 쌍으로 이루어진 데이터 객체를 전달하기 위해 사람이 읽을 수 있는 텍스트를 사용하는 포맷
    // 자바 객체를 JSON 형식으로 변환
    // JavaScript Object Notation(JSON)은 이를 가능케 하는 데이터 교환 포맷
    // JSON은 파싱 또는 직렬화 없이도 JavaScript 프로그램에서 사용할 수 있다.
    // JSON은 JavaScript 객체 리터럴, 배열, 스칼라 데이터를 표현하는 텍스트 기반의 방식이다.
    // JSON은 텍스트 기반의 경량 언어이며, 추가적인 코드 작업 없이도 손쉽게 파싱이 가능한 데이터 형식
    // 파싱 : 어떤 페이지(문서, html 등)에서 내가 원하는 데이터를 특정 패턴이나 순서로 추출해 가공하는 것
    // JSON이 개발자들 사이에서 인기를 얻게 된 이유는 인간이 읽을 수 있는 문서로 이루어졌기 때문이다.
    // 게다가 코딩도 더 적게 필요하고, 처리 속도가 빠른, 경량 언어이기 때문이다.
    // https://www.oracle.com/kr/database/what-is-json/
    // https://escapefromcoding.tistory.com/341

    @Autowired
    private NoticeRepository noticeRepository;

    Notice notice;

    @BeforeEach
        // Test코드 메서드가 실행하기전에 반드시 이 메서드가 먼저 실행함
    // 수정할 때 샘플 데이터가 필요하기 때문에 Test코드 메서드가 실행하기 전에
    // 데이터를 만들어 놓는 다.
    void setUp() {
        notice = noticeRepository.save(createNotice());
    }

    @Test
    void 공지사항_생성_성공() throws Exception{
        // 응답에 대한 공통 메시지
        // code : xxxx
        // message : ~~~
        // data : object


        NoticeCreateDto.Request request = new NoticeCreateDto.Request("제목", "본문");
        // Common Response 생성된 것을 확인해 주는 정립된 메시지를 보내줌, 생성했는 지 성공을 알려주는 메시지
        // 응답은 따로 없지만
        // code : S000 이라는 임의의 성공 메시지를 보내줌, 그러면 성공했는 지 알 수 있다.
        // message : success
        // code : E000 이라는 임의의 실패 메시지를 보내줌
        // message :  필수 값 오류



        // Exception Handling
        // MockMvc를 생성한다.
        // MockMvc에게 요청에 대한 정보를 입력한다.
        // 요청에 대한 응답값을 Expect를 이용하여 테스트한다.
        // Expect가 모두 통과하면 테스트 통과
        // Expect가 1개라도 실패하면 테스트 실패
        mockMvc.perform(post("/notice")
                        // 해당 url로 요청을 한다.
                        .contentType(MediaType.APPLICATION_JSON)
                        // Json 타입으로 지정
                        .content(objectMapper.writeValueAsBytes(request)))
                        // 내용 등록, writeValueAsBytes(변환할 객체): Java 객체를 JSON 형식으로 변환
                        // Java 오브젝트로 부터 JSON을 만들고 이를 Byte 배열로 반환
                        .andDo(print())
                        // 응답값 print
                        .andExpect(status().isCreated())
                        // 응답 200, 객체를 검증하는 곳, isOk 200, isCreated 201
                        // 응답 status를 ok로 테스트
                        .andExpectAll(
                        jsonPath("code").value("S000"),
                        jsonPath("message").value("success")
                        );

////        Forwarded URL = null return "notice"; forward 방식
////        Redirected URL = null
//
//        // 리포지토리에서 가져와서 있는 지 검사
        List<Notice> notices = noticeRepository.findAll();
        Notice notice = notices.stream().findFirst().orElseThrow(() -> new RuntimeException("not found notice"));
//        // Optional의 의미는 null or not null 2가지만 가지고 있음
//        // Optional뒤에 다음에 메서드 체이닝으로 orElseThrow가 null이면 throw한다. (Exception)
//        // orElseThrow는 람다식을 통해서 익셉션을 지정할 수 있음
//
//
//
//        assertThat(notices.size()).isEqualTo(1);
        System.out.println("notice.getTitle() = " + notice.getTitle());
        System.out.println("request.getTitle() = " + request.getTitle());
        assertThat(notice.getTitle()).isEqualTo(request.getTitle());
        assertThat((notice.getContent())).isEqualTo(request.getContent());

    }

    @Test
    void 공지사항_수정_성공() throws Exception {

        NoticeCreateDto.Request request = new NoticeCreateDto.Request("수정된 제목", "수정된 본문");

        mockMvc.perform(post("/notice/{id}", notice.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("code").value("S000"),
                        jsonPath("message").value("success")
                );
    }


    @ParameterizedTest
    @NullAndEmptySource
    void 공지사항_수정_실패__제목이_빈값_null(String title) throws Exception {

        NoticeUpdateDto.Request request = new NoticeUpdateDto.Request(title, "수정된 본문");

        mockMvc.perform(post("/notice/{id}", notice.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("code").value("E001"),
                        jsonPath("message").value("필수 값이 누락되었습니다.")
                );

    }

    @ParameterizedTest
    @NullAndEmptySource
    void 공지사항_수정_실패__내용이_빈값_null(String content) throws Exception {

        NoticeUpdateDto.Request request = new NoticeUpdateDto.Request("수정된 제목", content);

        mockMvc.perform(post("/notice/{id}", notice.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("code").value("E001"),
                        jsonPath("message").value("필수 값이 누락되었습니다.")
                );
    }

    @Test
    void 공지사항_수정_실패_존재하지_않는_공지사항() throws Exception {
        NoticeUpdateDto.Request request = new NoticeUpdateDto.Request("수정된 제목", "수정된 본문");

        mockMvc.perform(post("/notice/{id}", -1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("code").value("E003"),
                        jsonPath("message").value("공지사항을 찾을 수가 없습니다.")
                );

    }
}