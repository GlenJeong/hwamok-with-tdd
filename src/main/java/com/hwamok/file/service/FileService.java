package com.hwamok.file.service;

import com.hwamok.file.domain.File;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    File upload(MultipartFile imageProfile);

    void delete(long id);
}
