package com.study.Board.post.service;

import com.study.Board.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class Base64Service {

    private final String SAVE_DIR = "C:\\Users\\user\\Desktop\\image\\postImage\\";
    private final String UPLOAD_DIR = "/image/postImage/";

    public String saveImage(String base64Image) throws IOException {
        byte[] imageBytes = Base64.getDecoder().decode(base64Image.split(",")[1]);

        String fileName = UUID.randomUUID() + ".png";
        Path filePath = Paths.get(SAVE_DIR + fileName);

        Files.write(filePath, imageBytes);

        String imageUrl = UPLOAD_DIR + fileName;
        return imageUrl;
    }
}
