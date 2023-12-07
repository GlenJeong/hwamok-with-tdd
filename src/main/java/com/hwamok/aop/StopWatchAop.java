package com.hwamok.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component // 이 어노테이션이 있어야 Spring에서 자기네들 입맛대로 조금 개조한 프레임워크를 사용 할 수 있다.
@Slf4j
public class StopWatchAop {
    // AOP : Aspect Oriented Programming
    // Aspect --> 프레임워크 하위로 들어감
    // Spring에서 자기네들 입맛대로 조금 개조해서 사용
    // 사용하는 방식이 정해져있음

    // execute 구현 --> 이름이 관례
    //
    @Around("execution(* com.hwamok..*(..))") // execute가 동작하는 범위를 지정, 패키지 단위부터 함수 한개 단위까지 지정이 가능하다.
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable { // execute() 매개 변수로 통해서 들어옴
        // 범위를 지정할 수 있다.
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            // 메서드 실행 create() 메서드가 실행
//            joinPoint.toString(); // 메서드 정보가 찍힌다.
            System.out.println("Start Stopwatch : " + joinPoint.toString());
            return joinPoint.proceed(); // 문법이다.
        }finally {
            stopWatch.stop();
            System.out.println("End Stopwatch : " + joinPoint.toString()
            +"During Time : " + stopWatch.getTotalTimeSeconds());
        }
    }
}
