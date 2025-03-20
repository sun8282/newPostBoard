package com.study.Board.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
public class ProfileService {

    //private final String UPLOAD_DIR = "src/main/resources/static/uploads/profileImages/";
    //private final String UPLOAD_DIR = "/uploads/profileImages/";
    private final String SAVE_DIR = "C:\\Users\\user\\Desktop\\image\\profileImage\\";
    private final String UPLOAD_DIR = "/image/profileImage/";
    public String saveProfileImage(MultipartFile file) {
        if(file.isEmpty()){
            return UPLOAD_DIR+"default-profile.png";
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(SAVE_DIR + fileName);

        try {
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());

            return UPLOAD_DIR+fileName;
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());

            throw new RuntimeException("에러가 발생했다.");
        }
    }
}
