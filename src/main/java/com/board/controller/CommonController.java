package com.board.controller;

import com.board.domain.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Common", description = "기본화면")
@RestController
@RequiredArgsConstructor
public class CommonController {

    @GetMapping("/")
    public ResponseEntity<String> main() {
        return ResponseEntity.ok().body("메인페이지 안녕!");
    }

}
