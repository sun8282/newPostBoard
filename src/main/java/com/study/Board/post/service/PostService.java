package com.study.Board.post.service;

import com.study.Board.post.dto.PostDto;
import com.study.Board.post.entity.Post;
import com.study.Board.post.entity.PostImage;
import com.study.Board.post.repository.PostImageRepository;
import com.study.Board.post.repository.PostRepository;
import com.study.Board.post.util.Base64Util;
import com.study.Board.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final EntityManager entityManager;


    private final Base64Util base64Util;
    private final String SAVE_DIR = "C:\\Users\\user\\Desktop\\image\\postProfileImage\\";
    private final String UPLOAD_DIR = "/image/postProfileImage/";

    public String uploadImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return UPLOAD_DIR + "default-profile.png";
        }

        String contentType = file.getContentType();
        if (!contentType.startsWith("image/")) {
            throw new IllegalArgumentException("이미지 파일만 업로드 가능합니다.");
        }

        if (file.getSize() > 10 * 1024 * 1024) { // 최대 10MB
            throw new IllegalArgumentException("파일 크기는 10MB를 넘을 수 없습니다.");
        }

        String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path path = Paths.get(SAVE_DIR + filename);

        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());

        return UPLOAD_DIR + filename;
    }

    @Transactional
    public void createPost(PostDto postDto, User currentUser, String profileImagePath) {
        Post newPost = postDto.toEntity(currentUser, profileImagePath);

        List<PostImage> images = new ArrayList<>();

        for (String imageUrl : postDto.getImageUrls()) {
            imageUrl = imageUrl.replace("[", "").replace("]", "").trim();
            PostImage newPostImage = postDto.toEntity(imageUrl, newPost);

            images.add(newPostImage);
        }

        newPost.updateImage(images);
        newPost.persist(entityManager);
    }

    public PostDto findById(Long postId) {
        Post post = getPost(postId);
        PostDto postDto = new PostDto();
        postDto.setTitle(post.getTitle());
        postDto.setCategory(post.getCategory());
        postDto.setContent(post.getContent());
        return postDto;
    }

    public boolean isNotWirteUser(Long postId, Long id) {
        Post post = getPost(postId);
        return id != post.getUser().getId();
    }

    public void updatePost(Long postId, PostDto postDto) {
        Post post = getPost(postId);
        post.updatePost(postDto);
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    private Post getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("post를 찾을 수 없습니다."));
        return post;
    }

    public Page<Post> getAllPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return postRepository.findAll(pageable);
    }

    private Page<Post> getPostsByCategory(String category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return postRepository.findByCategory(category, pageable);
    }

    public Page<Post> isPossibleCategory(String category, int page, int size) {
        if (category == null) {
            return getAllPosts(page, size);
        }
        return getPostsByCategory(category, page, size);
    }


}
