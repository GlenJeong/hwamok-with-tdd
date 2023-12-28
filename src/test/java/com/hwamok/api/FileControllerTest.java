package com.hwamok.api;

import com.epages.restdocs.apispec.ResourceDocumentation;
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder;
import com.epages.restdocs.apispec.Schema;
import com.hwamok.core.integreation.aws.S3Service;
import com.hwamok.file.domain.File;
import com.hwamok.file.domain.FileRepository;
import com.hwamok.file.service.FileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FileService fileService;

    @Test
    void 파일_생성_성공() throws Exception {
        // MockMultipartFile 생성
        MockMultipartFile mockFile = new MockMultipartFile(
                "imageProfile", // MultipartFile의 필드명과 일치해야 함
                "imageProfile.jpg",   // 파일 이름
                "image/jpeg",    // 파일 타입
                "imageProfile".getBytes(StandardCharsets.UTF_8) // 파일 내용
        );

        mockMvc.perform(multipart("/file")
                        .file(mockFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpectAll(jsonPath("code").value("S000"),
                        jsonPath("message").value("success")
                )
                .andDo(document("파일 생성 API",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        ResourceDocumentation.resource(
                                new ResourceSnippetParametersBuilder()
                                        .tag("File")
                                        .responseFields(
                                                List.of(
                                                        PayloadDocumentation.fieldWithPath("code").type(JsonFieldType.STRING).description("S000"),
                                                        PayloadDocumentation.fieldWithPath("message").type(JsonFieldType.STRING).description("success"),
                                                        PayloadDocumentation.fieldWithPath("data").optional().type(JsonFieldType.NULL).description("NULL")
                                                )
                                        )
                                        .requestSchema(Schema.schema("FileCreateDto.Request"))
                                        .responseSchema(Schema.schema("FileCreateDto.Response"))
                                        .build()
                        )
                ));
        }

    @Test
    void 파일_삭제_성공() throws Exception {
        MockMultipartFile mockFile = new MockMultipartFile(
                "imageProfile", // MultipartFile의 필드명과 일치해야 함
                "imageProfile.jpg",   // 파일 이름
                "image/jpeg",    // 파일 타입
                "imageProfile".getBytes(StandardCharsets.UTF_8) // 파일 내용
        );

        File file = fileService.upload(mockFile);

        mockMvc.perform(delete("/file/{id}", file.getId()))
                .andExpect(status().isOk())
                .andExpectAll(jsonPath("code").value("S000"),
                        jsonPath("message").value("success")
                )
                        .andDo(document("파일 삭제 API",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        ResourceDocumentation.resource(
                                new ResourceSnippetParametersBuilder()
                                        .tag("File")
                                        .responseFields(
                                                List.of(
                                                        PayloadDocumentation.fieldWithPath("code").type(JsonFieldType.STRING).description("S000"),
                                                        PayloadDocumentation.fieldWithPath("message").type(JsonFieldType.STRING).description("success"),
                                                        PayloadDocumentation.fieldWithPath("data").optional().type(JsonFieldType.NULL).description("NULL")
                                                )
                                        )
                                        .responseSchema(Schema.schema("FileDeleteDto.Response"))
                                        .build()
                        )
                ));

    }
}