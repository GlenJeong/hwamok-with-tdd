package fixtures;

import com.hwamok.notice.domain.Notice;

public class NoticeFixture {

    public static Notice createNotice() {
        return new Notice("제목", "본문");
    }

    public static Notice createNotice(String title, String content) {
        return new Notice(title, content);
    }
}
