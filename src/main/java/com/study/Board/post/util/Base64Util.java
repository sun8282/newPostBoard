package com.study.Board.post.util;

import com.study.Board.global.exception.ImageProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class Base64Util {

    private final String SAVE_DIR = "C:\\Users\\user\\Desktop\\image\\postImage\\";
    private final String UPLOAD_DIR = "/image/postImage/";

    public String saveImage(String base64Image) throws IOException {

        try {
            byte[] imageBytes = Base64.getDecoder().decode(base64Image.split(",")[1]);

            String fileName = UUID.randomUUID() + ".png";
            Path filePath = Paths.get(SAVE_DIR + fileName);

            Files.write(filePath, imageBytes);

            String imageUrl = UPLOAD_DIR + fileName;
            return imageUrl;
        } catch (Exception e) {
            throw new ImageProcessingException("이미지가 저장되지 않았습니다.");
        }
    }
}
