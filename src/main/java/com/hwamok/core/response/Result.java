package com.hwamok.core.response;

import com.hwamok.core.exception.ExceptionCode;
import org.springframework.http.ResponseEntity;

public class Result {
    private Result() {
    } // 외부에서 사용하는 것을 원하지 않아서 private 만듬

    // 200 코드에 대한 정상적인 코드 만들기
    public static ResponseEntity<ApiResult<?>> ok() { // 재정의함
        return ResponseEntity.status(200).body(ApiResult.of(ExceptionCode.SUCCESS));
    }

}
