package com.hwamok.user.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest // Jpa에 필요한 bean들을 사용 할 수 있다. 우리는 JpaRepository를 사용, 톰캣이 구동되지 않아 더 빠름
// Transactional를 포함하고 있음
@AutoConfigureTestDatabase(replace = NONE)
class UserRepositoryTest {


    @Autowired
    private UserRepository userRepository;

    @Test
    void 회원_가입_성공() throws Exception {
        User user = userRepository.save(new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26"));

        assertThat(user.getId()).isNotNull();
        assertThat(user.getLoginId()).isEqualTo("jyb1624");
        assertThat(user.getPassword()).isEqualTo("1234");
        assertThat(user.getEmail()).isEqualTo("jyb1624@test.com");
        assertThat(user.getNickname()).isEqualTo("Glenn");
        assertThat(user.getName()).isEqualTo("정인범");
        assertThat(user.getUserStatus()).isEqualTo(UserStatus.ACTIVATED);
        //assertThat(user.getBirthday()).isEqualTo("1988-02-26");

    }


    @Test
    void 회원_단건_조회_성공() throws Exception {
        User user = userRepository.save(new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26"));

        User foundUserId = userRepository.findById(user.getId()).orElseThrow();

        assertThat(foundUserId.getId()).isEqualTo(user.getId());


    }

    @Test
    void 회원_단건_조회_성공_존재하지_않는_회원() throws Exception {
        User user = userRepository.save(new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26"));

        // User foundUserId = userRepository.findById(-1L).orElseThrow(()->new IllegalArgumentException());

        assertThatIllegalArgumentException().isThrownBy(()->userRepository.findById(-1L).orElseThrow(()->new IllegalArgumentException()));


    }

    @Test
    void 회원_탈퇴_성공() throws Exception {
        User user = userRepository.save(new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26"));

        User foundUserId = userRepository.findByLoginId(user.getLoginId()).orElseThrow();

        userRepository.delete(foundUserId);
        userRepository.delete(user);

        System.out.println("foundUserId.getLoginId() = " + foundUserId.getLoginId());
        System.out.println("user.getLoginId() = " + user.getLoginId());


        assertThat(user.getId()).isNotNull();


    }


    @Test
    void 회원_가입_실패_loginId_11글자() throws Exception {
        // User user = userRepository.save(new User("jyb1624jyb1624", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26", "ACTIVATED"));

//        assertThat(user.getId()).isNotNull();

        assertThatIllegalArgumentException().isThrownBy(()->userRepository.save(new User("jyb1624jyb1624", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26")));
    }


    @Test
    void 회원_가입_실패_email_31글자() throws Exception {
        // User user = userRepository.save(new User("jyb1624", "1234", "jyb1624jyb1624jyb16241234@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26", "ACTIVATED"));

        //        assertThat(user.getId()).isNotNull();

        assertThatIllegalArgumentException().isThrownBy(()->userRepository.save(new User("jyb1624", "1234", "jyb1624jyb1624jyb16241234@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26")));

    }

    @Test
    void 회원_가입_실패_name_11글자() throws Exception {
        // User user = userRepository.save(new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", "선선한바람이코끝을스치며", "ACTIVATED", "1988-02-26", "ACTIVATED"));

        //        assertThat(user.getId()).isNotNull();

        assertThatIllegalArgumentException().isThrownBy(()->userRepository.save(new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", "선선한바람이코끝을스치며", "ACTIVATED", "1988-02-26")));

    }

    @ParameterizedTest
    @NullAndEmptySource
    void 회원_가입_실패_loginId_빈값_혹은_null(String loginId)throws Exception{
//        User user = userRepository.save(new User(loginId, "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26", "ACTIVATED"));

        //        assertThat(user.getId()).isNotNull();

        assertThatIllegalArgumentException()
                .isThrownBy(()->userRepository.save(new User(loginId, "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26")));


    }

    @ParameterizedTest
    @NullAndEmptySource
    void 회원_가입_실패_password_빈값_혹은_null(String password)throws Exception{
//        User user = userRepository.save(new User("jyb1624", password, "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26", "ACTIVATED"));

        //        assertThat(user.getId()).isNotNull();

        assertThatIllegalArgumentException()
                .isThrownBy(()->userRepository.save(new User("jyb1624", password, "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26")));


    }

    @ParameterizedTest
    @NullAndEmptySource
    void 회원_가입_실패_email_빈값_혹은_null(String email)throws Exception{
//        User user = userRepository.save(new User("jyb1624", "1234", email, "Glenn", "정인범", "ACTIVATED", "1988-02-26", "ACTIVATED"));

        //        assertThat(user.getId()).isNotNull();

        assertThatIllegalArgumentException()
                .isThrownBy(()->userRepository.save(new User("jyb1624", "1234", email, "Glenn", "정인범", "ACTIVATED", "1988-02-26")));


    }

    @ParameterizedTest
    @NullAndEmptySource
    void 회원_가입_실패_name_빈값_혹은_null(String name)throws Exception{
//        User user = userRepository.save(new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", name, "ACTIVATED", "1988-02-26", "ACTIVATED"));

        //        assertThat(user.getId()).isNotNull();

        assertThatIllegalArgumentException()
                .isThrownBy(()->userRepository.save(new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", name, "ACTIVATED", "1988-02-26")));


    }
}