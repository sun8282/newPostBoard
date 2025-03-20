package com.study.Board.user.service;

import com.study.Board.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ProfileService {

    //private final String UPLOAD_DIR = "src/main/resources/static/uploads/profileImages/";
    //private final String UPLOAD_DIR = "/uploads/profileImages/";
    private final String SAVE_DIR = "C:\\Users\\user\\Desktop\\image\\profileImage\\";
    private final String UPLOAD_DIR = "/image/profileImage/";
    public String saveProfileImage(MultipartFile file) throws IOException {
        if(file.isEmpty()){
            return UPLOAD_DIR+"default-profile.png";
        }
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(SAVE_DIR + fileName);

        Files.createDirectories(path.getParent());

        Files.write(path, file.getBytes());
        return UPLOAD_DIR+fileName;
    }
}
