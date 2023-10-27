package com.hwamok.notice.service;

import com.hwamok.core.exception.HwamokException;
import com.hwamok.notice.domain.Notice;
import com.hwamok.notice.domain.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.hwamok.core.exception.ExceptionCode.*;

@Service
@Transactional
@RequiredArgsConstructor // 생성자를 만들어줌, 파라미터를 아큐먼트라고도 함 Args
public class NoticeServiceImpl implements NoticeService{

    private final NoticeRepository noticeRepository; //@RequiredArgsConstructor 추가해야 빨간줄 사라짐

    @Override
    public Notice create(String title, String content) {
        // throw new HwamokException(ExceptionCode.INVALID_NOTICE);
        // HwamokException 익셉션 발생함, code, message 출력됨
        // 테스트는 scratch.http에서 테스트함
        // HwamokException.validate(Strings.isNotBlank(title), INVALID_NOTICE);

       return noticeRepository.save(new Notice(title, content));
    }

    @Override
    public Notice getNotice(Long id) {
        return noticeRepository.findById(id).orElseThrow(() -> new HwamokException(NOT_FOUND_NOTICE));
    }

    @Override
    public Notice update(Long id, String title, String content) { // findById가 유일한 값이기 때문에 이걸로 조회
        Notice notice = noticeRepository.findById(id).orElseThrow(() -> new HwamokException(NOT_FOUND_NOTICE));
        notice.update(title, content);

        return notice;
    }
}
