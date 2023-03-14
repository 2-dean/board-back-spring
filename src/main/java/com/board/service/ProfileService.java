package com.board.service;

import com.board.mapper.ProfileMapper;
import com.board.model.ProfileModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProfileService {
    private final S3FileUploadService s3FileUploadService;
    private final ProfileMapper profileMapper;

    public ProfileService(S3FileUploadService s3FileUploadService, ProfileMapper profileMapper) {
        this.s3FileUploadService = s3FileUploadService;
        this.profileMapper = profileMapper;
    }


    public ResponseEntity<String> profileSave(ProfileModel profileModel, MultipartFile multipartFile) {
        try {
            if (multipartFile != null) {
                profileModel.setProfileURL(s3FileUploadService.upload(multipartFile));
            }
            profileMapper.saveProfile(profileModel);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
