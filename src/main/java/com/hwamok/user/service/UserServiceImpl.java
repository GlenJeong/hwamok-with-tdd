package com.hwamok.user.service;

import com.hwamok.core.exception.ExceptionCode;
import com.hwamok.core.exception.HwamokException;
import com.hwamok.notice.domain.NoticeRepository;
import com.hwamok.user.domain.User;
import com.hwamok.user.domain.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    // @PostConstruct 어노테이션이 붙은 메소드는 해당 클래스의 인스턴스가 생성된 후에 자동으로 호출되는 메소드
    // 주로 초기화 작업을 수행하는 데 사용
    // 이 메소드는 스프링 빈의 라이프사이클에서 인스턴스가 생성된 후에 호출되기 때문에,
    // 빈이 사용될 준비가 완료된 상태에서 초기화 작업을 수행할 수 있다.
    public void init() throws Exception {
        if (!userRepository.findByLoginId("jyb0120").isPresent()) {
            userRepository.save(new User("jyb0120", passwordEncoder.encode("1234"), "jyb0120@gmail.com", "GlennJeong", "InBeom", "ACTIVATED", "1988-02-26"));
        }
    }
    // init 메소드가 빈이 초기화될 때 호출되도록 되어 있다.
    // 이 메소드는 데이터베이스에 특정 사용자(jyb0226)가 존재하는지 확인하고,
    // 없다면 새로운 사용자를 생성하여 저장한다.
    // 즉, 이 메소드를 통해 초기 데이터를 데이터베이스에 주입하는 역할을 한다.
    //이 메소드를 사용하는 이유는 테스트 환경에서 자동으로 테스트 데이터를 생성하기 위함이거나,
    // 애플리케이션이 처음 실행될 때 초기 데이터를 생성하기 위함일 수 있다.
    // 이는 애플리케이션을 처음 실행할 때 필요한 기본 데이터나 설정을 자동으로 주입하는 등의 용도로 활용된다.
    //

    @Override
    public User create(String loginId, String password, String email, String nickname, String name, String userStatus, String birthday) throws Exception {

        return userRepository.save(new User(loginId, passwordEncoder.encode(password), email, nickname, name, userStatus, birthday));
    }

    @Override
    public User getUser(long id) {
        return userRepository.findById(id).orElseThrow(() -> new HwamokException(ExceptionCode.NOT_FOUND_USER));
    }

    @Override
    public User updateProfile(String loginId, String password, String email, String nickname, String name, String userStatus, String birthday) throws Exception {

        User user = userRepository.findByLoginId(loginId).orElseThrow(() -> new HwamokException(ExceptionCode.NOT_FOUND_USER));
        userRepository.save(new User(user.getLoginId(), passwordEncoder.encode(password), email, nickname, name, userStatus, birthday));
        return user;

    }

    @Override
    public User updateProfile(long id, String loginId, String password, String email, String nickname, String name, String userStatus, String birthday) throws Exception {

        User user = userRepository.findById(id).orElseThrow(() -> new HwamokException(ExceptionCode.NOT_FOUND_USER));
        user.updateUser(loginId, passwordEncoder.encode(password), email, nickname, name, userStatus, birthday);
        return user;

    }

    @Override
    public void withdraw(long id) {

        User user = userRepository.findById(id).orElseThrow(() -> new HwamokException(ExceptionCode.NOT_FOUND_USER));
        user.withdraw();
        userRepository.save(user);

    }
}
