package com.hwamok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    // Spring MVC 패턴에 맞춰서 개발 <--- 아래의 순서대로 개발하면 상당히 어려운 개발 방법
    // 1. Controller 의존성이 제일 크고  --> 기능 중심의 개발
    // 2. Service
    // 3. Repository
    // 4. Entity 의존성이 제일 없음   --> 비지니스 모델 중심의 개발 --> 도메인 중심 개발
    //  의존성이 낮은 것부터 작성해야 다른 곳에 변화가 없다.

    // 뭘 리턴할지 모르니까 일단 void
    // 뭐 필요한지 모르니까 일단 비워두고

    // https://blog.toktokhan.dev/요즘-대세-clean-architecture-67b80df66c6
    // https://daryeou.tistory.com/280
    // https://techblog.woowahan.com/2647/
    // https://medium.com/mj-studio/클린아키텍처-썼는데-왜-프로젝트가-더-더러워지지-3565aaffca8c
    // 클린 아키텍쳐 살짝 바꾼 방식
    // notice - domain
    //        - service

    // api - controller

    // 아래 순서대로 해야 개발이 쉬워진다.
    // 1. Entity
    // 2. Repository
    // 3. Service
    // 4. Controller



    // 테스트 코드란 무엇일까?
    // 코드가 잘 돌아가나 확인해보는 것
    // 실행의 결과가 원하는 결과인지 보는 것
    // 인풋 값에 따라 에러가 날 수 있는 상황들을 확인

    // 내가 만든 코드가 나에게 신뢰성을 주는 것
    // 테스트 코드의 목적은 실패할 만한 상항을 계속 생각하면서
    // 코드를 견고하게 작성하는 것에 목적이 있음

    // 테스트 코드 작성 -> 실패하는 테스트
    // 어플리케이션 코드를 수정 -> 실패하지 않도록
    // 테스트를 다시 돌려서 테스트가 성공
    // 과정을 반복하는 것

    // Test Driven Development의 규칙
    // 1. 최소한의 성공하는 어플케이션 코드를 작성한다.
    // 2. 실패하는 테스트 케이스를 작성한다.
    // 3. 실패하지 않도록 어플리케이션 코드를 수정한다.
    // 4. 실패하던 테스트케이스가 성공한다.
    // 5. 테스트 코드의 중복을 최소화한다.
    // 6. 테스트 코드와 어플리케이션 코드를 리팩토링한다.
    // 7. 반복한다.

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // A 메서드에서 DB에 유저를 넣음 user id = 1
    // creat-drop
    // B 메서드에서 DB에 유저를 똑같이 넣음 user id = 1
}
