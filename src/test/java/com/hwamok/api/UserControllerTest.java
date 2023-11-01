package com.hwamok.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hwamok.api.dto.user.UserCreateDto;
import com.hwamok.notice.domain.NoticeRepository;
import com.hwamok.user.domain.User;
import com.hwamok.user.domain.UserRepository;
import com.hwamok.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Application Context 완전하게 Start 시키지 않고 web layer를 테스트 하고 싶을 때 @WebMvcTest를 사용하는 것을 고려한다. Present Layer 관련 컴포넌트만 스캔을 한다.
//
//Service, Repository dependency가 필요한 경우에는 @MockBean으로 주입받아 테스트를 진행 한다.
//
//@SpringBootTest의 경우 모든 빈을 로드하기 때문에 테스트 구동 시간이 오래 걸리고, 테스트 단위가 크기 때문에 디버깅이 어려울 수 있다. Controller 레이어만 슬라이스 테스트 하고 싶을 때에는 @WebMvcTest를 쓰는게 유용하다.

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @Test
    void PwdEnc() {
        String pwd ="1234";
        String encodePwd = passwordEncoder.encode(pwd);
        System.out.println("encodePwd = " + encodePwd);

        Assertions.assertThat(encodePwd).isNotNull();
    }

    @Test
    void 회원_가입_성공() throws Exception {

        UserCreateDto.Request request = new UserCreateDto.Request("jyb1624", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26");

        String pwd = request.getPassword();
        System.out.println("request.getPassword() = " + request.getPassword());
        String encodePwd = passwordEncoder.encode(pwd);
        System.out.println("encodePwd = " + encodePwd);

        mockMvc.perform(post("/user")
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
    }

    @Test
    void 회원_탈퇴_성공() throws Exception {

        UserCreateDto.Request request = new UserCreateDto.Request("jyb1624", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26");

        mockMvc.perform(get("/user/withdraw")
                        // 해당 url로 요청을 한다.
                        .contentType(MediaType.APPLICATION_JSON)
                        // Json 타입으로 지정
                        .content(objectMapper.writeValueAsBytes(request)))
                // 내용 등록, writeValueAsBytes(변환할 객체): Java 객체를 JSON 형식으로 변환
                // Java 오브젝트로 부터 JSON을 만들고 이를 Byte 배열로 반환
                .andDo(print())
                // 응답값 print
                .andExpect(status().isOk())
                // 응답 200, 객체를 검증하는 곳, isOk 200, isCreated 201
                // 응답 status를 ok로 테스트
                .andExpectAll(
                        jsonPath("code").value("S000"),
                        jsonPath("message").value("success")
                );

    }
}