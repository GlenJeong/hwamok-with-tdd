package com.hwamok.core.exception;

public enum ExceptionCode { // enum을 사용할 때는 값이 바뀌지 않을 때 사용한다, final, 열거형 클래스라고 한다.
    // 응답에 필요한 모든 코드와 메시지를 정의
    // code : S000, message : success

    // MockHttpServletResponse:
    //           Status = 200
    //    Error message = null
    //          Headers = [Content-Type:"application/json"]
    //     Content type = application/json
    //             Body = {"code":"S000","message":"success","data":null}
    //    Forwarded URL = null
    //   Redirected URL = null
    //          Cookies = []

    SUCCESS("S000", "success"), // 이렇게 값을 지정하려면 iv와 생성자가 필요하다. 생성자 호출이다.

    ERROR_SYSTEM("E000", "치명적인 오류가 발생했습니다."),

    REQUIRED_PARAMETER("E001", "필수 값이 누락되었습니다."),

    INVALID_NOTICE("E002", " 공지사항 작성 중 에러가 발생하였습니다."),

    NOT_FOUND_NOTICE("E003", "공지사항을 찾을 수가 없습니다."),
    FAILED_TO_MODIFY_NOTICE("E004", "공지사항을 수정하지 못 했습니다."),
    NOT_FOUND_USER("E005", "유저를 찾을 수가 없습니다."),

    FAIL_LOGIN_REQUEST("E006", "아이디 또는 패스워드를 확인해주세요."),

    ACCESS_DNEIED("E007", "로그인을 먼저 해주세요."),
    OVER_LENGTH_SAVEDFILENAME("E029", "저장 파일 이름의 길이가 초과되었습니다."),
    OVER_LENGTH_ORIGINALFILENAME("E030", "원본 파일 이름의 길이가 초과되었습니다."),
    NOT_FOUND_FILE("E031", "파일을 찾을 수 없습니다."),
    FILE_UPLOAD_FAILED("E032", "파일 업로드에 실패 하였습니다."),
    NOT_FILE_FORM("E033", "잘 못 된 형식의 파일입니다.");

    private final String code; // final 재할당을 금지하는 것
    private final String message; // 선언



    ExceptionCode(String code, String message) { // 생성자는 접근제어자가 private
        this.code = code; // 재할당, 초기화
        this.message = message; // 재할당, 초기화
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
