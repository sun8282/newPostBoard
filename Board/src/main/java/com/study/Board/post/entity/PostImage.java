package com.study.Board.post.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class PostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    private PostImage(String imageUrl, Post post) {
        this.imageUrl = imageUrl;
        this.post = post;
    }

}
