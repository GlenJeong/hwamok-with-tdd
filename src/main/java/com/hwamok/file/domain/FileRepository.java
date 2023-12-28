package com.hwamok.file.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {
    Optional<File> findByOriginalFileName(String originalFileName);
}
