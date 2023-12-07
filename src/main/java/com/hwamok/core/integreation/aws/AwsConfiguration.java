package com.hwamok.core.integreation.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfiguration { // 주로 파일 업로드 및 다운로드, 객체 저장 등의 S3 관련 작업에 활용
    // AWS Simple Storage Service의 줄임말로 파일 서버의 역할을 하는 서비스
    // 프로젝트 개발 중 파일을 저장하고 불러오는 작업이 필요한 경우에 프로젝트 내부 폴더에 저장할 수 있지만, AWS S3를 사용하여 파일을 관리할 수도 있음

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    public AmazonS3 amazonS3() { // 컨트롤 알트 브이
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
        // BasicAWSCredentials는 AWSCredentials인터페이스의 구체클래스로, 다형성을 활용해 객체를 생성
        // AWS 자격 증명(AWS Credentials)을 나타내는 객체
        // accessKey, secretKey, region 필드로부터 값을 가져와서 AWSCredentials 객체를 생성

        // AWS S3 서비스에 대한 클라이언트를 구성하고 빌드하기 위한 빌더 클래스
        // standard() 메서드를 호출하여 기본 빌더를 생성한 뒤, 자격 증명 객체와 region을 지정
        // build() 메서드를 호출하여 AmazonS3 객체를 생성하여 반환
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .withRegion(region)
                .build();
    }
}
