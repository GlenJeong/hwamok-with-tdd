package com.hwamok.api;

import com.hwamok.api.dto.auth.LoginDto;
import com.hwamok.auth.service.AuthService;
import com.hwamok.core.response.ApiResult;
import com.hwamok.core.response.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthLoginController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResult<?>> login(@RequestBody LoginDto.Request request) {
        Pair<String, String> pair = authService.login(request.getLoginId(), request.getPassword());
        return Result.ok(new LoginDto.Response(pair.getFirst(), pair.getSecond()));
    }

}
// NoticeServiceImpl이 IoC 컨테이너에 등록
// UserServiceImpl이 IoC 컨테이너에 등록
// AuthServiceImpl이 IoC 컨테이너에 등록
// project로 호출 할때는 NoticeService, UserService, AuthService을 호출해서 사용
// (UserServiceImpl)구현체를 호출해서 사용하면 controller가 어떻게 도착하는 지 다 알게된다.
// 컨트롤러가 너무 많은 정보를 알게 된다. 복잡성이 올라간다.
// 그래서 인터페이스를 호출해서 사용하는 이유는 최소한의 정보를 받는 것이다.
// 호출함수를 하고 어떤 함수가 리턴이 되는 지, 어떤 값이 오는 지만 컨트롤러가 알면 된다.
// 컨트롤러가 최소한의 정보만 알게해야 한다.
// @Qualifier



