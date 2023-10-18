package com.hwamok.notice.domain;

import com.hwamok.core.Preconditions;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 파라메터가 없는 생성자는 기본 생성자가 public이라 protected로 변경
public class Notice {
    //컨트롤 쉽프트 티 누르면 테스트코딩이 자동 완성됨
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10)
    private String title;

    private String content;

    private Instant createdAt=Instant.now();

    public Notice(String title, String content) {
        // 의미있는 함수를 이용해서 사용 required
        // 검증을 할 때는 긍정문은 값이 있다.
        // 부정문은 값이 null이거나 빈 값
        // if문이나 true나 false일 때 긍정문으로 사용해서 작성한다.
        // isBlank보단 isNotBlank로 작성해야 한다. 긍정문으로 작성하는 것이 좋다.
        Preconditions.require(Strings.isNotBlank(title));
        Preconditions.require(Strings.isNotBlank(content));

        this.title = title;
        this.content = content;
    }
}
