package com.hwamok;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootTest // full application config을 로드해서 통합 테스트를 진행하기 위한 어노테이션이다
    // @SpringBootTest는 @SpringBootApplication을 찾아가서 모든 Bean을 로드하게 됨
// 통합 테스트를 제공하는 가장 기본적인 테스트 애노테이션으로
// 애플리케이션이 실행 될 때의 설정을 임의로 바꿀수도 있고 여러 단위 테스트를 하나의 통합 테스트로 수행 할 수도 있다.
// 해당 애노테이션의 컴포넌트 스캔 범위는 Bean 전체이다. 즉 애플리케이션이 실행할 당시 스캔되는 범위와 동일하다.
// 그렇기에 최대한 실제와 유사한 환경에서 테스트를 할 수 있다는 장점이 있다.
// 하지만, 이 말은 반대로 애플리케이션의 모든 설정을 가져오기 때문에 애플리케이션의 범위가 넓을수록 테스트가 느려질 수 밖에 없고,
// 이는 단위테스트의 의미를 희석하기에 단위테스트에 적절하지는 않다.
    // 그래서 전체를 테스트하는 @SpringBootTest보단 각 단계마다 단위 테스트를 진행하여 테스트하는 것이 바람직하다.

    // F I R S T 원칙
    // Fast: 테스트 코드의 실행은 빠르게 진행되어야 한다.
    // Independent: 독립적인 테스트가 가능해야 한다.
    // Repeatable: 테스트는 매번 같은 결과를 만들어야 한다.
    // self-Validating: 테스트는 그 자체로 실행하여 결과를 확인 할 수 있어야 한다.
    // Timely : 단위 테스트는 비즈니스 코드가 완성되기 전에 구성하고 테스트가 가능해야 한다.
    //          코드가 완성되기 전부터 테스트가 따라와야 한다는 TDD의 원칙을 담고 있다.
class ApplicationTests {

    @Test
    void contextLoads() {
    }

}
