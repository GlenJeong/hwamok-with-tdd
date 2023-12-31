package com.hwamok.core.response;

import com.hwamok.core.exception.ExceptionCode;
import lombok.Builder;
import lombok.Getter;

// 공통 된 코드(200, 201 ...)를 재공하는 코드,
@Builder
@Getter
public class ApiResult<T> {
    // code : xxxx
    // message : ~~~
    // data : T
    private String code;
    private String message;
    private T data;



    // 오버라이딩 -> 함수 재정의하네

    public static ApiResult<?> of(ExceptionCode exceptionCode){
        return ApiResult.of(exceptionCode, null);
    }
    // 정적팩토리메서드패턴 => 고유의 이름을 하나 부여해주는 패턴
    //https://tecoble.techcourse.co.kr/post/2020-05-26-static-factory-method/

    public static <T> ApiResult<T> of(ExceptionCode exceptionCode, T data) { // 오버로딩 -> 개많이 만드네
        return ApiResult.<T>builder()
                .code(exceptionCode.getCode())
                .message(exceptionCode.getMessage())
                .data(data)
                .build();// 끝내는 것.
    }

}
