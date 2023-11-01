package fixtures;

import com.hwamok.user.domain.User;

import java.text.ParseException;

public class UserFixture {

    public static User createUser() throws ParseException {
        return new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26");
    }

    public static User createUser(String loginId, String password, String email, String nickname, String name, String userStatus, String birthday) throws ParseException {
        return new User(loginId, password, email, nickname, name, userStatus, birthday);
    }
}
