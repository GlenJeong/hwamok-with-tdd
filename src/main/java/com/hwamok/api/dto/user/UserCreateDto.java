package com.hwamok.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;


public class UserCreateDto {
    @Getter // getter 추가
    @NoArgsConstructor // No 없는 Args 인자, 파라미터 Constructor 생성자, 매개변수 없는 생성자는 기본생성자를 의미
    @AllArgsConstructor // 모든 파라미터가 있는 생성자
    public static class Request {

        private String loginId;

        private String password;

        private String email;

        private String nickname;

        private String name;

        private String userStatus;

        private String birthday;

        public void encodePwd(String password) {
            this.password = password;
        }
    }
}
