package com.board.service;

import com.board.domain.AttachedFile;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface AttachedFileService {

    //파일 업로드
    Long saveFile(AttachedFile attachedFile);
    //파일 다운로드
    ResponseEntity<Resource> downloadFile(Long idx) throws FileNotFoundException;
}
