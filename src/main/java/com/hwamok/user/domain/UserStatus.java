package com.hwamok.user.domain;

import java.util.Calendar;
import java.util.Date;

public enum UserStatus {
    ACTIVATED("ACTIVATED", "활성화"),
    INACTIVATED("INACTIVATED", "비활성화");

    private String code;
    private String desc;

    UserStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public static UserStatus of(String userStatus) {
        if(userStatus.equals("ACTIVATED")){
            return UserStatus.ACTIVATED;
        }

        return UserStatus.INACTIVATED;

//        long time = (loginDate.getTime()/1000);
//
//        Date date2 = new Date();
//        long today = date2.getTime()/1000;
//
//        long different = today - time;
//        long InActiveDate = 8640000;
//
//        // 최근 로그인 날짜와 오늘 날짜의 차이가 100일(8640000초) 이상이면 InActive
//        // 최근 로그인 날짜와 오늘 날짜를 초 단위로 바꾸고 뺀 다음 8640000보다 크면 InActive
//        if(different > InActiveDate) { // 최근 로그인이 3개월 전이면 Inactive
//            return UserStatus.INACTIVATED;
//        } else {
//            return UserStatus.ACTIVATED;
//        }
    }

}
