package com.hwamok.api;

import com.epages.restdocs.apispec.ResourceDocumentation;
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goyounha11.docs.DocsUtil;
import com.hwamok.api.dto.auth.LoginDto;
import com.hwamok.api.dto.user.UserCreateDto;
import com.hwamok.api.dto.user.UserUpdateDto;
import com.hwamok.user.domain.User;
import com.hwamok.user.domain.UserRepository;
import fixtures.UserFixture;
import org.junit.jupiter.api.Test;
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

import java.sql.Time;
import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static java.time.LocalTime.now;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
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
@AutoConfigureRestDocs
@Transactional
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;




    @Test
    void 회원_가입_성공() throws Exception {

        UserCreateDto.Request request = new UserCreateDto.Request("jyb1624", "q1w2e3r4t5", "jyb1234@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26");

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
                )
                .andDo(document("/user",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        ResourceDocumentation.resource(
                                new ResourceSnippetParametersBuilder()
                                        .tag("User")
                                        .description("회원 생성 API")
                                        .requestFields(
                                                List.of(
                                                        PayloadDocumentation.fieldWithPath("loginId").type(JsonFieldType.STRING).description("jyb1624"),
                                                        PayloadDocumentation.fieldWithPath("password").type(JsonFieldType.STRING).description("q1w2e3r4t5"),
                                                        PayloadDocumentation.fieldWithPath("email").type(JsonFieldType.STRING).description("jyb1234@test.com"),
                                                        PayloadDocumentation.fieldWithPath("nickname").type(JsonFieldType.STRING).description("Glenn"),
                                                        PayloadDocumentation.fieldWithPath("name").type(JsonFieldType.STRING).description("정인범"),
                                                        PayloadDocumentation.fieldWithPath("userStatus").type(JsonFieldType.STRING).description("ACTIVATED"),
                                                        PayloadDocumentation.fieldWithPath("birthday").type(JsonFieldType.STRING).description("1988-02-26")
                                                )
                                        )
                                        .responseFields(
                                                List.of(
                                                        PayloadDocumentation.fieldWithPath("code").type(JsonFieldType.STRING).description("S000"),
                                                        PayloadDocumentation.fieldWithPath("message").type(JsonFieldType.STRING).description("success"),
                                                        PayloadDocumentation.fieldWithPath("data").ignored()
                                                )
                                        )
                                        .requestSchema(Schema.schema("UserCreateDto.Request"))
                                        .responseSchema(Schema.schema("UserCreateDto.Response"))
                                        .build()
                        )
                ));
    }

    @Test
    void 회원_단건_정보_조회() throws Exception {
        User user = userRepository.save(new User("jyb1624", "q1w2e3r4t5", "jyb124@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26"));


        mockMvc.perform(get("/user/userOne/{id}", user.getId()))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("code").value("S000"),
                        jsonPath("message").value("success")
                )
                .andDo(document("/user/userOne/{id}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        ResourceDocumentation.resource(
                                new ResourceSnippetParametersBuilder()
                                        .tag("User")
                                        .description("회원 정보 조회 API")
                                        .responseFields(
                                                List.of(
                                                        PayloadDocumentation.fieldWithPath("code").type(JsonFieldType.STRING).description("S000"),
                                                        PayloadDocumentation.fieldWithPath("message").type(JsonFieldType.STRING).description("success"),
                                                        PayloadDocumentation.fieldWithPath("data.id").type(JsonFieldType.NUMBER).description(1),
                                                        PayloadDocumentation.fieldWithPath("data.loginId").type(JsonFieldType.STRING).description("jyb1624"),
                                                        PayloadDocumentation.fieldWithPath("data.password").type(JsonFieldType.STRING).description("q1w2e3r4t5"),
                                                        PayloadDocumentation.fieldWithPath("data.email").type(JsonFieldType.STRING).description("jyb124@test.com"),
                                                        PayloadDocumentation.fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("Glenn"),
                                                        PayloadDocumentation.fieldWithPath("data.name").type(JsonFieldType.STRING).description("정인범"),
                                                        PayloadDocumentation.fieldWithPath("data.userStatus").type(JsonFieldType.STRING).description("ACTIVATED"),
                                                        PayloadDocumentation.fieldWithPath("data.birthday").type(JsonFieldType.STRING).description("1988-02-26"),
                                                        PayloadDocumentation.fieldWithPath("data.updateAt").type(JsonFieldType.STRING).description("The timestamp when the data was update"),
                                                        PayloadDocumentation.fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("The timestamp when the data was created")
                                                        )
                                        )
                                        .requestSchema(Schema.schema("UserCreateDto.Request"))
                                        .responseSchema(Schema.schema("UserCreateDto.Response"))
                                        .build()
                        )
                ));

    }

    @Test
    void 회원_수정_성공() throws Exception {
        User user = userRepository.save(new User("jyb1624", "q1w2e3r4t5", "jyb114@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26"));

        UserUpdateDto.Request request = new UserUpdateDto.Request("jyb005222", "q1w2e3r4t5", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26");

        mockMvc.perform(patch("/user/updateProfile/{id}", user.getId())
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
                )
                .andDo(document("/user/updateProfile/{id}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        ResourceDocumentation.resource(
                                new ResourceSnippetParametersBuilder()
                                        .tag("User")
                                        .description("회원 수정 API")
                                        .requestFields(
                                                List.of(
                                                        PayloadDocumentation.fieldWithPath("loginId").type(JsonFieldType.STRING).description("jyb005222"),
                                                        PayloadDocumentation.fieldWithPath("password").type(JsonFieldType.STRING).description("q1w2e3r4t5"),
                                                        PayloadDocumentation.fieldWithPath("email").type(JsonFieldType.STRING).description("jyb1624@test.com"),
                                                        PayloadDocumentation.fieldWithPath("nickname").type(JsonFieldType.STRING).description("Glenn"),
                                                        PayloadDocumentation.fieldWithPath("name").type(JsonFieldType.STRING).description("정인범"),
                                                        PayloadDocumentation.fieldWithPath("userStatus").type(JsonFieldType.STRING).description("ACTIVATED"),
                                                        PayloadDocumentation.fieldWithPath("birthday").type(JsonFieldType.STRING).description("1988-02-26")
                                                )
                                        )
                                        .responseFields(
                                                List.of(
                                                        PayloadDocumentation.fieldWithPath("code").type(JsonFieldType.STRING).description("S000"),
                                                        PayloadDocumentation.fieldWithPath("message").type(JsonFieldType.STRING).description("success"),
                                                        PayloadDocumentation.fieldWithPath("data").ignored()
                                                )
                                        )
                                        .requestSchema(Schema.schema("UserUpdateDto.Request"))
                                        .responseSchema(Schema.schema("UserUpdateDto.Response"))
                                        .build()
                        )
                ));

    }

    @Test
    void 회원_탈퇴_성공() throws Exception {
        User user = userRepository.save(new User("jyb1624", "q1w2e3r4t5", "jyb162324@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26"));

        mockMvc.perform(delete("/user/withdraw/{id}", user.getId()))
                // 내용 등록, writeValueAsBytes(변환할 객체): Java 객체를 JSON 형식으로 변환
                // Java 오브젝트로 부터 JSON을 만들고 이를 Byte 배열로 반환
                // 응답값 print
                .andExpect(status().isOk())
                // 응답 200, 객체를 검증하는 곳, isOk 200, isCreated 201
                // 응답 status를 ok로 테스트
                .andExpectAll(
                        jsonPath("code").value("S000"),
                        jsonPath("message").value("success")
                )
                .andDo(document("/user/withdraw/{id}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        ResourceDocumentation.resource(
                                new ResourceSnippetParametersBuilder()
                                        .tag("User")
                                        .description("회원 탈퇴 API")
                                        .responseFields(
                                                List.of(
                                                        PayloadDocumentation.fieldWithPath("code").type(JsonFieldType.STRING).description("S000"),
                                                        PayloadDocumentation.fieldWithPath("message").type(JsonFieldType.STRING).description("success"),
                                                        PayloadDocumentation.fieldWithPath("data").ignored()
                                                )
                                        )
                                        .requestSchema(Schema.schema("UserUpdateDto.Request"))
                                        .responseSchema(Schema.schema("UserUpdateDto.Response"))
                                        .build()
                        )
                ));

    }
}