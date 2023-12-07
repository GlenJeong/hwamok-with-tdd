package com.hwamok.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

import static com.hwamok.core.Preconditions.notNull;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private static final long EXPIRE_MINUTES = 30_000; // 30초
    private static final long EXPIRE_DAYS = 86_400_000; // 24시간

    @Value("${jwt.secretKey}")
    private String secretKey;


    @Override
    public String issue(long userId, JwtType type) { //issue: 사용자 ID 및 토큰 타입을 받아 JWT 토큰을 생성합니다.
        // JwtType type 열거형으로, ACCESS 또는 REFRESH 중 하나
        // jwt 토큰은 2가지 종류로 나눰
        // accessToken - 만료주기가 짧은 토큰, 로그인할 때 사용, 발행한 후 몇 분
        // 짧은 이유는 통신하면서 해킹으로 노출될 것을 방지하기 위해서 짧게 사용해서 계속 발행함
        // refreshToken - 만료주기가 긴 토큰, 새로운 accessToekn을 발행할 때 사용
        //
        // claims를 string으로 변환해줌

        Date now = new Date();

        Date expiration = null;

        switch (type) { // 열거형 타입에 따라 발급 기간이 달라짐, long 단위로 계산 30초, 24시간
            case ACCESS ->  expiration = new Date(now.getTime() + EXPIRE_MINUTES); // accessToken
            case REFRESH -> expiration = new Date(now.getTime() + EXPIRE_DAYS); // refreshToken
        }

        // 들어오는 Type에 따라 accessToken과 refreshToken 구분하여 생성함
        return Jwts.builder()
                .issuer("hwamok") // 토큰 발급자 "hwamok"
                .subject("hwamok jwt api token") // 토큰 내용 "hwamok jwt api token"
                .issuedAt(now) // 현재 날짜를 발행일로 설정
                .expiration(expiration) // accessToken이면 현재 날짜시간 + 30초,  refreshToken이면 현재 날짜시간 + 24시간
                .claim("id", userId) // 사용자 ID를 토큰에 추가
                .signWith(generateSecretKey()) // 비밀 키를 사용하여 토큰을 서명
                .compact(); // .compact(); jwt를 string으로 변환해주는 역할을 함
    }

    @Override
    public String resolveToken(HttpServletRequest request) {
        // resolveToken: HTTP 요청에서 토큰을 추출합니다.
        // HTTP 요청의 Authorization 헤더에서 JWT 토큰을 추출
        String token = request.getHeader("Authorization");
        // 요청 HTTP의 헤더에 Authorization에서 Bearer 로 시작하는 토큰을 추출하여 tokne에 저장

        if(token != null && token.startsWith("Bearer")){ // tokne이 null이 아니거나
            // Bearer로 시작한다면 토큰을 추출
            return token.substring(7);
        } else {
            return null; // 찾을 수 없으면 null
        }
//        System.out.println("accessTokne = " + token);
//        System.out.println("accessTokne.substring(7) = " + token.substring(7));

    }

    @Override
    public boolean validate(String token) { // validate: 토큰의 유효성을 검사, 유효성을 검사할 JWT 토큰
        try {
            notNull(Jwts.parser().verifyWith(generateSecretKey()).build().parse(token).getPayload());
            // parser가 해석하는 역할을 한다. generateSecretKey을 decryptWith로 복호화하는 데
            // getPayload가 아이디, 만료 값을 가져옴
            // Jwts.parser()를 사용하여 토큰을 구문 분석
            // verifyWith 메서드를 사용하여 토큰의 서명을 확인
            return false; // 확인이 성공하면 메서드는 false를 반환
        }catch (ExpiredJwtException | NullPointerException e) { // or 익셉션 2개 동신에 사용
           return true; // 예외(예: 만료된 토큰)가 발생하면 true를 반환
        }
    }

    @Override
    public Long getId(String token) { // JWT 토큰에서 사용자 ID를 추출, 매개변수 token은 ID를 추출할 JWT 토큰
        return Jwts.parser().verifyWith(generateSecretKey()).build().parseSignedClaims(token).getPayload().get("id", Long.class);
    } // 사용자 ID를 Long 형식으로 반환
    // Jwts.parser()를 사용하여 토큰을 구문 분석
    // verifyWith 메서드를 사용하여 토큰의 서명을 확인


    // private 시작하는 메서드는 맨 밑에 추가한다.
    private SecretKey generateSecretKey() { // 문자열을 바이트 배열로 변환하는 부분
        // 이 바이트 배열을 기반으로 HMAC-SHA 키를 생성하고 반환
        return Keys.hmacShaKeyFor(secretKey.getBytes());
        // secretKey.getBytes() 문자열 형태의 시크릿 키를 바이트 배열로 반환
        // 변환된 바이트 배열을 사용하여 HMAC SHA 알고리즘에 기반한 시크릿 키를 생성
      // HMAC (Hash-based Message Authentication Code) 알고리즘을 사용하여 시크릿 키를 생성하는 역할
        // 해시 함수와 비밀 키를 이용하여 메시지에 서명하는 메커니즘으로, 메시지 무결성을 보장
        // 우리만의 키값이 hwamokJwtSecretKey20231102PM1057
        // JWT(JSON Web Token)에서 사용되는 비밀 키를 생성하는 메서드
        // JWT는 토큰 기반의 인증 시스템에서 사용되며, 토큰을 발행하고 확인하기 위해서는
        // 서버와 클라이언트 간에 공유되는 비밀 키가 필요
        // 이 생성된 비밀 키는 JWT를 서명(Sign)하거나, 서명된 JWT를 확인할 때 사용
        // JWT의 서명은 해당 토큰이 유효하고 변경되지 않았음을 검증하는 데에 활용
    }
}
