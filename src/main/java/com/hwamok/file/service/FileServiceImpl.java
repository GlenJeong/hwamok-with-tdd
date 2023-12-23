package com.hwamok.file.service;

import com.hwamok.core.exception.ExceptionCode;
import com.hwamok.core.exception.HwamokException;
import com.hwamok.core.integreation.aws.S3Service;
import com.hwamok.file.domain.File;
import com.hwamok.file.domain.FileRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final S3Service s3Service;

    @PostConstruct
    public void init() {
        if(!fileRepository.findByOriginalFileName("originalFileName").isPresent()) {
            fileRepository.save(new File("originalFileName", "savedFileName"));
        }
    }

    @Override
    public File upload(MultipartFile imageProfile) {
        Pair pair = s3Service.upload(imageProfile);

        return fileRepository.save(new File(pair.getFirst().toString(), pair.getSecond().toString()));
    }

    @Override
    public void delete(long id) {
        File file = fileRepository.findById(id).orElseThrow(() -> new HwamokException(ExceptionCode.NOT_FOUND_FILE));
        file.delete();
    }
}
