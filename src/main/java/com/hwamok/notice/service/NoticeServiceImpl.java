package com.hwamok.notice.service;

import com.hwamok.notice.domain.Notice;
import com.hwamok.notice.domain.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor // 생성자를 만들어줌, 파라미터를 아큐먼트라고도 함 Args
public class NoticeServiceImpl implements NoticeService{

    private final NoticeRepository noticeRepository; //@RequiredArgsConstructor 추가해야 빨간줄 사라짐

    @Override
    public Notice create(String title, String content) {
        return noticeRepository.save(new Notice(title, content));
    }
}
