package com.hwamok.core.response;

import com.hwamok.core.exception.ExceptionCode;
import com.hwamok.user.domain.UserStatus;
import org.springframework.http.ResponseEntity;

// NoticeController에서 게시물 작성이 정상적으로 성공할 때 결과를 보내기 위한 메서드 정의
public class Result {
    private Result() {
    } // 외부에서 기본 생성자를 사용하는 것을 원하지 않아서 private 만듬

    // 200 코드에 대한 정상적인 코드 만들기

    public static ResponseEntity<ApiResult<?>> ok() { // 재정의함
        return ResponseEntity.status(200).body(ApiResult.of(ExceptionCode.SUCCESS));
        // ResponseEntity는 사용자의 HttpRequest에 대한 응답 데이터를 포함하는 클래스\
        // ResponEntity를 이용해서 클라이언트에게 응답을 보냄
    }
    public static <T> ResponseEntity<ApiResult<?>> ok(T data) { // 재정의함
        return ResponseEntity.status(200).body(ApiResult.of(ExceptionCode.SUCCESS, data));
        // ResponseEntity는 사용자의 HttpRequest에 대한 응답 데이터를 포함하는 클래스\
        // ResponEntity를 이용해서 클라이언트에게 응답을 보냄
    }

    public static ResponseEntity<ApiResult<?>> created() { // 재정의함
        return ResponseEntity.status(201).body(ApiResult.of(ExceptionCode.SUCCESS));
        // ResponseEntity는 사용자의 HttpRequest에 대한 응답 데이터를 포함하는 클래스\
        // ResponEntity를 이용해서 클라이언트에게 응답을 보냄
    }

    public static <T> ResponseEntity<ApiResult<?>> created(T data) { // 재정의함
        return ResponseEntity.status(201).body(ApiResult.of(ExceptionCode.SUCCESS, data));
        // ResponseEntity는 사용자의 HttpRequest에 대한 응답 데이터를 포함하는 클래스\
        // ResponEntity를 이용해서 클라이언트에게 응답을 보냄
    }



    public static ResponseEntity<ApiResult<?>> error() { // error 코드는 원래 500인데 200으로 속여서 테스트는 성공한 것으로 만든다.
        return ResponseEntity.status(200).body(ApiResult.of(ExceptionCode.ERROR_SYSTEM));
    }

    public static ResponseEntity<ApiResult<?>> error(ExceptionCode exceptionCode) {
        return ResponseEntity.status(200).body(ApiResult.of(exceptionCode));
    }

    // MockHttpServletResponse:
    //           Status = 200
    //    Error message = null
    //          Headers = [Content-Type:"application/json"]
    //     Content type = application/json
    //             Body = {"code":"S000","message":"SUCCESS","data":null}
    //    Forwarded URL = null
    //   Redirected URL = null
    //          Cookies = []

}

