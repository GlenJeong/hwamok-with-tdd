package com.hwamok.user.domain;

import com.hwamok.notice.domain.Notice;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.text.ParseException;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import static com.hwamok.fixtures.NoticeFixture.createNotice;
import static com.hwamok.user.domain.UserStatus.Active;
import static com.hwamok.user.domain.UserStatus.InActive;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;


class UserTest {

    @Test
    void 사용자_생성_성공() throws ParseException {
        // String loginId, String password, String email, String nickname, String name, Date birthday
        // 최근 로그인이 2023년 7월, 22일 가정하고 date2.set(2023, 7, 29); 날짜를 초 단위로 환산  loginDate = date2.getTimeInMillis()/1000 => 1693313241
        // Active 최근 로그인 날짜와 오늘 날짜의 차이가 100일 미만이면 Active
        // InActive 최근 로그인 날짜와 오늘 날짜의 차이가 100일 이상이면 InActive
        // 2023년 10월 29일 1698586852
        // 2023년 7월 20일 1689860625   101일 차이 InActive 8726227  8640000
        // 2023년 7월 25일 1690292674   96일차    Active    8294178  8640000

        Calendar date2 = Calendar.getInstance();
        date2.set(2023, 6, 25); // 7월 20일(101일) 100일 이상 InActive 1689860199, 7월 25일(96일) 100일 미만 Active 1690292220
        // month는 0부터 시작하기 때문에 6은 7월
        long loginDate = date2.getTimeInMillis()/1000;
        System.out.println("loginDate = " + loginDate);

        User user = new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", "정인범", loginDate, "1988-02-26");

        Assertions.assertThat(user.getId()).isNull();
        Assertions.assertThat(user.getLoginId()).isEqualTo("jyb1624");
        Assertions.assertThat(user.getPassword()).isEqualTo("1234");
        Assertions.assertThat(user.getEmail()).isEqualTo("jyb1624@test.com");
        Assertions.assertThat(user.getNickname()).isEqualTo("Glenn");
        Assertions.assertThat(user.getName()).isEqualTo("정인범");
        Assertions.assertThat(user.getStatus()).isEqualTo(Active);
        Assertions.assertThat(user.getBirthday()).isEqualTo("1988-02-26");
        Assertions.assertThat(user.getBirthday()).isNotNull();


    }

    @ParameterizedTest
    @NullAndEmptySource
    void 사용자_생성_실패_loginId_빈값_혹은_null(String loginId) {
        Calendar date2 = Calendar.getInstance();
        date2.set(2023, 6, 25);// 100일 미만 Active
        long loginDate = date2.getTimeInMillis()/1000;

//        User user = new User(loginId, "1234", "jyb1624@test.com", "Glenn", "정인범", loginDate, "1988-02-26");
//
//        Assertions.assertThat(user.getId()).isNull();
//        Assertions.assertThat(user.getLoginId()).isEqualTo("jyb1624");
//        Assertions.assertThat(user.getPassword()).isEqualTo("1234");
//        Assertions.assertThat(user.getEmail()).isEqualTo("jyb1624@test.com");
//        Assertions.assertThat(user.getNickname()).isEqualTo("Glenn");
//        Assertions.assertThat(user.getName()).isEqualTo("정인범");
//        Assertions.assertThat(user.getStatus()).isEqualTo(Active);
//        Assertions.assertThat(user.getBirthday()).isEqualTo("1988-02-26");
//        Assertions.assertThat(user.getBirthday()).isNotNull();

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(()-> new User(loginId, "1234", "jyb1624@test.com", "Glenn", "정인범", loginDate, "1988-02-26"));

    }

    @ParameterizedTest
    @NullAndEmptySource
    void 사용자_생성_실패_password_빈값_혹은_null(String password) {
        Calendar date2 = Calendar.getInstance();
        date2.set(2023, 6, 25);// 100일 미만 Active
        long loginDate = date2.getTimeInMillis()/1000;

//        User user = new User("jyb1624", password, "jyb1624@test.com", "Glenn", "정인범", loginDate, "1988-02-26");
//
//        Assertions.assertThat(user.getId()).isNull();
//        Assertions.assertThat(user.getLoginId()).isEqualTo("jyb1624");
//        Assertions.assertThat(user.getPassword()).isEqualTo("1234");
//        Assertions.assertThat(user.getEmail()).isEqualTo("jyb1624@test.com");
//        Assertions.assertThat(user.getNickname()).isEqualTo("Glenn");
//        Assertions.assertThat(user.getName()).isEqualTo("정인범");
//        Assertions.assertThat(user.getStatus()).isEqualTo(Active);
//        Assertions.assertThat(user.getBirthday()).isEqualTo("1988-02-26");
//        Assertions.assertThat(user.getBirthday()).isNotNull();

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(()-> new User("jyb1624", password, "jyb1624@test.com", "Glenn", "정인범", loginDate, "1988-02-26"));

    }

