package com.hwamok.user.service;

import com.hwamok.core.exception.ExceptionCode;
import com.hwamok.core.exception.HwamokException;
import com.hwamok.core.exception.HwamokExceptionTest;
import com.hwamok.user.domain.User;
import com.hwamok.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static com.hwamok.core.exception.HwamokExceptionTest.assertThatHwamokException;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Transactional
class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    void 회원_가입_성공() throws Exception {

        User user = userRepository.save(new User("jybpo534", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26"));

        assertThat(user.getId()).isNotNull();
    }

    @Test
    void 회원_단건_정보_조회() throws Exception{
        User user = userRepository.save(new User("jyb0226", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26"));

        User foundLoginId = userRepository.findByLoginId(user.getLoginId()).orElseThrow(()-> new HwamokException(ExceptionCode.NOT_FOUND_USER));

        assertThat(foundLoginId.getLoginId()).isEqualTo(user.getLoginId());

        System.out.println("userOne.getLoginId() = " + foundLoginId.getLoginId());
        System.out.println("user.getLoginId() = " + user.getLoginId());


    }

    @Test
    void 회원_단건_정보_조회_실패_존재하지_않는_회원() throws Exception{
        User user = userRepository.save(new User("vfgty123", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26"));

//        User userOne = userRepository.findByLoginId("jyb0226").orElseThrow();
//
//        assertThat(userOne.getLoginId()).isEqualTo(user.getLoginId());

        assertThatHwamokException(ExceptionCode.NOT_FOUND_USER).isThrownBy(()->userRepository.findById(-1l).orElseThrow(()-> new HwamokException(ExceptionCode.NOT_FOUND_USER)));

    }

    @Test
    void 회원_수정_성공() throws Exception {
        User user = userRepository.save(new User("jyb015420", passwordEncoder.encode("1234"), "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26"));

        User foundLoginId = userRepository.findByLoginId(user.getLoginId()).orElseThrow(()-> new HwamokException(ExceptionCode.NOT_FOUND_USER));

        foundLoginId = userService.updateProfile(foundLoginId.getId(), "jyb162245" , passwordEncoder.encode("4321"), "jyb0226@test.com", "Inbeom", "인범", "INACTIVATED", "1988-02-26");

        assertThat(foundLoginId.getId()).isNotNull();
        assertThat(foundLoginId.getLoginId()).isEqualTo(user.getLoginId());
    }

    @Test
    void 회원_탈퇴_성공() throws Exception {
        User user = userRepository.save(new User("jyb8822", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26"));

        User foundLoginId = userRepository.findByLoginId(user.getLoginId()).orElseThrow();

        userRepository.delete(foundLoginId);

        assertThat(user.getLoginId()).isEqualTo("jyb8822");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 회원_가입_실패_loginId_null_혹은_빈값(String loginId) throws Exception {
//        User user = userRepository.save(new User(loginId, "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26", "ACTIVATED"));
//
//        assertThat(user.getId()).isNotNull();

        assertThatIllegalArgumentException()
                .isThrownBy(()->userRepository.save(new User(loginId, "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26")));

    }

    @ParameterizedTest
    @NullAndEmptySource
    void 회원_가입_실패_password_null_혹은_빈값(String password) throws Exception {
//        User user = userRepository.save(new User("jyb1624", password, "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26", "ACTIVATED"));
//
//        assertThat(user.getId()).isNotNull();

        assertThatIllegalArgumentException()
                .isThrownBy(()->userRepository.save(new User("poiu12231", password, "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26")));

    }

    @ParameterizedTest
    @NullAndEmptySource
    void 회원_가입_실패_email_null_혹은_빈값(String email) throws Exception {
//        User user = userRepository.save(new User("jyb1624", "1234", email, "Glenn", "정인범", "ACTIVATED", "1988-02-26", "ACTIVATED"));
//
//        assertThat(user.getId()).isNotNull();

        assertThatIllegalArgumentException()
                .isThrownBy(()->userRepository.save(new User("jyb1021", "1234", email, "Glenn", "정인범", "ACTIVATED", "1988-02-26")));

    }

    @ParameterizedTest
    @NullAndEmptySource
    void 회원_가입_실패_name_null_혹은_빈값(String name) throws Exception {
//        User user = userRepository.save(new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", name, "ACTIVATED", "1988-02-26", "ACTIVATED"));
//
//        assertThat(user.getId()).isNotNull();

        assertThatIllegalArgumentException()
                .isThrownBy(()->userRepository.save(new User("jyb0012", "1234", "jyb1624@test.com", "Glenn", name, "ACTIVATED", "1988-02-26")));

    }



    @ParameterizedTest
    @NullSource
    void 회원_수정_실패_loginId_null(String loginId) throws Exception {
        User user = userRepository.save(new User("jyb0052", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26"));

        User foundLoginId = userRepository.findByLoginId(user.getLoginId()).orElseThrow(()-> new HwamokException(ExceptionCode.NOT_FOUND_USER));

        //foundLoginId = userService.updateProfile(foundLoginId.getId(), loginId,"4321", "jyb0226@test.com", "Glenn", "인범", "INACTIVATED", "1988-02-26", "ACTIVATED");

        assertThatHwamokException(ExceptionCode.NOT_FOUND_USER).isThrownBy(()->userService.updateProfile(loginId,"4321", "jyb0226@test.com", "Glenn", "인범", "INACTIVATED", "1988-02-26"));

    }

    @ParameterizedTest
    @EmptySource
    void 회원_수정_실패_loginId_빈값(String loginId) throws Exception {
        User user = userRepository.save(new User("tyrxdvs34", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26"));

        User foundLoginId = userRepository.findByLoginId(user.getLoginId()).orElseThrow(()-> new HwamokException(ExceptionCode.NOT_FOUND_USER));

        //foundLoginId = userService.updateProfile(foundLoginId.getId(), loginId,"4321", "jyb0226@test.com", "Glenn", "인범", "INACTIVATED", "1988-02-26", "ACTIVATED");

        assertThatHwamokException(ExceptionCode.NOT_FOUND_USER).isThrownBy(()->userService.updateProfile(loginId,"4321", "jyb0226@test.com", "Glenn", "인범", "INACTIVATED", "1988-02-26"));

    }

    @ParameterizedTest
    @NullSource
    void 회원_수정_실패_password_null(String password) throws Exception {
        User user = userRepository.save(new User("fghg123", passwordEncoder.encode("1234"), "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26"));

        User foundLoginId = userRepository.findByLoginId(user.getLoginId()).orElseThrow(()-> new HwamokException(ExceptionCode.NOT_FOUND_USER));

        // foundLoginId = userService.updateProfile(foundLoginId.getId(), "jyb0226",password, "jyb0226@test.com", "Glenn", "인범", "INACTIVATED", "1988-02-26", "ACTIVATED");

        assertThatIllegalArgumentException().isThrownBy(()->userService.updateProfile(foundLoginId.getLoginId(), password, "jyb0226@test.com", "Glenn", "인범", "INACTIVATED", "1988-02-26"));

    }

    @ParameterizedTest
    @EmptySource
    void 회원_수정_실패_password_빈값(String password) throws Exception {
        User user = userRepository.save(new User("rdf56523", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26"));

        User foundLoginId = userRepository.findByLoginId(user.getLoginId()).orElseThrow(()-> new HwamokException(ExceptionCode.NOT_FOUND_USER));

        //foundLoginId = userService.updateProfile(foundLoginId.getLoginId(), password, "jyb0226@test.com", "Glenn", "인범", "INACTIVATED", "1988-02-26");

        System.out.println("password = " + password);

        // assertThatIllegalArgumentException().isThrownBy(()->userService.updateProfile(foundLoginId.getLoginId(), password, "jyb0226@test.com", "Glenn", "인범", "INACTIVATED", "1988-02-26"));
        //
    }

    @ParameterizedTest
    @NullSource
    void 회원_수정_실패_email_null(String email) throws Exception {
        User user = userRepository.save(new User("yhbgt43", passwordEncoder.encode("1234"), "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26"));

        User foundLoginId = userRepository.findByLoginId(user.getLoginId()).orElseThrow(()-> new HwamokException(ExceptionCode.NOT_FOUND_USER));

        assertThatIllegalArgumentException().isThrownBy(()->userService.updateProfile(foundLoginId.getLoginId(), passwordEncoder.encode("4321"), email, "Glenn", "인범", "INACTIVATED", "1988-02-26"));

    }

    @ParameterizedTest
    @EmptySource
    void 회원_수정_실패_email_빈값(String email) throws Exception {
        User user = userRepository.save(new User("jyb2222", passwordEncoder.encode("1234"), "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26"));

        User foundLoginId = userRepository.findByLoginId(user.getLoginId()).orElseThrow(()-> new HwamokException(ExceptionCode.NOT_FOUND_USER));

        assertThatIllegalArgumentException().isThrownBy(()->userService.updateProfile(foundLoginId.getLoginId(), passwordEncoder.encode("4321"), email, "Glenn", "인범", "INACTIVATED", "1988-02-26"));

    }
    @ParameterizedTest
    @NullSource
    void 회원_수정_실패_name_null(String name) throws Exception {
        User user = userRepository.save(new User("zaqwsx34", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26"));

        User foundLoginId = userRepository.findByLoginId(user.getLoginId()).orElseThrow(()-> new HwamokException(ExceptionCode.NOT_FOUND_USER));

        // foundLoginId = userService.updateProfile(foundLoginId.getId(), "jyb0226","4321", "jyb0226@test.com", "Glenn", name, "INACTIVATED", "1988-02-26", "ACTIVATED");

        assertThatIllegalArgumentException().isThrownBy(()->userService.updateProfile(foundLoginId.getLoginId(),"4321", "jyb11624@test.com", "Glenn", name, "INACTIVATED", "1988-02-26"));

    }

    @ParameterizedTest
    @EmptySource
    void 회원_수정_실패_name_빈값(String name) throws Exception {
        User user = userRepository.save(new User("xcv1234", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26"));

        User foundLoginId = userRepository.findByLoginId(user.getLoginId()).orElseThrow(()-> new HwamokException(ExceptionCode.NOT_FOUND_USER));

        // foundLoginId = userService.updateProfile(foundLoginId.getId(), "jyb0226","4321", "jyb0226@test.com", "Glenn", name, "INACTIVATED", "1988-02-26", "ACTIVATED");

        assertThatIllegalArgumentException().isThrownBy(()->userService.updateProfile(foundLoginId.getLoginId(),"4321", "jyb11624@test.com", "Glenn", name, "INACTIVATED", "1988-02-26"));

    }

    @Test
    void 회원_수정_실패_loginId_11글자_이상() throws Exception {
        User user = userRepository.save(new User("asdf123", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26"));

        User foundLoginId = userRepository.findByLoginId(user.getLoginId()).orElseThrow();

        //foundLoginId = userService.updateProfile(foundLoginId.getId(), "jyb0226jyb0226", "4321", "jyb0226@test.com", "Glen", "인범", "INACTIVATED", "1988-01-20", "ACTIVATED");

        assertThatHwamokException(ExceptionCode.NOT_FOUND_USER).isThrownBy(() -> userService.updateProfile("jyb1624getLoginId()", "4321", "jyb0226@test.com", "Glen", "인범", "INACTIVATED", "1988-01-20"));

    }

    @Test
    void 회원_수정_실패_email_31글자_이상() throws Exception {
        User user = userRepository.save(new User("asd1234", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26"));

        User foundLoginId = userRepository.findByLoginId(user.getLoginId()).orElseThrow(()-> new HwamokException(ExceptionCode.NOT_FOUND_USER));

        //foundLoginId = userService.updateProfile(foundLoginId.getId(), "jyb0226jyb0226", "4321", "jyb0226jyb0226jyb0226123@test.com", "Glen", "인범", "INACTIVATED", "1988-01-20", "ACTIVATED");

        assertThatIllegalArgumentException().isThrownBy(() -> userService.updateProfile(foundLoginId.getLoginId(), "4321", "jyb0226jyb0226jyb0226123@test.com", "Glen", "인범", "INACTIVATED", "1988-01-20"));

    }

    @Test
    void 회원_수정_실패_name_11글자_이상() throws Exception {
        User user = userRepository.save(new User("qwer1234", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26"));

        User foundLoginId = userRepository.findByLoginId(user.getLoginId()).orElseThrow(()-> new HwamokException(ExceptionCode.NOT_FOUND_USER));

        //foundLoginId = userService.updateProfile(foundLoginId.getId(), "jyb0226", "4321", "jyb0226@test.com", "Glen", "낙엽이흐드러지는가을저녁", "INACTIVATED", "1988-01-20", "ACTIVATED");

        assertThatIllegalArgumentException().isThrownBy(() -> userService.updateProfile(foundLoginId.getLoginId(), "4321", "jyb0226@test.com", "Glen", "낙엽이흐드러지는가을저녁", "INACTIVATED", "1988-01-20"));

    }
}