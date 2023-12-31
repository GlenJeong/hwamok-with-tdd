package com.hwamok.api;

import com.hwamok.api.dto.user.UserCreateDto;
import com.hwamok.api.dto.user.UserUpdateDto;
import com.hwamok.core.response.ApiResult;
import com.hwamok.core.response.Result;
import com.hwamok.user.domain.User;

import com.hwamok.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Spring Security -> Spring의 하위 프레임워크
    // 사용하는 것만으로도 대부분의 보안 취약점을 개선, 그 이유는 security filter가 추가 되기 때문이다.
    // 403 에러가 발생함 권한 에러
    //
    // filter라는 개념이 가장 중요
    // 구현해서 사용함
    // 필터라는 것은 요청을 맨 앞단에서 걸러줌
    // Servlet에서 filter가 존재
    // 여러개가 중첩되어 있음
    // Servlet과 Security에서 필터는 다른 것임
    // 요청과 DispatchServlet 사이에 filter가 많이 있음, 사용자가 필요에 의해서 만들수 있음
    // 여러개의 필터가 서로 연결되어 있는 것을 filterChain이 하나 존재함.
    // filterChain안에 Security Filter를 넣어서 사용한다.
    // Security Filter는 proxy 방식으로 사용하고
    // Delegating proxy fliter가 proxy이고 Security Filter를 호출한다.
    // Security Filter 안에 UsernamePasswordFilter, jwt Filter(직접구현)를 제일 많이 사용.
    // jwt Filter 먼저 처리 후 UsernamePasswordFilter 처리
    // jwt.io url -> postman jwt bearer에서 확인 가능
    // jwt 토큰이 신뢰할 수 있는 지 확인해야 한다. 내 서버에서 발생한 토큰인지 검증해야 한다.
    // 사용자가 가지고 있는 jwt 토큰이 내 서버에서 발행한 토큰인지 확인해야 한다. 그것을 jwt filter의 역할이다.
    // UsernamePasswordFilter를 통과하면 인증된 사용자라고 인식한다.
    // 내 서버에서 발행한 키인지 확인하는 방법은 SecretKey라는 것을 가지고 있는 지 확인하는 것
    // 토큰은 사용자가 가지고 있고 SecretKey는 서버가 가지고 있음

    // SecretKey을 가지고 발행한 토큰을 확인한다.
    // SecretKey에 사용자 정보가 있음
    // 서버에서 토큰 안에는 필요한 최소한의 정보를 담아서 보낸다. 사용자 계정을 담아서 보낸다.


    // loginId, password, email, nickname, name, userStatus, birthday

    @PostMapping // 회원 가입
    public ResponseEntity<ApiResult<?>> signupUser(@RequestBody UserCreateDto.Request request) throws Exception {
        User user = userService.create(request.getLoginId(), request.getPassword(), request.getEmail(), request.getNickname(), request.getName(), request.getUserStatus(), request.getBirthday());
        return Result.created();
    }

    @PatchMapping("/updateProfile/{id}") // 회원 수정
    public ResponseEntity<ApiResult<?>> updateProfile(@PathVariable Long id, @RequestBody UserUpdateDto.Request dto) throws Exception {
        User user = userService.updateProfile(id, dto.getLoginId(), dto.getPassword(), dto.getEmail(),dto.getNickname(), dto.getName(), dto.getUserStatus(), dto.getBirthday() );
        return Result.ok();
    }


    @GetMapping("/userOne/{id}") // 회원 조회
    public ResponseEntity<ApiResult<?>> UserOne(@PathVariable long id) {
        User user = userService.getUser(id);
        return Result.ok(user);
    }

    @DeleteMapping ("/withdraw/{id}") // 회원 탈퇴
    public ResponseEntity<ApiResult<?>> withdrawUser(@PathVariable long id) {
        userService.withdraw(id);
        User user = userService.getUser(id);
        return Result.ok();
    }

}
