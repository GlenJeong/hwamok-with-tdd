package com.hwamok.user.domain;

import java.util.Calendar;
import java.util.Date;

public enum UserStatus {
    Active("A", "활성화"),
    InActive("I", "비활성화");

    private String code;
    private String desc;

    UserStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public static UserStatus of(long loginDate) {

        Calendar date1 = Calendar.getInstance();
        int year = date1.get(Calendar.YEAR);
        int month = date1.get(Calendar.MONTH);
        int date = date1.get(Calendar.DATE);

        date1.set(year, month, date);
        long today = date1.getTimeInMillis()/1000;

        long different = today - loginDate;
        long InActiveDate = 8640000;

        // 최근 로그인 날짜와 오늘 날짜의 차이가 100일(8640000초) 이상이면 InActive
        // 최근 로그인 날짜와 오늘 날짜를 초 단위로 바꾸고 뺀 다음 8640000보다 크면 InActive
        if(different > InActiveDate) { // 최근 로그인이 3개월 전이면 Inactive
            return UserStatus.InActive;
        } else {
            return UserStatus.Active;
        }
    }

}
