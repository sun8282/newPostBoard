package com.study.Board.post.controller;

import com.study.Board.post.service.Base64Service;
import com.study.Board.post.util.Base64Util;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostApiController {

    private final Base64Service base64Service;

    @PostMapping("/upload-image")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestBody Map<String, String> request) {

        try {
            String base64Image = request.get("image");
            if (base64Image == null || base64Image.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "이미지가 없습니다."));
            }

            String imageUrl = base64Service.saveImage(base64Image);
            return ResponseEntity.ok(Map.of("imageUrl", imageUrl));

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "이미지 저장 실패"));
        }
    }
}



