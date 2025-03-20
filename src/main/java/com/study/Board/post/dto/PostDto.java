package com.study.Board.post.dto;

import com.study.Board.post.entity.Post;
import com.study.Board.post.entity.PostImage;
import com.study.Board.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class PostDto {

    @NotBlank(message = "title is mandatory")
    private String title;

    @NotBlank(message = "content is mandatory")
    private String content;

    @NotBlank(message = "category is mandatory")
    private String category;

    private MultipartFile postProfileImage;

    private List<String> imageUrls;

    public List<String> getImageUrls() {
        if (imageUrls == null) {
            return Collections.emptyList();
        }
        return imageUrls;
    }

    public Post toEntity(User currentUser, String profileImagePath) {
        return Post.builder()
                .title(title)
                .content(content)
                .category(category)
                .user(currentUser)
                .postProfileImage(profileImagePath)
                .build();
    }

    public PostImage toEntity(String imagePath, Post post) {
        return PostImage.builder()
                .imageUrl(imagePath)
                .post(post)
                .build();
    }
}
