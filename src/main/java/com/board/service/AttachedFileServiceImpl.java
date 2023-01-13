package com.board.service;

import com.board.domain.AttachedFile;
import com.board.mapper.AttachedFileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachedFileServiceImpl implements AttachedFileService {

    private final AttachedFileMapper attachedFileMapper;
    private String path = "/Users/ddu/Study/testboard/src/main/resources/static/fileRepository/";

    @Override
    public Long saveFile(MultipartFile file) throws IOException {

        String oriFileName = file.getOriginalFilename();
        String extention = oriFileName.substring(oriFileName.lastIndexOf("."));
        String saveFileName = UUID.randomUUID().toString() + extention;

        String savePath = path + saveFileName;

        // 서버에 파일 저장
        file.transferTo(new File(savePath));

        // DB에 파일 저장 정보 저장
        AttachedFile attachedFile = new AttachedFile();
        attachedFile.setOriFileName(oriFileName);
        attachedFile.setSaveFileName(saveFileName);
        attachedFile.setSavePath(path);

        attachedFileMapper.saveFile(attachedFile);

        Long fileIdx = attachedFile.getIdx();

        System.out.println("file db저장 idx 값 : " + fileIdx);

        return fileIdx;

    }


}
