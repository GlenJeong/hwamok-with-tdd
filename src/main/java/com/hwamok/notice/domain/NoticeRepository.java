package com.hwamok.notice.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    // 내가 만든 쿼리가 있다면 반드시 테스트를 해야한다.

}
