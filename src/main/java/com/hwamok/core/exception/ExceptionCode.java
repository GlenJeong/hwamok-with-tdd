package com.hwamok.core.exception;

public enum ExceptionCode { // enum을 사용할 때는 값이 바뀌지 않을 때 사용한다, final, 열거형 클래스라고 한다.
    // 응답에 필요한 모든 코드와 메시지를 정의
    // code : S000, message : success

    SUCCESS("S000", "SUCCESS");


    private final String code; // final 재할당을 금지하는 것
    private final String message; // 선언

    ExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
