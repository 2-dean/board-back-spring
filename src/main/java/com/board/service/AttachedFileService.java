package com.board.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AttachedFileService {

    Long saveFile(MultipartFile file) throws IOException;
    Resource downloadFile(Long idx);
}
