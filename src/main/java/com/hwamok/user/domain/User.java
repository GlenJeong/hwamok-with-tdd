package com.hwamok.user.domain;

import com.hwamok.core.Preconditions;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalTime;
import java.util.Calendar;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10)
    private String loginId;

    @Column(length = 30)
    private String password;

    @Column(length = 50)
    private String email;

    private String nickname;

    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private Calendar birthday;

    private Instant createdAt=Instant.now(); // now() 현재 시간으로 반환

    //    public User(String loginId, String password, String email, String nickname, String name, Date birthday, long loginDate) {
    public User(String loginId, String password, String email, String nickname, String name,long loginDate, Calendar birthday) {

        Preconditions.require(Strings.isNotBlank(loginId));
        Preconditions.require(Strings.isNotBlank(password));
        Preconditions.require(Strings.isNotBlank(email));
        Preconditions.require(Strings.isNotBlank(name));
        Preconditions.require(loginId.length() <= 10 );
        Preconditions.require(password.length() <= 30 );
        Preconditions.require(email.length() <= 30 );
        Preconditions.require(name.length() <= 10 );

        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.name = name;
        this.status = UserStatus.of(loginDate); // Active or InActive
        this.birthday = birthday;
    }

    public void updateUser(String loginId, String password, String email, String nickname, String name,long loginDate, Calendar birthday) {

        Preconditions.require(Strings.isNotBlank(loginId));
        Preconditions.require(Strings.isNotBlank(password));
        Preconditions.require(Strings.isNotBlank(email));
        Preconditions.require(Strings.isNotBlank(name));
        Preconditions.require(loginId.length() <= 10 );
        Preconditions.require(password.length() <= 30 );
        Preconditions.require(email.length() <= 30 );
        Preconditions.require(name.length() <= 10 );

        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.name = name;
        this.status = UserStatus.of(loginDate); // Active or InActive
        this.birthday = birthday;
    }
}
//유저 엔티티
//
//loginId
//password
//email
//nickname
//name
//status(ACTIVATED, INACTIVATED)
//birthday(YYYY-mm-DD)
//createdAt
//
//회원가입
//회원단건정보조회
//회원수정
//회원탈퇴 (INACTIVATED)
//
//테스트 코드 모두 작성할 것