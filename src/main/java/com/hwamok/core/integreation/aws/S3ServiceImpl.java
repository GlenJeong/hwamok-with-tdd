package com.hwamok.core.integreation.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Profile({"dev", "prod"})
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    private final AmazonS3 amazonS3;

    @Value("${s3.bucket}")
    private String bucket;

    //@Value
    //외부 설정 파일이나 환경 변수의 값을 읽어와 해당 필드에 주입하는 역할을 수행한다.
    //accessKey와 secretKey는 외부에 드러나면 안되므로, 설정파일에 실제 값을 두고, 그 값을 주입하는 형식으로 사용한다.
    //${}로 감싸진 부분은 프로퍼티 표현식(Property Expression)으로, 설정파일에서 해당 속성에 대한 속성 값을 찾아서 주입한다.
    //즉, yml파일의 cloud > aws > credentials > access-key 계층구조에 존재하는 속성 값이 accessKey에 주입된다.
    //secretKey와 region의 경우도 동일하다.


    @Override
    public List<Pair<String, String>> upload(List<MultipartFile> multipartFiles) {
        List<Pair<String, String>> pairs = new ArrayList<>();

        multipartFiles.forEach(file -> {
            String savedFileName = createFileName(file.getOriginalFilename());
            String fileName = file.getOriginalFilename();

            ObjectMetadata objectMetadata = new ObjectMetadata();
            // ObjectMetadata 객체 생성: S3에 업로드할 파일의 메타데이터를 설정
            // 파일의 MIME 타입과 크기 등이 포함
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(file.getSize());

            try(InputStream inputStream = file.getInputStream()) {
                amazonS3.putObject(bucket, savedFileName, inputStream, objectMetadata); //putObject() 메소드가 파일을 저장해주는 메소드
            }catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "파일 업로드에 실패 하였습니다.");
            }

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
        if(amazonS3.doesObjectExist(bucket, savedFileName)) {
            amazonS3.deleteObject(new DeleteObjectRequest(bucket, savedFileName));

            return true;
        }

        return false;
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
