package com.hwamok.user.domain;

public enum AccountStatus {

    ACTIVATED("ACTIVATED", "회원가입"),
    INACTIVATED("INACTIVATED", "회원탈퇴");

    private String status;
    private String desc;

    AccountStatus(String status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    public static AccountStatus of(String accountActive) {

        if(accountActive.equals("ACTIVATED")){
            return AccountStatus.ACTIVATED;
        }

        return AccountStatus.INACTIVATED;
    }
}
