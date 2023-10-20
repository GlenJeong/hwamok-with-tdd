package com.hwamok.api.dto.notice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class NoticeCreateDto {
    // innerClass

    @Getter
    @NoArgsConstructor // 기본 생성자
    @AllArgsConstructor
    public static class Request {
        private String title;
        private String content;

    }
}
