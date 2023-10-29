package com.hwamok.api.dto.notice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class NoticeUpdateDto {
    @Getter // getter 추가
    @NoArgsConstructor // No 없는 Args 인자, 파라미터 Constructor 생성자, 매개변수 없는 생성자는 기본생성자를 의미
    @AllArgsConstructor // 모든 파라미터가 있는 생성자
    public static class Request {
        private String title;
        private String content;

    }
}
