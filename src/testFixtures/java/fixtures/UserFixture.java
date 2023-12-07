package fixtures;

import com.hwamok.user.domain.User;
public class UserFixture {

    public static User createUser() throws Exception {
        return new User("jyb1624", "1234", "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26");
    }

    public static User createUser(String loginId, String password, String email, String nickname, String name, String userStatus, String birthday) throws Exception {
        return new User(loginId, password, email, nickname, name, userStatus, birthday);
    }

    public static User createUser(String loginId, String password) throws Exception {
        return new User(loginId, password, "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26");
    }

}
