package com.board.controller;

import com.board.model.ProfileModel;
import com.board.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("aws")
@RequiredArgsConstructor
public class AwsController {

    private final ProfileService profileService;

    @PostMapping("/save")
    public ResponseEntity saveProfile(ProfileModel profileModel, @RequestPart(value = "profile", required = false) final MultipartFile multipartFile) {
        try {
            return new ResponseEntity(profileService.profileSave(profileModel, multipartFile), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
