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
import java.util.Map;

@Service
@Profile({"local", "default"})
@RequiredArgsConstructor
public class LocalS3ServiceImpl implements S3Service {

    //@Value
    //외부 설정 파일이나 환경 변수의 값을 읽어와 해당 필드에 주입하는 역할을 수행한다.
    //accessKey와 secretKey는 외부에 드러나면 안되므로, 설정파일에 실제 값을 두고, 그 값을 주입하는 형식으로 사용한다.
    //${}로 감싸진 부분은 프로퍼티 표현식(Property Expression)으로, 설정파일에서 해당 속성에 대한 속성 값을 찾아서 주입한다.
    //즉, yml파일의 cloud > aws > credentials > access-key 계층구조에 존재하는 속성 값이 accessKey에 주입된다.
    //secretKey와 region의 경우도 동일하다.

    @Override
    public List<Pair<String, String>> upload(List<MultipartFile> multipartFiles) {
        return List.of(Pair.of("originalname", "savedname"));
    }
    // amazonS3.getUrl(bucket, originalFilename).toString();
    // getURl()을 통해 파일이 저장된 URL을 return 해주고, 이 URL로 이동 시 해당 파일이 오픈됨
    // (버킷 정책 변경을 하지 않았으면 파일은 업로드 되지만 해당 URL로 이동 시 accessDenied 됨)

    @Override
    public boolean delete(String savedFileName) {
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
