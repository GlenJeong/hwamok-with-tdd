package com.hwamok.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hwamok.api.dto.user.UserCreateDto;
import com.hwamok.api.dto.user.UserUpdateDto;
import com.hwamok.user.domain.User;
import com.hwamok.user.domain.UserRepository;
import com.hwamok.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Application Context 완전하게 Start 시키지 않고 web layer를 테스트 하고 싶을 때 @WebMvcTest를 사용하는 것을 고려한다.
// present Layer 관련 컴포넌트만 스캔을 한다.
// Service, Repository dependency가 필요한 경우에는 @MockBean으로 주입받아 테스트를 진행 한다.
// @SpringBootTest의 경우 모든 빈을 로드하기 때문에 테스트 구동 시간이 오래 걸리고, 테스트 단위가 크기 때문에 디버깅이 어려울 수 있다.
// Controller 레이어만 슬라이스 테스트 하고 싶을 때에는 @WebMvcTest를 쓰는게 유용하다.

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
// @Transactional 어노테이션은 테스트 메서드에 적용되면 해당 메서드가 실행될 때 트랜잭션을 시작하고,
// 메서드가 완료될 때 롤백시킵니다. 이것은 테스트 간에 데이터를 공유하지 않고 독립적으로 실행되게끔 하는데 도움이 됩니다.
// 롤백은 데이터베이스를 변경하지 않고 테스트를 진행할 수 있도록 해줍니다.
// @Test 바로 위에 @Commit을 추가하면 실제 DB에 반영한다.
// @Commit 어노테이션은 테스트 메서드에 적용되면 해당 메서드가 실행될 때 트랜잭션을 커밋하도록 합니다.
// 이 경우에는 트랜잭션을 롤백시키지 않고 실제로 데이터베이스에 변경사항을 반영합니다.
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
    MockMultipartFile mockFile;

    @BeforeEach
    void setUp() throws IOException {
        Path path = Paths.get("imageProfile/winter background.png");
        byte[] fileContent = Files.readAllBytes(path);

        mockFile = new MockMultipartFile(
                "profilePicture",
                "winter background.png",
                "image/png",
                fileContent
        );
    }

    @Test
    void 회원_가입_성공() throws Exception {

        UserCreateDto.Request request = new UserCreateDto.Request("jyb1624", "q1w2e3r4t5", "jyb1234@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26");

        mockMvc.perform(multipart("/user")
                        .file(mockFile)
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
    void 회원_단건_정보_조회() throws Exception {
        User user = userRepository.save(new User("jyb1624", "q1w2e3r4t5", "jyb124@test.com", "Glenn", "정인범", "ACTIVATED", "originalFileName", "savedFileName","1988-02-26"));


        mockMvc.perform(get("/user/userOne/{id}", user.getId()))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("code").value("S000"),
                        jsonPath("message").value("success")
                );

    }

    @Test
    void 회원_수정_성공() throws Exception {

        UserUpdateDto.Request request = new UserUpdateDto.Request("jyb005222", "q1w2e3r4t5", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26");

        User user = userRepository.save(new User("jyb1624", "q1w2e3r4t5", "jyb114@test.com", "Glenn", "정인범", "ACTIVATED", "originalFileName", "savedFileName","1988-02-26"));

        mockMvc.perform(multipart("/user/updateProfile/{id}", user.getId())
                        .file(mockFile)
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

    @Test
    void 회원_탈퇴_성공() throws Exception {
//        String name = mockFile.getOriginalFilename().substring(mockFile.getOriginalFilename().lastIndexOf("."));
//        String savedFileName = "F_" + System.currentTimeMillis() + name;
//
//        User user = userRepository.save(new User("jyb0226", "q1w2e3r4t5", "jyb0226@test.com", "Glenn", "정인범", "ACTIVATED", mockFile.getOriginalFilename(), savedFileName,"1988-02-26"));

        User user = userService.create("jyb0209", "1234", "jyb0209@test.com", "Jane", "전현정", "ACTIVATED", "1992-01-20", mockFile);

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
                );

    }
}