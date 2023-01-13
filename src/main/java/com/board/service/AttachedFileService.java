package com.board.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AttachedFileService {

    Long saveFile(MultipartFile file) throws IOException;
}
