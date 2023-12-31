package com.hwamok.file.domain;

import com.hwamok.core.exception.ExceptionCode;
import com.hwamok.support.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import static com.hwamok.core.Preconditions.require;
import static com.hwamok.core.exception.HwamokException.validate;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File extends BaseEntity {
    @Column(length = 50, nullable = false)
    private String originalFileName;

    @Column(length = 50, nullable = false)
    private String savedFileName;

    @Column(length = 11, nullable = false)
    @Enumerated(EnumType.STRING)
    private FileStatus status = FileStatus.ACTIVATED;

    public File(String originalFileName, String savedFileName) {
        require(Strings.isNotBlank(originalFileName));
        require(Strings.isNotBlank(savedFileName));

        System.out.println("public class File extends BaseEntity originalFileName = " + originalFileName);
        System.out.println("public class File extends BaseEntity originalFileName = " + savedFileName);

        validate(originalFileName.length() <= 50, ExceptionCode.OVER_LENGTH_ORIGINALFILENAME);
        validate(savedFileName.length() <= 50, ExceptionCode.OVER_LENGTH_SAVEDFILENAME);

        this.originalFileName = originalFileName;
        this.savedFileName = savedFileName;
    }

    public void delete() {
        this.status=FileStatus.INACTIVATED;
    }
}
