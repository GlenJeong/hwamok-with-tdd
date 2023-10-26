package com.hwamok.core.exception;

import com.hwamok.core.response.ApiResult;
import com.hwamok.core.response.Result;
import lombok.Getter;
// 커스터 익셉션
@Getter
public class HwamokException extends RuntimeException { // RuntimeException 상속받아야 익셉션이 발생

    private final ApiResult<?> apiResult;
    private final ExceptionCode exceptionCode;

    public HwamokException(ExceptionCode exceptionCode) {
        super(exceptionCode.name()); // RuntimeException을 의미함
        // exceptionCode.name()은 SUCCESS 의미함
        this.apiResult = ApiResult.of(exceptionCode);
        this.exceptionCode=exceptionCode;
    }

//    public static void validate(boolean expression, ExceptionCode exceptionCode) {
//        if(!expression){
//            throw new HwamokException(exceptionCode);
//        }

//    }
}












//    // HwamokException에 ExceptionCode를 넣어서 Exception 발생
//    // ExceptionCode가 ExceptionCode가지고 있어서 익셉션
//    private ApiResult<?> apiResult = Result.ok().getBody();
//    private String message;
//
//    public HwamokException(Throwable throwable) {
//        this.setApiResult(throwable);
//
//    }
//
//    private void setApiResult(Throwable throwable) {
//        ExceptionCode exceptionCode = getExceptionCode();
//        this.apiResult=ApiResult.of(throwable);
//    }

