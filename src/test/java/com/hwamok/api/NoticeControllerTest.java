package com.hwamok.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hwamok.api.dto.notice.NoticeCreateDto;
import com.hwamok.notice.domain.Notice;
import com.hwamok.notice.domain.NoticeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcResultHandlersDsl;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

// 시나리오 테스트
// API제공 - Endpoint --> localhost:8080/notice
// request -> c -> s -> r -> response
// 가짜 요청 -> MocMVC(가짜 요청을 해주는 라이브러리) - spring의 기본(공식? )
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc // mock 테스트시 필요함
class NoticeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NoticeRepository noticeRepository;

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
        mockMvc.perform(MockMvcRequestBuilders.post("/notice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isOk()); // 응답 200, 객체를 검증하는 곳, isOk 200, isCreated 201

//        Forwarded URL = null return "notice"; forward 방식
//        Redirected URL = null

        // 리포지토리에서 가져와서 있는 지 검사
        List<Notice> notices = noticeRepository.findAll();
        Notice notice = notices.stream().findFirst().orElseThrow();

        assertThat(notices.size()).isEqualTo(1);
        assertThat(notice.getTitle()).isEqualTo(request.getTitle());
        assertThat((notice.getContent())).isEqualTo(request.getContent());

    }
}