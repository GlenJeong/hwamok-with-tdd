package com.hwamok.api;

import com.hwamok.api.dto.notice.NoticeCreateDto;
import com.hwamok.api.dto.notice.NoticeUpdateDto;
import com.hwamok.core.response.ApiResult;
import com.hwamok.core.response.Result;
import com.hwamok.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Java Object를 Json으로 변환하거나 Json을 Java Object로 변환하는데 사용할 수 있는 Java 라이브러리
// JSON 데이터 형태 { "name" : "Jones", "age" : "42" , "city" : "New York" }
@RestController // Json 형태로 객체 데이터를 반환 , @Controller + @ResponseBody가 합쳐진 형태로 JSON 형태의 객체 데이터를 반환
// 데이터를 응답으로 제공하는 REST API를 개발할 때 주로 사용하며 객체를 ResponseEntity로 감싸서 반환
// @RestController는 리턴값에 자동으로 @ResponseBody가 붙게되어 별도 어노테이션을 명시해주지 않아도
// HTTP 응답데이터(body)에 자바 객체가 매핑되어 전달 된다.
// @Controller인 경우에 바디를 자바객체로 받기 위해서는 @ResponseBody 어노테이션을 반드시 명시해주어야한다.
// @ResponseBody 가 붙은 파라미터에는 HTTP 요청의 분문 body 부분이 그대로 전달된다.
@RequestMapping("/notice")
@RequiredArgsConstructor // lombok의 어노테이션의 하나로 생성자를 자동으로 주입해 준다.
public class NoticeController {
    @Autowired
    private NoticeService noticeService;


    @PostMapping // 스프링에서 제공하는 ResponseEntity를 이용하여 성공, 실패 메시지를 재정의하는 코드를 만든 것.
    // ApiResult<?> 모든 클래스나 인터페이스 타입 올 수 있다. 어떤 것일 올지 몰라서 <?> 와이일 카드를 사용
    public ResponseEntity<ApiResult<?>> createNotice(@RequestBody NoticeCreateDto.Request request){ // @RequestBody json을 사용하니까
        // ResponseEntity는 사용자의 HttpRequest에 대한 응답 데이터를 포함하는 클래스
        // @RequestBody를 통해서 자바객체로 conversion을 하는데, 이때 HttpMessageConverter를 사용한다.
        // 스프링에서 비동기 처리를 하는 경우 @RequestBody , @ResponseBody를 사용한다.
        // 웹에서 화면전환(새로고침) 없이 이루어지는 동작들은 대부분 비동기 통신으로 이루어진다.
        // 비동기통신을 하기위해서는 클라이언트에서 서버로 요청 메세지를 보낼 때, 본문에 데이터를 담아서 보내야 하고,
        // 서버에서 클라이언트로 응답을 보낼때에도 본문에 데이터를 담아서 보내야 한다.
        // 이 본문이 담기는 곳이 바로 body 이다. 요청본문 requestBody, 응답본문 responseBody 을 담아서 보내야 한다.
        // 이때 본문에 담기는 데이터 형식은 여러가지 형태가 있겠지만 가장 대표적으로 사용되는 것이 JSON 이다.
        // JSON 데이터 형태 { "name" : "Jones", "age" : "42" , "city" : "New York" }
        // 즉, 비동기식 클라-서버 통신을 위해 JSON 형식의 데이터를 주고받는 것이다.
        noticeService.create(request.getTitle(), request.getContent());
        return Result.created();

    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResult<?>> updateNoice(@PathVariable Long id, @RequestBody NoticeUpdateDto.Request dto) {
        noticeService.update(id, dto.getTitle(), dto.getContent());
        return Result.ok();
    }
}