package com.hwamok.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean // IoC 컨테이너에 등록
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic(h-> h.disable()) //
                .sessionManagement(session -> // 세션 생성 안함, jwt를 사용할 거라서
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> // 모든 요청에 대한 검사를 한다.
                        request.requestMatchers("/user/**").permitAll()
                               .requestMatchers("/notice/**").permitAll() // 인증된 authenticated 인증된 사용자만
                               .requestMatchers("/admin/**").hasRole("admin")
                ) // requestMatchers url를 매칭해준다. /**은 /이하 모든 url이라는 의미
                // /user/** user 아래 모든 허용, url은 세부적으로 작성해야 함
               // .formLogin(login -> login.) // theymleaf사용 할 때
                .csrf(c->c.disable());

        // url은 세부적인 것부터 위에서 아래로 적용
        return http.build();

        // Notice는 로그인이 되어야 사용 가능
        // User는 모두가 사용 가능
    }
}
