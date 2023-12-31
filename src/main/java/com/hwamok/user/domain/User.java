package com.hwamok.user.domain;

import com.hwamok.core.Preconditions;
import com.hwamok.core.exception.ExceptionCode;
import com.hwamok.core.exception.HwamokException;
import com.hwamok.util.RegexType;
import com.hwamok.util.RegexUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import static com.hwamok.core.exception.HwamokException.validate;

@DynamicUpdate // 변경된 컬럼만 업데이트함,
// 매번 전체 업데이트 된다면 쿼리문을 보고 어떤 쿼리가 업데이트 되었는 지 확인하기가 힘듬
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, unique = true)
    private String loginId;

    @Column(length = 256)
    private String password;

    @Column(length = 30, unique = true)
    private String email;

    private String nickname;

    @Column(length = 10)
    private String name;

    @Column(length = 100)
    private String originalFileName;

    @Column(length = 100)
    private String savedFileName;

    @Column
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Temporal(TemporalType.DATE)
    private Date birthday;

    @UpdateTimestamp
    private Instant updateAt=Instant.now();

    @CreationTimestamp
    private Instant createdAt=Instant.now(); // now() 현재 시간으로 반환

    public User(String loginId, String password, String email, String nickname, String name, String userStatus, String originalFileName, String savedFileName, String birthday) throws Exception {

        Preconditions.require(Strings.isNotBlank(loginId));
        Preconditions.require(Strings.isNotBlank(password));
        Preconditions.require(Strings.isNotBlank(email));
        Preconditions.require(Strings.isNotBlank(name));
        Preconditions.require(loginId.length() <= 10 );
        Preconditions.require(email.length() <= 30 );
        Preconditions.require(name.length() <= 10 );

        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.name = name;
        this.userStatus = UserStatus.of(userStatus); // Active or InActive
        this.originalFileName = originalFileName;
        this.savedFileName = savedFileName;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(birthday);

        this.birthday = date;
    }

    public void updateUser(String loginId, String password, String email, String nickname, String name,String userStatus, String originalFileName, String savedFileName, String birthday) throws Exception {

        Preconditions.require(Strings.isNotBlank(loginId));
        Preconditions.require(Strings.isNotBlank(password));
        Preconditions.require(Strings.isNotBlank(email));
        Preconditions.require(Strings.isNotBlank(name));
        Preconditions.require(loginId.length() <= 10 );
        Preconditions.require(email.length() <= 30 );
        Preconditions.require(name.length() <= 10 );

//        validate(
//                RegexUtil.matches(password, RegexType.PASSWORD),
//                ExceptionCode.ERROR_SYSTEM);

        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.name = name;
        this.userStatus = UserStatus.of(userStatus); // Active or InActive
        this.originalFileName = originalFileName;
        this.savedFileName = savedFileName;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(birthday);

        this.birthday = date;
    }

    public void withdraw() {

        this.userStatus = UserStatus.INACTIVATED; // Active or InActive
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