package com.hwamok.user.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.text.ParseException;



class UserTest {

    @Test
    void 회원_가입_성공() throws ParseException {
        // userStatus = 회원 활성화, 계정 휴면
        // accountStatus = 계정 활성화, 계정 탈퇴

        User user = new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26");

        // 테스트할 때 순선도 중요, pk가 맨 위로 올라오게 테스트,
        Assertions.assertThat(user.getId()).isNull();
        Assertions.assertThat(user.getLoginId()).isEqualTo("jyb1624");
        Assertions.assertThat(user.getPassword()).isEqualTo("1234");
        Assertions.assertThat(user.getEmail()).isEqualTo("jyb1624@test.com");
        Assertions.assertThat(user.getNickname()).isEqualTo("Glenn");
        Assertions.assertThat(user.getName()).isEqualTo("정인범");
        Assertions.assertThat(user.getBirthday()).isEqualTo("1988-02-26");
        Assertions.assertThat(user.getUserStatus()).isEqualTo(UserStatus.ACTIVATED);


    }

    @Test
    void 회원_수정_성공() throws Exception {
        User user = new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26");

        user.updateUser("jyb0226", "4321", "jyb0226@test.com", "InBeom", "인범", "ACTIVATED", "1988-02-26");

        Assertions.assertThat(user.getId()).isNull();
        Assertions.assertThat(user.getLoginId()).isEqualTo("jyb0226");
        Assertions.assertThat(user.getPassword()).isEqualTo("4321");
        Assertions.assertThat(user.getEmail()).isEqualTo("jyb0226@test.com");
        Assertions.assertThat(user.getNickname()).isEqualTo("InBeom");
        Assertions.assertThat(user.getName()).isEqualTo("인범");
        Assertions.assertThat(user.getUserStatus()).isEqualTo(UserStatus.ACTIVATED);
        Assertions.assertThat(user.getBirthday()).isEqualTo("1988-02-26");

    }

    @Test
    void 회원_탈퇴_성공() throws Exception {
        User user = new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26");
        System.out.println("withdraw user.getAccountStatus() = " + user.getUserStatus());
        Assertions.assertThat(user.getUserStatus()).isEqualTo(UserStatus.ACTIVATED);

        user.withdraw();

        Assertions.assertThat(user.getId()).isNull();
        Assertions.assertThat(user.getLoginId()).isEqualTo("jyb1624");
        Assertions.assertThat(user.getPassword()).isEqualTo("1234");
        Assertions.assertThat(user.getEmail()).isEqualTo("jyb1624@test.com");
        Assertions.assertThat(user.getNickname()).isEqualTo("Glenn");
        Assertions.assertThat(user.getName()).isEqualTo("정인범");
        Assertions.assertThat(user.getUserStatus()).isEqualTo(UserStatus.INACTIVATED);
        Assertions.assertThat(user.getBirthday()).isEqualTo("1988-02-26");

        System.out.println("withdraw user.getAccountStatus() = " + user.getUserStatus());
        Assertions.assertThat(user.getUserStatus()).isEqualTo(UserStatus.INACTIVATED);
    }


    @ParameterizedTest
    @NullAndEmptySource
    void 회원_가입_실패_loginId_빈값_혹은_null(String loginId) throws Exception{

//        User user = new User(loginId, "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26", "ACTIVATED");

//        Assertions.assertThat(user.getId()).isNull();
//        Assertions.assertThat(user.getLoginId()).isEqualTo("jyb1624");
//        Assertions.assertThat(user.getPassword()).isEqualTo("1234");
//        Assertions.assertThat(user.getEmail()).isEqualTo("jyb1624@test.com");
//        Assertions.assertThat(user.getNickname()).isEqualTo("Glenn");
//        Assertions.assertThat(user.getName()).isEqualTo("정인범");
//        Assertions.assertThat(user.getUserStatus()).isEqualTo(UserStatus.ACTIVATED);
//        Assertions.assertThat(user.getBirthday()).isEqualTo("1988-02-26");
//        Assertions.assertThat(user.getAccountStatus()).isEqualTo(AccountStatus.ACTIVATED);

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(()-> new User(loginId, "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26"));

    }

