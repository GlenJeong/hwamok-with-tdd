package com.hwamok.config;

import com.hwamok.security.DefaultAuthenticationProvider;
import com.hwamok.security.JwtService;
import com.hwamok.security.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Configuration
@EnableWebSecurity
// 과거에는 WebSecurityConfigurerAdapter를 상속하여 configure 메소드를 오버라이딩 하여 구현하였지만,
// 현재는 @Bean어노테이션을 이용하여 이용하고 있다.
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtService jwtService;

    // Filter -> UsernamePasswordFilter -> 인증, 인가 완료
    // 인증 인가 되었다면 표시를 해줘야 한다.
    // UsernamePasswordFilter를 지나갈 때 로그인을 시도한 유저가
    // AccessToken이 발급이 된다면
    // 다음 요청부터는 AccessToken을 헤더에 담아서 요청을 보냄
    // AccessToken을 해독해서 Id값을 가져와야함
    // UsernamePasswordFilter를 지날 때 ID값을 userRepository에서 검색해서
    // User 객체로 Security에게 이 객체는 로그인한 객체라고 알려줘야 한다.
    // Security는 SecurityContextHolder안에 Authentication이라는 Sessoin과 비슷한 저장 공간이 있는 데 여기에 Id 값을 넣어줘야함


    // 디테일하게 필터를 적용
    // POST를 담아주는 배열을 만들어줌

    private final static String[] POST_PERMIT_MATCHERS = Arrays.asList( // 자바의 정석 11-27에 나옴 Arrays.asList
            "/**"
    ).toArray(String[]::new);
    // 이 URL은 POST 메서드로 수행되는 요청에 대해 권한이 허용
    // "/auth/login" 및 "/user" 문자열 요소를 포함하는 리스트를 생성


    @Bean // IoC 컨테이너에 등록
    public PasswordEncoder passwordEncoder() {
        log.info("패스워드 인코딩");
        BCryptPasswordEncoder aaa = new BCryptPasswordEncoder();
        System.out.println("aaa.encode(\"1234\") = " + aaa.encode("1234"));
        System.out.println("aaa.encode(\"1234\") = " + aaa.encode("1234"));

        return new BCryptPasswordEncoder();
    }



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    } //authenticationManager 메서드는 AuthenticationManager 빈을 생성합니다. 이 메서드는 AuthenticationConfiguration을 주입받아 인증 관리자를 생성합니다.

    // ProviderManager는 AuthenticationManager의 가장 일반적인 구현체이다. ProviderManager는 AuthenticationProvider 목록을 위임 받는다.
    //각 AuthenticationProvider는 인증 성공, 실패, 결정할 수 없음을 나타낼 수 있고, 나머지 AuthenticationProvider가 결정을 할 수 있도록 전달한다.

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic(h-> h.disable()) // 메서드를 통해 HTTP 기본 인증을 비활성화
                .sessionManagement(session -> // 세션 생성 안함, jwt를 사용할 거라서
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // JWT 기반의 인증을 사용할 때 일반적인 설정
                .authorizeHttpRequests(request -> // 모든 요청에 대한 검사를 한다.
                                request.requestMatchers(HttpMethod.POST, POST_PERMIT_MATCHERS).permitAll()
                        // authorizeHttpRequests() 메서드를 통해 HTTP 요청에 대한 인가를 구성함, 여기서는 POST 메서드이면서 POST_PERMIT_MATCHERS에 해당하는 URL에 대해 모든 권한을 허용
 //                              .requestMatchers("/notice/**").authenticated() // 인증된 authenticated 인증된 사용자만
//                               .requestMatchers("/admin/**").hasRole("admin")
                ) // requestMatchers url를 매칭해준다. /**은 /이하 모든 url이라는 의미
                // /user/** user 아래 모든 허용, url은 세부적으로 작성해야 함
                // .formLogin(login -> login.) // theymleaf사용 할 때
                .authorizeHttpRequests(request -> request.anyRequest().permitAll()) //anyRequest()에 대한 요청은 인증되어야 한다고 설정
                .authenticationProvider(new DefaultAuthenticationProvider())
                .csrf(c->c.disable()) //csrf()를 통해 CSRF(Cross-Site Request Forgery) 보호를 비활성화함, 일반적으로 RESTful API에서는 CSRF를 사용하지 않는 것이 일반적
                .addFilterBefore(new JwtTokenFilter(jwtService), UsernamePasswordAuthenticationFilter.class);        // url은 세부적인 것부터 위에서 아래로 적용, JWT 필터를 적용
        return http.build();

        // Notice는 로그인이 되어야 사용 가능
        // User는 모두가 사용 가능
    }
}


