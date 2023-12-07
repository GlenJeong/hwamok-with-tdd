package com.hwamok.api;

import com.epages.restdocs.apispec.ResourceDocumentation;
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goyounha11.docs.DocsUtil;
import com.hwamok.api.dto.auth.LoginDto;
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

import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;


    @Test
    void 로그인_성공() throws Exception {
        // 3가지 방법의 문서 자동화
        // 1. Swagger
        // 장점
        // -> Annotation을 기반으로 하는 문서화 방법, 사용이 간편화
        // -> 이쁨, API 문서에서 실제 응답을 보내고 응답을 받아 볼 수 있음
        // -> 빠른 문서화가 가능하다.
        // 단점
        // -> 테스트 코드가 없어도 문서화가 가능, 테스트 코드가 없어서도 문서화가 된다는 것은 테스트를 하지 않았다는 것.
        // -> 문서화 관련된 Annotation들이 Application(Controller에) Code에 작성됨, controller에 대한 책임과 문서화에 대한 책임이 생김



        // 2. RestDocs
        // 장점
        // -> 테스트가 통과하지 않으면 문서도 안 만들어진다, 테스트를 강제화함
        // -> Application Code를 침범하지 않는 다.
        // -> Snippet(조각)라는 것을 기반으로 하기 때문에 만들어진 문서는 실제 요청과 응답의 값을 문서화 한다.

        // 단점
        // -> 못 생겼다.
        // -> 테스트를 바로 해 볼 수 없고 curl 복사해서 postman 같은 곳에서 테스트 해 볼 수 있다. 불친절함
        // -> 문서화 시간이 오래 걸린다.

        // 3. epages, openApi3 + SwaggerUI
        // Swagger의 장점 + RestDocs의 장점을 모아다가 문서화함
        // 장점
        // -> 테스트가 통과하지 않으면 문서도 안 만들어진다, 테스트를 강제화함
        // -> Application Code를 침범하지 않는 다.
        // -> 이쁨, API 문서에서 실제 응답을 보내고 응답을 받아 볼 수 있음
        // -> Annotation을 기반으로 하는 문서화 방법, 사용이 간편화
        // -> 빠른 문서화가 가능하다.
        // -> Snippet(조각)라는 것을 기반으로 하기 때문에 만들어진 문서는 실제 요청과 응답의 값을 문서화 한다.



        User user = userRepository.save(userRepository.save(UserFixture.createUser("jiy88226", passwordEncoder.encode("q1w2e3r4t5"))));

        LoginDto.Request dto = new LoginDto.Request("jiy88226","q1w2e3r4t5");

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(dto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("code").value("S000"),
                        jsonPath("message").value("success"),
                        jsonPath("data.accessToken").isNotEmpty(),
                        jsonPath("data.refreshToken").isNotEmpty()
                );
//                .andDo(document("/auth/login",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint())
//                ));
    }

    @Test
    void 자동화_테스트() throws Exception {
        userRepository.save(userRepository.save(UserFixture.createUser("jyb0226", passwordEncoder.encode("a1s2d3f4g5"))));

        LoginDto.Request dto = new LoginDto.Request("jyb0226","a1s2d3f4g5");

        ResultActions resultActions = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(dto)));
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("code").value("S000"),
                        jsonPath("message").value("success"),
                        jsonPath("data.accessToken").isNotEmpty(),
                        jsonPath("data.refreshToken").isNotEmpty()
                );
    }
}