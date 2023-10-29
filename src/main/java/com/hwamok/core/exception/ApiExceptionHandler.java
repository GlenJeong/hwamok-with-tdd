package com.hwamok.core.exception;

import com.hwamok.core.exception.ExceptionCode;
import com.hwamok.core.response.ApiResult;
import com.hwamok.core.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// @ControllerAdvice은 글로벌 예외처리
// @Controller에 대한, 전역적으로 발생할 수 있는 예외를 잡아서 처리할 수 있다.
// @ControllerAdvice와 @ResponseBody를 합쳐놓은 어노테이션이다.
// @ControllerAdvice와 동일한 역할을 수행하고, 추가적으로 @ResponseBody를 통해 객체를 리턴할 수도 있다.
// 따라서 단순히 예외만 처리하고 싶다면 @ControllerAdvice를 적용하면 되고,
// 응답으로 객체를 리턴해야 한다면 @RestControllerAdvice를 적용하면 된다.

@Slf4j // 로깅하는 것, 로그를 기록하는 것
@RestControllerAdvice //  어떤 익셉션을 정의하면 어드바이스에서 catch해서 리턴해줌
// RestControllerAdvice = ControllerAdvice + ResponseBody
// RestControllerAdvice 로 선언하면 컨트롤러에서 리턴하는 값이 응답 값의 body로 세팅되어 클라이언트에게 전달
// Throwable(예외의 최고 조상) 예외를 잡는 RestControllerAdvice 를 선언했다.
// Throwable 예외를 잡는 RestControllerAdvice 를 선언
public class ApiExceptionHandler {

    @ExceptionHandler({Throwable.class}) // Throwable이 발생하면 이 에러 코드가 발생
    // Exception과 Error는 Throwable의 상속을 받는다.
    // 우선순위가 가장 낮아서 모든 익셉션이 적용되지 않으면 여기에 걸린다. 최상위 익셉션 클래스이다.
    // 모든 익셉션을 상속 받는 클래스가 Throwable.class
    public ResponseEntity<ApiResult<?>> exception(Throwable throwable) {
        log.info(throwable.getMessage());
        return Result.error();
    }

    @ExceptionHandler({IllegalArgumentException.class}) // IllegalArgumentException 발생하면 이 에러 코드가 발생
    public ResponseEntity<ApiResult<?>> IllegalArgumentException(IllegalArgumentException e) {
        log.info(e.getMessage());
        return Result.error(ExceptionCode.REQUIRED_PARAMETER);

    }

    @ExceptionHandler({HwamokException.class}) // HwamokException 발생하면 이 에러 코드가 발생
    public ResponseEntity<ApiResult<?>> HwamokException(HwamokException hwamokException) {
        log.info(hwamokException.getMessage());

        return Result.error(hwamokException.getExceptionCode());
    }


    // 모든 익셉션에 대해서 처리해 줘야 하고 나중에 처리하지 못한 익셉션이 있다면 반드시 익셉션을 추가해줘야 한다.
    // IllegalArgumentException, IllegalStateException(타입이나 객체 상태) 왠만하면 이 두 익셉션에서 걸러진다.
    // IllegalStateException는 Notice 객체 상태를 확인하면서 다시 작성하기로 함

    // 구체적인 Exception이 우선순위가 높아서 IllegalArgumentException 나온다.
    // {
    //  "code": "E001",
    //  "message": "필수 값이 누락되었습니다.",
    //  "data": null
    //}
}
