package com.hwamok.security;

import com.hwamok.user.domain.User;
import com.hwamok.user.domain.UserRepository;
import fixtures.UserFixture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailServiceTest implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // loadUserByUsername=>스프링 시큐리티 처리
        try {
            User user = userRepository.save(UserFixture.createUser("jyb", "1234"));
            return new HwamokUser(user.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
