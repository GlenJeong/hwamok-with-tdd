package com.hwamok.core.integreation.aws;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Profile({"local", "default"})
@RequiredArgsConstructor
public class LocalS3ServiceImpl implements S3Service {
    @Override
    public List<Pair<String, String>> upload(List<MultipartFile> multipartFiles) {
        List<Pair<String, String>> pairs = new ArrayList<>();

        multipartFiles.forEach(file -> {
            String savedFileName = createFileName(file.getOriginalFilename());
            String fileName = file.getOriginalFilename();

            pairs.add(Pair.of(fileName, savedFileName));
            // 업로드가 성공하면 파일의 원래 이름과 S3에 저장된 이름을 Pair로 묶어서 리스트에 추가, 이 정보는 클라이언트에 반환
        });

        return pairs;
    }
    // amazonS3.getUrl(bucket, originalFilename).toString();
    // getURl()을 통해 파일이 저장된 URL을 return 해주고, 이 URL로 이동 시 해당 파일이 오픈됨
    // (버킷 정책 변경을 하지 않았으면 파일은 업로드 되지만 해당 URL로 이동 시 accessDenied 됨)

    @Override
    public Pair<String, String> upload(MultipartFile file) {
        List<MultipartFile> multipartFiles = new ArrayList<>();
        multipartFiles.add(file);

        return this.upload(multipartFiles).get(0);
    }

    @Override
    public boolean delete(String savedFileName) {

        return true;
    }

    private String createFileName(String name) {
        return "F_" + System.currentTimeMillis() + getExtension(name);
    }

    private String getExtension(String name) {
        try {
            return name.substring(name.lastIndexOf("."));
        }catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘 못 된 형식의 파일입니다.");
        }
    }
}
