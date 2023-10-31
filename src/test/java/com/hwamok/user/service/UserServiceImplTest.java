package com.hwamok.user.service;

import com.hwamok.notice.domain.NoticeRepository;
import com.hwamok.notice.service.NoticeService;
import com.hwamok.user.domain.User;
import com.hwamok.user.domain.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void 회원_가입_성공() throws Exception {

       User user = userRepository.save(new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26", "ACTIVATED"));

        assertThat(user.getId()).isNotNull();
    }

    @Test
    void 회원_탈퇴_성공() throws Exception {
        User user = userRepository.save(new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26", "ACTIVATED"));

        User foundUserId = userRepository.findById(user.getId()).orElseThrow();

        userRepository.delete(foundUserId);

        assertThat(user.getLoginId()).isEqualTo("jyb1624");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 회원_가입_실패_loginId_null_혹은_빈값(String loginId) throws Exception {
//        User user = userRepository.save(new User(loginId, "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26", "ACTIVATED"));
//
//        assertThat(user.getId()).isNotNull();

        assertThatIllegalArgumentException()
                .isThrownBy(()->userRepository.save(new User(loginId, "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26", "ACTIVATED")));

    }

    @ParameterizedTest
    @NullAndEmptySource
    void 회원_가입_실패_password_null_혹은_빈값(String password) throws Exception {
//        User user = userRepository.save(new User("jyb1624", password, "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26", "ACTIVATED"));
//
//        assertThat(user.getId()).isNotNull();

        assertThatIllegalArgumentException()
                .isThrownBy(()->userRepository.save(new User("jyb1624", password, "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26", "ACTIVATED")));

    }

    @ParameterizedTest
    @NullAndEmptySource
    void 회원_가입_실패_email_null_혹은_빈값(String email) throws Exception {
//        User user = userRepository.save(new User("jyb1624", "1234", email, "Glenn", "정인범", "ACTIVATED", "1988-02-26", "ACTIVATED"));
//
//        assertThat(user.getId()).isNotNull();

        assertThatIllegalArgumentException()
                .isThrownBy(()->userRepository.save(new User("jyb1624", "1234", email, "Glenn", "정인범", "ACTIVATED", "1988-02-26", "ACTIVATED")));

    }

    @ParameterizedTest
    @NullAndEmptySource
    void 회원_가입_실패_name_null_혹은_빈값(String name) throws Exception {
//        User user = userRepository.save(new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", name, "ACTIVATED", "1988-02-26", "ACTIVATED"));
//
//        assertThat(user.getId()).isNotNull();

        assertThatIllegalArgumentException()
                .isThrownBy(()->userRepository.save(new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", name, "ACTIVATED", "1988-02-26", "ACTIVATED")));

    }

    @Test
    void 회원_단건_정보_조회() throws Exception{
        User user = userRepository.save(new User("jyb0226", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26", "ACTIVATED"));

        User userOne = userRepository.findByLoginId(user.getLoginId()).orElseThrow(()-> new RuntimeException());

        assertThat(userOne.getLoginId()).isEqualTo(user.getLoginId());

        System.out.println("userOne.getLoginId() = " + userOne.getLoginId());
        System.out.println("user.getLoginId() = " + user.getLoginId());


    }

    @Test
    void 회원_단건_정보_조회_() throws Exception{
        User user = userRepository.save(new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26", "ACTIVATED"));

        User userOne = userRepository.findById(user.getId()).orElseThrow(()-> new RuntimeException());

        assertThat(userOne.getLoginId()).isEqualTo(user.getLoginId());
        assertThat(userOne.getEmail()).isEqualTo(user.getEmail());

        System.out.println("userOne.getLoginId() = " + userOne.getLoginId());
        System.out.println("user.getLoginId() = " + user.getLoginId());


    }

    @Test
    void 회원_단건_정보_조회_실패_존재하지_않는_회원() throws Exception{
        User user = userRepository.save(new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26", "ACTIVATED"));

//        User userOne = userRepository.findByLoginId("jyb0226").orElseThrow();
//
//        assertThat(userOne.getLoginId()).isEqualTo(user.getLoginId());

        assertThatIllegalArgumentException().isThrownBy(()->userRepository.findByLoginId("jyb0226").orElseThrow(()->new IllegalArgumentException()));

    }

    @Test
    void 회원_수정_성공() throws Exception {
        User user = userRepository.save(new User("jyb0120", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26", "ACTIVATED"));

        User foundUserId = userRepository.findByLoginId(user.getLoginId()).orElseThrow();

        foundUserId = userService.updateProfile(foundUserId.getId(), "jyb1234", "4321", "jyb0226@test.com", "Glenn", "인범", "INACTIVATED", "1988-02-26", "ACTIVATED");

        assertThat(foundUserId.getId()).isNotNull();
        assertThat(foundUserId.getLoginId()).isEqualTo("jyb1234");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 회원_수정_실패_loginId_null_혹은_빈값(String loginId) throws Exception {
        User user = userRepository.save(new User("jyb0052", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26", "ACTIVATED"));

        User foundLoginId = userRepository.findById(user.getId()).orElseThrow();

       //foundLoginId = userService.updateProfile(foundLoginId.getId(), loginId,"4321", "jyb0226@test.com", "Glenn", "인범", "INACTIVATED", "1988-02-26", "ACTIVATED");

       assertThatIllegalArgumentException().isThrownBy(()->userService.updateProfile(foundLoginId.getId(), loginId,"4321", "jyb0226@test.com", "Glenn", "인범", "INACTIVATED", "1988-02-26", "ACTIVATED"));

    }

    @ParameterizedTest
    @NullAndEmptySource
    void 회원_수정_실패_password_null_혹은_빈값(String password) throws Exception {
        User user = userRepository.save(new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26", "ACTIVATED"));

        User foundLoginId = userRepository.findById(user.getId()).orElseThrow();

        // foundLoginId = userService.updateProfile(foundLoginId.getId(), "jyb0226",password, "jyb0226@test.com", "Glenn", "인범", "INACTIVATED", "1988-02-26", "ACTIVATED");

        assertThatIllegalArgumentException().isThrownBy(()->userService.updateProfile(foundLoginId.getId(), "jyb0226",password, "jyb0226@test.com", "Glenn", "인범", "INACTIVATED", "1988-02-26", "ACTIVATED"));

    }

    @ParameterizedTest
    @NullAndEmptySource
    void 회원_수정_실패_email_null_혹은_빈값(String email) throws Exception {
        User user = userRepository.save(new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26", "ACTIVATED"));

        User foundLoginId = userRepository.findById(user.getId()).orElseThrow();

        // foundLoginId = userService.updateProfile(foundLoginId.getId(), "jyb0226","4321", email, "Glenn", "인범", "INACTIVATED", "1988-02-26", "ACTIVATED");

        assertThatIllegalArgumentException().isThrownBy(()->userService.updateProfile(foundLoginId.getId(), "jyb0226","4321", email, "Glenn", "인범", "INACTIVATED", "1988-02-26", "ACTIVATED"));

    }

    @ParameterizedTest
    @NullAndEmptySource
    void 회원_수정_실패_name_null_혹은_빈값(String name) throws Exception {
        User user = userRepository.save(new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26", "ACTIVATED"));

        User foundLoginId = userRepository.findById(user.getId()).orElseThrow();

        // foundLoginId = userService.updateProfile(foundLoginId.getId(), "jyb0226","4321", "jyb0226@test.com", "Glenn", name, "INACTIVATED", "1988-02-26", "ACTIVATED");

        assertThatIllegalArgumentException().isThrownBy(()->userService.updateProfile(foundLoginId.getId(), "jyb0226","4321", "jyb11624@test.com", "Glenn", name, "INACTIVATED", "1988-02-26", "ACTIVATED"));

    }

    @Test
    void 회원_수정_실패_loginId_11글자_이상() throws Exception {
        User user = userRepository.save(new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26", "ACTIVATED"));

        User foundLoginId = userRepository.findById(user.getId()).orElseThrow();

        //foundLoginId = userService.updateProfile(foundLoginId.getId(), "jyb0226jyb0226", "4321", "jyb0226@test.com", "Glen", "인범", "INACTIVATED", "1988-01-20", "ACTIVATED");

        assertThatIllegalArgumentException().isThrownBy(() -> userService.updateProfile(foundLoginId.getId(), "jyb0226jyb0226", "4321", "jyb0226@test.com", "Glen", "인범", "INACTIVATED", "1988-01-20", "ACTIVATED"));

    }

    @Test
    void 회원_수정_실패_password_31글자_이상() throws Exception {
        User user = userRepository.save(new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26", "ACTIVATED"));

        User foundLoginId = userRepository.findById(user.getId()).orElseThrow();

        //foundLoginId = userService.updateProfile(foundLoginId.getId(), "jyb0226jyb0226", "109876543211098765432110987654321", "jyb0226@test.com", "Glen", "인범", "INACTIVATED", "1988-01-20", "ACTIVATED");

        assertThatIllegalArgumentException().isThrownBy(() -> userService.updateProfile(foundLoginId.getId(), "jyb0226jyb0226", "109876543211098765432110987654321", "jyb0226@test.com", "Glen", "인범", "INACTIVATED", "1988-01-20", "ACTIVATED"));

    }

    @Test
    void 회원_수정_실패_email_31글자_이상() throws Exception {
        User user = userRepository.save(new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26", "ACTIVATED"));

        User foundLoginId = userRepository.findById(user.getId()).orElseThrow();

        //foundLoginId = userService.updateProfile(foundLoginId.getId(), "jyb0226jyb0226", "4321", "jyb0226jyb0226jyb0226123@test.com", "Glen", "인범", "INACTIVATED", "1988-01-20", "ACTIVATED");

        assertThatIllegalArgumentException().isThrownBy(() -> userService.updateProfile(foundLoginId.getId(), "jyb0226jyb0226", "4321", "jyb0226jyb0226jyb0226123@test.com", "Glen", "인범", "INACTIVATED", "1988-01-20", "ACTIVATED"));

    }

    @Test
    void 회원_수정_실패_name_11글자_이상() throws Exception {
        User user = userRepository.save(new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26", "ACTIVATED"));

        User foundLoginId = userRepository.findById(user.getId()).orElseThrow();

        //foundLoginId = userService.updateProfile(foundLoginId.getId(), "jyb0226", "4321", "jyb0226@test.com", "Glen", "낙엽이흐드러지는가을저녁", "INACTIVATED", "1988-01-20", "ACTIVATED");

        assertThatIllegalArgumentException().isThrownBy(() -> userService.updateProfile(foundLoginId.getId(), "jyb0226", "4321", "jyb0226@test.com", "Glen", "낙엽이흐드러지는가을저녁", "INACTIVATED", "1988-01-20", "ACTIVATED"));

    }
}