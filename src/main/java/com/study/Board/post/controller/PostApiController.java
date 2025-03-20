package com.study.Board.post.controller;

import com.study.Board.global.exception.ImageProcessingException;
import com.study.Board.post.util.Base64Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;


@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostApiController {

    private final Base64Util base64Util;

    @PostMapping("/{postId}/image")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestBody Map<String, String> request) throws IOException {

        String base64Image = request.get("image");
        if (base64Image == null || base64Image.isEmpty()) {
            throw new ImageProcessingException("이미지 저장 실패");
        }

        String imageUrl = base64Util.saveImage(base64Image);
        return ResponseEntity.ok(Map.of("imageUrl", imageUrl));
    }
}



