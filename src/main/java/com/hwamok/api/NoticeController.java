package com.hwamok.api;

import com.hwamok.api.dto.notice.NoticeCreateDto;
import com.hwamok.core.response.ApiResult;
import com.hwamok.core.response.Result;
import com.hwamok.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {
    @Autowired
    private NoticeService noticeService;


    @PostMapping // 스프링에서 제공하는 ResponseEntity를 이용하여 성공, 실패 메시지를 재정의하는 코드를 만든 것.
    public ResponseEntity<ApiResult<?>> createNotice(@RequestBody NoticeCreateDto.Request request){ // @RequestBody json을 사용하니까
        noticeService.create(request.getTitle(), request.getContent());
        return Result.ok();

    }

}