    @ParameterizedTest
    @NullAndEmptySource
    void 회원_가입_실패_password_빈값_혹은_null(String password) {

//        User user = new User("jyb1624", password, "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26", "ACTIVATED");

//        Assertions.assertThat(user.getId()).isNull();
//        Assertions.assertThat(user.getLoginId()).isEqualTo("jyb1624");
//        Assertions.assertThat(user.getPassword()).isEqualTo("1234");
//        Assertions.assertThat(user.getEmail()).isEqualTo("jyb1624@test.com");
//        Assertions.assertThat(user.getNickname()).isEqualTo("Glenn");
//        Assertions.assertThat(user.getName()).isEqualTo("정인범");
//        Assertions.assertThat(user.getUserStatus()).isEqualTo(UserStatus.ACTIVATED);
//        Assertions.assertThat(user.getBirthday()).isEqualTo("1988-02-26");
//        Assertions.assertThat(user.getAccountStatus()).isEqualTo(AccountStatus.ACTIVATED);

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(()-> new User("jyb1624", password, "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26"));

    }

    @ParameterizedTest
    @NullAndEmptySource
    void 회원_가입_실패_email_빈값_혹은_null(String email) throws Exception {

//        User user = new User("jyb1624", "1234", email, "Glenn", "정인범", "ACTIVATED", "1988-02-26", "ACTIVATED");

//        Assertions.assertThat(user.getId()).isNull();
//        Assertions.assertThat(user.getLoginId()).isEqualTo("jyb1624");
//        Assertions.assertThat(user.getPassword()).isEqualTo("1234");
//        Assertions.assertThat(user.getEmail()).isEqualTo("jyb1624@test.com");
//        Assertions.assertThat(user.getNickname()).isEqualTo("Glenn");
//        Assertions.assertThat(user.getName()).isEqualTo("정인범");
//        Assertions.assertThat(user.getUserStatus()).isEqualTo(UserStatus.ACTIVATED);
//        Assertions.assertThat(user.getBirthday()).isEqualTo("1988-02-26");
//        Assertions.assertThat(user.getAccountStatus()).isEqualTo(AccountStatus.ACTIVATED);

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(()-> new User("jyb1624", "1234", email, "Glenn", "정인범", "ACTIVATED", "1988-02-26"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 회원_가입_실패_name_빈값_혹은_null(String name) throws Exception {


//        User user = new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", name, "ACTIVATED", "1988-02-26", "ACTIVATED");

//        Assertions.assertThat(user.getId()).isNull();
//        Assertions.assertThat(user.getLoginId()).isEqualTo("jyb1624");
//        Assertions.assertThat(user.getPassword()).isEqualTo("1234");
//        Assertions.assertThat(user.getEmail()).isEqualTo("jyb1624@test.com");
//        Assertions.assertThat(user.getNickname()).isEqualTo("Glenn");
//        Assertions.assertThat(user.getName()).isEqualTo("정인범");
//        Assertions.assertThat(user.getUserStatus()).isEqualTo(UserStatus.ACTIVATED);
//        Assertions.assertThat(user.getBirthday()).isEqualTo("1988-02-26");
//        Assertions.assertThat(user.getAccountStatus()).isEqualTo(AccountStatus.ACTIVATED);

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(()-> new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", name, "ACTIVATED", "1988-02-26"));
    }

    @Test
    void 회원_가입_실패_loginId_11글자_이상() throws Exception {

//        User user = new User("jyb16241624", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26", "ACTIVATED");
//        Assertions.assertThat(user.getLoginId()).isEqualTo("jyb1624162");

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(()->new User("jyb16241624", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26"));

    }

    @Test
    void 회원_가입_실패_password_31글자_이상() throws Exception {

//        User user = new User("jyb1624", "123456789101234567891012345678910", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26", "ACTIVATED");
//        Assertions.assertThat(user.getPassword()).isEqualTo("123456789101234567891012345678910");

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(()->new User("jyb1624", "123456789101234567891012345678910", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26"));

    }

    @Test
    void 회원_가입_실패_email_31글자_이상()throws Exception {

//        User user = new User("jyb1624", "1234", "jyb1624jyb1624jyb16241@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26", "ACTIVATED");
//        Assertions.assertThat(user.getEmail()).isEqualTo("jyb1624jyb1624jyb1624@test.com");

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(()->new User("jyb1624", "1234", "jyb1624jyb1624jyb16241@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26"));

    }

    @Test
    void 회원_가입_실패_name_11글자_이상() throws Exception {

//        User user = new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", "나무잎이오색으로물든가을", "ACTIVATED", "1988-02-26", "ACTIVATED");
//        Assertions.assertThat(user.getName()).isEqualTo("나무잎이오색으로물든가을");

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(()->new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", "나무잎이오색으로물든가을", "ACTIVATED", "1988-02-26"));
    }


    @ParameterizedTest
    @NullAndEmptySource
    void 회원_수정_실패_loginId_빈값_혹은_null(String loginId) throws Exception {
        User user = new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26");

        // user.updateUser(loginId, "4321", "jyb0226@test.com", "InBeom", "인범", "ACTIVATED", "1988-02-26");

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(()-> user.updateUser(loginId, "4321", "jyb0226@test.com", "InBeom", "인범", "ACTIVATED", "1988-02-26"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 회원_수정_실패_password_빈값_혹은_null(String password) throws Exception {
        User user = new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26");

        // user.updateUser("jyb1624", password, "jyb0226@test.com", "InBeom", "인범", "ACTIVATED", "1988-02-26", "ACTIVATED");

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(()-> user.updateUser("jyb0226", password, "jyb0226@test.com", "InBeom", "인범", "ACTIVATED", "1988-02-26"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 회원_수정_실패_email_빈값_혹은_null(String email) throws Exception {
        User user = new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26");

        // user.updateUser("jyb1624", "1234", email, "InBeom", "인범", "ACTIVATED", "1988-02-26", "ACTIVATED");

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(()-> user.updateUser("jyb1624", "1234", email, "InBeom", "인범", "ACTIVATED", "1988-02-26"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 회원_수정_실패_name_빈값_혹은_null(String name) throws Exception {
        User user = new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26");

        // user.updateUser("jyb0226", "4321", "jyb0226@test.com", "InBeom", name, "ACTIVATED", "1988-02-26", "ACTIVATED");

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(()-> user.updateUser("jyb0226", "4321", "jyb0226@test.com", "InBeom", name, "ACTIVATED", "1988-02-26"));
    }

    @Test
    void 회원_수정_실패_loginId_11글자_이상() throws Exception {
        User user = new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26");

        // user.updateUser("jybjyb02260226", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26", "INACTIVATED");

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(()-> user.updateUser("jybjyb02260226", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26"));
    }

    @Test
    void 회원_수정_실패_password_31글자_이상() throws Exception {
        User user = new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26");

        // user.updateUser("jyb0226", "109876543211098765432110987654321", "jyb0226@test.com", "InBeom", "인범", "ACTIVATED", "1988-02-26", "ACTIVATED");

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(()-> user.updateUser("jyb0226", "109876543211098765432110987654321", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26"));
    }

    @Test
    void 회원_수정_실패_email_31글자_이상() throws Exception {
        User user = new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26");

        // user.updateUser("jyb0226", "4321", "jyb0226jyb0226jyb0226123@test.com", "InBeom", "인범", "ACTIVATED", "1988-02-26", "ACTIVATED");

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(()-> user.updateUser("jyb0226", "4321", "jyb0226jyb0226jyb0226123@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26"));
    }

    @Test
    void 회원_수정_실패_name_11글자_이상() throws Exception {
        User user = new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26");

        // user.updateUser("jyb0226", "4321", "jyb0226@test.com", "InBeom", "늦은오후하늘에노을과구름", "ACTIVATED", "1988-02-26", "ACTIVATED");

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(()-> user.updateUser("jyb0226", "4321", "jyb0226@test.com", "Glenn", "늦은오후하늘에노을과구름", "ACTIVATED", "1988-02-26"));
    }
}