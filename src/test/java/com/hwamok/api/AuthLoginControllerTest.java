package com.hwamok.api;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceDocumentation;
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
class AuthLoginControllerTest {

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
        User user = userRepository.save(UserFixture.createUser("jiy88226", passwordEncoder.encode("q1w2e3r4t5")));

        LoginDto.Request dto = new LoginDto.Request(user.getLoginId(), "q1w2e3r4t5");

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
                )
                .andDo(MockMvcRestDocumentationWrapper.document("유저로그인 API",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        ResourceDocumentation.resource(
                                new ResourceSnippetParametersBuilder()
                                        .tag("Auth")
                                        .requestFields(
                                                List.of(
                                                        PayloadDocumentation.fieldWithPath("loginId").type(JsonFieldType.STRING).description("jiy88226"),
                                                        PayloadDocumentation.fieldWithPath("password").type(JsonFieldType.STRING).description("q1w2e3r4t5")
                                                )
                                        )
                                        .responseFields(
                                                List.of(
                                                        PayloadDocumentation.fieldWithPath("code").type(JsonFieldType.STRING).description("S000"),
                                                        PayloadDocumentation.fieldWithPath("message").type(JsonFieldType.STRING).description("success"),
                                                        PayloadDocumentation.fieldWithPath("data.accessToken").type(JsonFieldType.STRING).description("access Token"),
                                                        PayloadDocumentation.fieldWithPath("data.refreshToken").type(JsonFieldType.STRING).description("refresh Token")
                                                )
                                        )
                                        .requestSchema(Schema.schema("LoginDto.Request"))
                                        .responseSchema(Schema.schema("LoginDto.Response"))
                                        .build()
                        )
                ));
    }
}