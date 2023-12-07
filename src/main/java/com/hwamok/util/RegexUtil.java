package com.hwamok.util;

public class RegexUtil {

    //문자+숫자, 8자이상
    private static final String PASSWORD_PATTERN = " ^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$\n";
    //이메일형식
    private static final String EMAIL_PATTERN = "^[_a-z0-9-]+(.[_a-z0-9-]+)@(?:\\w+\\.)+\\w+$";
    //한글
    private static final String NICKNAME_PATTERN = "^[ㄱ-ㅎ가-힣]$";

    public static Boolean matches(String s, RegexType type) {
        boolean result;
        switch (type) {
            case EMAIL -> result = s.matches(EMAIL_PATTERN);

            case PASSWORD -> result = s.matches(PASSWORD_PATTERN);

            case NICKNAME -> result = s.matches(NICKNAME_PATTERN);

            default -> result = false;

        }
        return result;
    }
}
