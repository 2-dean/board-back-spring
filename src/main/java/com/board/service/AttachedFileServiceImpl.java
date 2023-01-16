package com.board.service;

import com.board.domain.AttachedFile;
import com.board.mapper.AttachedFileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachedFileServiceImpl implements AttachedFileService {

    private final AttachedFileMapper attachedFileMapper;
    private final String PATH = "/Users/ddu/Study/testboard/src/main/resources/static/fileRepository/";

    @Override
    public Long saveFile(MultipartFile file) throws IOException {

        String oriFileName = file.getOriginalFilename();
        String extention = oriFileName.substring(oriFileName.lastIndexOf("."));
        String saveFileName = UUID.randomUUID().toString() + extention;

        String savePath = PATH + saveFileName;

        // 서버에 파일 저장
        file.transferTo(new File(savePath));

        // DB에 파일 저장정보 저장
        AttachedFile attachedFile = new AttachedFile();
        attachedFile.setOriFileName(oriFileName);
        attachedFile.setSaveFileName(saveFileName);
        attachedFile.setSavePath(PATH);

        attachedFileMapper.saveFile(attachedFile);

        Long fileIdx = attachedFile.getIdx();

        System.out.println("file db저장 idx 값 : " + fileIdx);

        return fileIdx;

    }

    @Override
    public ResponseEntity<Resource> downloadFile(Long idx) {
        AttachedFile file = attachedFileMapper.downloadFile(idx);
        String fileName = file.getSaveFileName();
        System.out.println("다운로드 파일이름 : " + fileName);

        Resource resource = new FileSystemResource(PATH+ fileName);
        String resourceFilename= resource.getFilename();
        System.out.println("resource:  " + resource);
        System.out.println("resource.getFilename: " + resource.getFilename());

        HttpHeaders httpHeaders = new HttpHeaders();
        try {
            httpHeaders.add("Content-Disposition", "attachment; filename=" +
                    new String(resourceFilename.getBytes(StandardCharsets.UTF_8), "ISO-8859-1"));
            httpHeaders.add("Content-Disposition", "attachment; filename=" + file.getOriFileName());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(resource, httpHeaders, HttpStatus.OK);
    }


}