    @ParameterizedTest
    @NullAndEmptySource
    void 사용자_생성_실패_email_빈값_혹은_null(String email) {
        Calendar date2 = Calendar.getInstance();
        date2.set(2023, 6, 25);// 100일 미만 Active
        long loginDate = date2.getTimeInMillis()/1000;

//        User user = new User("jyb1624", "1234", email, "Glenn", "정인범", loginDate, "1988-02-26");
//
//        Assertions.assertThat(user.getId()).isNull();
//        Assertions.assertThat(user.getLoginId()).isEqualTo("jyb1624");
//        Assertions.assertThat(user.getPassword()).isEqualTo("1234");
//        Assertions.assertThat(user.getEmail()).isEqualTo("jyb1624@test.com");
//        Assertions.assertThat(user.getNickname()).isEqualTo("Glenn");
//        Assertions.assertThat(user.getName()).isEqualTo("정인범");
//        Assertions.assertThat(user.getStatus()).isEqualTo(Active);
//        Assertions.assertThat(user.getBirthday()).isEqualTo("1988-02-26");
//        Assertions.assertThat(user.getBirthday()).isNotNull();

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(()-> new User("jyb1624", "1234", email, "Glenn", "정인범", loginDate, "1988-02-26"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 사용자_생성_실패_name_빈값_혹은_null(String name) {
        Calendar date2 = Calendar.getInstance();
        date2.set(2023, 6, 25);// 100일 미만 Active
        long loginDate = date2.getTimeInMillis()/1000;

//        User user = new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", name, loginDate, "1988-02-26");
//
//        Assertions.assertThat(user.getId()).isNull();
//        Assertions.assertThat(user.getLoginId()).isEqualTo("jyb1624");
//        Assertions.assertThat(user.getPassword()).isEqualTo("1234");
//        Assertions.assertThat(user.getEmail()).isEqualTo("jyb1624@test.com");
//        Assertions.assertThat(user.getNickname()).isEqualTo("Glenn");
//        Assertions.assertThat(user.getName()).isEqualTo("정인범");
//        Assertions.assertThat(user.getStatus()).isEqualTo(Active);
//        Assertions.assertThat(user.getBirthday()).isEqualTo("1988-02-26");
//        Assertions.assertThat(user.getBirthday()).isNotNull();

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(()-> new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", name, loginDate, "1988-02-26"));
    }

    @Test
    void 사용자_생성_실패_loginId_11글자_이상() {
        Calendar date2 = Calendar.getInstance();
        date2.set(2023, 6, 25); // 100일 미만 Active
        long loginDate = date2.getTimeInMillis()/1000;

//        User user = new User("jyb16241624", "1234", "jyb1624@test.com", "Glenn", "정인범", loginDate, "1988-02-26");
//        Assertions.assertThat(user.getLoginId()).isEqualTo("jyb1624162");

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(()->new User("jyb16241624", "1234", "jyb1624@test.com", "Glenn", "정인범", loginDate, "1988-02-26"));

    }

    @Test
    void 사용자_생성_실패_password_30글자_이상() {
        Calendar date2 = Calendar.getInstance();
        date2.set(2023, 6, 25); // 100일 미만 Active
        long loginDate = date2.getTimeInMillis()/1000;

//        User user = new User("jyb1624", "123456789101234567891012345678910", "jyb1624@test.com", "Glenn", "정인범", loginDate, "1988-02-26");
//        Assertions.assertThat(user.getPassword()).isEqualTo("123456789101234567891012345678910");

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(()->new User("jyb1624", "123456789101234567891012345678910", "jyb1624@test.com", "Glenn", "정인범", loginDate, "1988-02-26"));

    }

    @Test
    void 사용자_생성_실패_email_30글자_이상() {
        Calendar date2 = Calendar.getInstance();
        date2.set(2023, 6, 25); // 100일 미만 Active
        long loginDate = date2.getTimeInMillis()/1000;

//        User user = new User("jyb1624", "1234", "jyb1624jyb1624jyb16241@test.com", "Glenn", "정인범", loginDate, "1988-02-26");
//        Assertions.assertThat(user.getEmail()).isEqualTo("jyb1624jyb1624jyb1624@test.com");

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(()->new User("jyb1624", "1234", "jyb1624jyb1624jyb16241@test.com", "Glenn", "정인범", loginDate, "1988-02-26"));

    }

    @Test
    void 사용자_생성_실패_name_10글자_이상() {
        Calendar date2 = Calendar.getInstance();
        date2.set(2023, 6, 25); // 100일 미만 Active
        long loginDate = date2.getTimeInMillis()/1000;

//        User user = new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", "정인범정인범정인범정인범", loginDate, "1988-02-26");
//        Assertions.assertThat(user.getName()).isEqualTo("정인범정인범정인범정인범");

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(()->new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", "정인범정인범정인범정인범", loginDate, "1988-02-26"));

    }


}