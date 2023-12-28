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

// @PostMapping
//    public ResponseEntity<ApiResult<?>> create(@RequestBody FileCreateDto.Request request) {
//        fileService.upload(request.getImageProfile());
//        return Result.created();
//    }
// 문제의 원인은 @RequestBody를 사용할 때 기본적으로 Content-Type이 application/json으로 설정되기 때문입니다.
// 하지만 파일을 업로드할 때는 multipart/form-data로 설정되어야 합니다.
// 따라서 @RequestBody를 사용할 때는 자동으로 파싱되는 Content-Type이 multipart/form-data가 아니라서 해당 에러가 발생하는 것입니다.
// @RequestPart는 Spring에서 멀티파트 요청에서 파일이나 파라미터를 추출하는 데 사용되는 애노테이션입니다.
// 주로 파일 업로드와 함께 사용되며, @RequestBody와 유사하지만 멀티파트 요청에서 데이터를 추출하는 데 특화되어 있습니다.
// 멀티파트 요청은 보통 파일 업로드와 관련이 있습니다. HTML 폼에서 enctype="multipart/form-data"를 사용하거나,
// REST 클라이언트에서 파일을 첨부하여 요청할 때 사용됩니다. @RequestPart를 사용하면 멀티파트 요청에서 파일과 함께 전송되는
// 다른 파라미터들을 추출할 수 있습니다.

//문제가 발생하는 이유는 @RequestPart를 사용할 때 클라이언트에서 전송되는 멀티파트 요청의 구조와 서버에서 처리하는 메서드의
// 파라미터 구조가 일치하지 않기 때문입니다.
//첫 번째 코드에서는 다음과 같이 작성되어 있습니다.
//@PostMapping
//public ResponseEntity<ApiResult<?>> create(@RequestPart FileCreateDto.Request request) {
//    fileService.upload(request.getImageProfile());
//    return Result.created();
//}
//여기서 @RequestPart FileCreateDto.Request request는 FileCreateDto.Request 객체를 요청의 일부로 받아들이려고 시도합니다.
// 그러나 클라이언트에서 실제로는 FileCreateDto.Request 객체의 imageProfile 필드만을 멀티파트로 보내고 있습니다.
// 그 결과로 서버에서는 FileCreateDto.Request 객체를 바인딩하는 데 실패하고, 요청에 필요한 request 파라미터가 없다는 에러가 발생하게 됩니다.
//두 번째 코드에서는 다음과 같이 작성되어 있습니다.
//@PostMapping
//public ResponseEntity<ApiResult<?>> create(@RequestPart MultipartFile imageProfile) {
//    fileService.upload(imageProfile);
//    return Result.created();
//}
//여기서는 @RequestPart MultipartFile imageProfile로 직접 파일만을 받아들이고 있습니다. 따라서 클라이언트에서도 멀티파트로 전송되는 것은
// 파일 하나 뿐이므로 일치하게 됩니다.
//만약 첫 번째 코드처럼 객체를 통째로 받고 싶다면 클라이언트에서도 객체 전체를 멀티파트로 보내야 합니다.
// 클라이언트 측에서는 멀티파트 요청을 구성하여 서버로 전송해야 합니다. 일반적으로 HTML 폼에서 파일 업로드를 하는 경우에 해당하는데,
// REST 클라이언트에서는 파일을 첨부하여 요청하는 방식으로 구현할 수 있습니다.
// 클라이언트 코드를 확인하고, 필요에 따라 서버 측 코드를 조정하여 클라이언트와 서버 간의 요청 및 응답 구조를 일치시키세요.

// 문제는 @PatchMapping("/{id}")에서 @RequestPart MultipartFile profilePicture를 받고 있기 때문에 발생하고 있는 것 같습니다.
// @PatchMapping을 사용할 때는 Spring에서는 기본적으로 Multipart 요청을 지원하지 않습니다.
// Multipart 요청은 주로 파일 업로드와 관련이 있기 때문에 이를 지원하지 않는 경우도 있습니다.
// PATCH 메서드는 JSON 형식의 데이터를 전송하며, 파일 업로드와 관련된 경우가 아니라면 Multipart 요청을 사용하지 않습니다.
// 따라서 PATCH 메서드에서는 @RequestBody를 사용하여 JSON 데이터를 받는 것이 일반적입니다.