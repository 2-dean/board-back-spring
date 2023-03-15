package com.board.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.board.domain.AttachedFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3FileUploadService {
    // 버킷 이름 동적 할당
    @Value("${cloud.aws.s3.bucket}")
    private String S3Bucket;

    // AmazonS3 Client는 S3 전송객체를 만들 때 필요한 클래스
    private final AmazonS3Client amazonS3Client;

    // 파일명
    private static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public AttachedFile upload(MultipartFile multipartFile) throws Exception {
        log.info("/upload 요청들어옴");
        //List<String> imagePathList = new ArrayList<>();

        log.info("1");
        String originalName = multipartFile.getOriginalFilename(); // 파일 이름
        log.info("originalName: " + originalName) ;
        // 확장자 추출
        String ext = originalName.substring(originalName.lastIndexOf('.'));
        log.info("확장자 ext : " + ext );
        // 파일이름 암호화
        final String saveFileName = getUuid() + ext;
        log.info("saveFileName : " + saveFileName );

        long size = multipartFile.getSize(); // 파일 크기
        log.info("2");


        // ObjectMetadata : Amazon S3에 저장되는 객체 메타데이터를 나타냅니다
        ObjectMetadata objectMetaData = new ObjectMetadata();
        log.info("3");
        objectMetaData.setContentType(multipartFile.getContentType());
        log.info("4");
        objectMetaData.setContentLength(size);

        // S3에 업로드
        amazonS3Client.putObject(
                new PutObjectRequest(S3Bucket, saveFileName, multipartFile.getInputStream(), objectMetaData)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );
        log.info("5");
        String imagePath = amazonS3Client.getUrl(S3Bucket, originalName).toString(); // 접근가능한 URL 가져오기
        //imagePathList.add(imagePath); //이미지 여러개 업로드시
        log.info("imagePath : " + imagePath);

        log.info("/upload 완료");
        //return new ResponseEntity<>(imagePathList, HttpStatus.OK);

        // DB에
        AttachedFile attachedFile = new AttachedFile();
        attachedFile.setOriFileName(originalName);
        attachedFile.setSaveFileName(saveFileName);
        attachedFile.setSavePath(imagePath);
        return attachedFile;
    }





    /*public String upload(MultipartFile uploadFile) throws IOException {
        log.info("[ S3FileUploadService - upload ] 실행");
        String origName = uploadFile.getOriginalFilename();
        String url;
        log.info("origName : " + origName );

        try {
            // 확장자를 찾기 위한 코드
            final String ext = origName.substring(origName.lastIndexOf('.'));
            log.info("ext : " + ext );
            // 파일이름 암호화
            final String saveFileName = getUuid() + ext;
            log.info("saveFileName : " + saveFileName );

            // 파일 객체 생성
            // System.getProperty => 시스템 환경에 관한 정보를 얻을 수 있다. (user.dir = 현재 작업 디렉토리를 의미함)
            File file = new File(System.getProperty("user.dir") + saveFileName);
            log.info("System.getProperty(\"user.dir\") : " + System.getProperty("user.dir") );

            // 파일 변환
            uploadFile.transferTo(file);
            // S3 파일 업로드
            uploadOnS3(saveFileName, file);
            // 주소 할당
            url = defaultUrl + saveFileName;
            // 파일 삭제
            file.delete();
        } catch (StringIndexOutOfBoundsException e) {
            url = null;
        }
        return url;
    }

    private static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    private void uploadOnS3(final String findName, final File file) {
        log.info("[ S3FileUploadService - uploadOnS3 실행 ]");

        // AWS S3 전송 객체 생성
        final TransferManager transferManager = new TransferManager(this.amazonS3Client);
        // 요청 객체 생성
        final PutObjectRequest request = new PutObjectRequest(bucket, findName, file);
        // 업로드 시도
        final Upload upload =  transferManager.upload(request);

        try {
            upload.waitForCompletion();
        } catch (AmazonClientException amazonClientException) {
            log.error(amazonClientException.getMessage());
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }*/
}