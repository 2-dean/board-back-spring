package com.board.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AttachedFileService {

    Long saveFile(MultipartFile file) throws IOException;
    ResponseEntity<Resource> downloadFile(Long idx);
}
