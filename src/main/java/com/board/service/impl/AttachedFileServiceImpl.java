package com.board.service.impl;

import com.board.domain.AttachedFile;
import com.board.mapper.AttachedFileMapper;
import com.board.service.AttachedFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachedFileServiceImpl implements AttachedFileService {

    private final AttachedFileMapper attachedFileMapper;
    private final String PATH = "/Users/ddu/Study/testboard/src/main/resources/static/fileRepository/";

    //파일업로드
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

    //파일 다운로드
    @Override
    public ResponseEntity<Resource> downloadFile(Long idx) throws FileNotFoundException {
        AttachedFile file = attachedFileMapper.downloadFile(idx);
        String fileName = file.getSaveFileName();
        System.out.println("다운로드 파일이름 : " + fileName);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(PATH+ fileName));
        String resourceFilename= resource.getFilename();
        System.out.println("resource:  " + resource);
        System.out.println("resource.getFilename: " + resourceFilename);


        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .cacheControl(CacheControl.noCache())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .body(resource);
    }


}
