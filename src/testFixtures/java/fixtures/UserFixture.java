package fixtures;

import com.hwamok.user.domain.User;
public class UserFixture {
    public static User createUser(String loginId, String password) throws Exception {
        return new User(loginId, password, "jyb1624@test.com", "Glenn", "정인범", "ACTIVATED", "1988-02-26");
    }

}
