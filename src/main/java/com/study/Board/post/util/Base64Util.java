package com.study.Board.post.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class Base64Util {

    private static final String SAVE_DIR = "C:\\Users\\user\\Desktop\\image\\postImage\\";
    private static final String UPLOAD_DIR = "/image/postImage/";

    public static String saveBase64Image(String base64Image) throws IOException {

        String[] parts = base64Image.split(",");
        String imageData = parts[1];

        byte[] decodedBytes = Base64.getDecoder().decode(imageData);

        String fileName = "image_" + System.currentTimeMillis() + ".png";
        String filePath = SAVE_DIR + fileName;

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(decodedBytes);
        }

        return UPLOAD_DIR + fileName;
    }



}
