package com.study.Board.post.repository;

import com.study.Board.post.entity.Post;
import com.study.Board.post.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {

    List<PostImageRepository> findByPost(Post post);
}