package com.study.Board.post.entity;

import com.study.Board.post.dto.PostDto;
import com.study.Board.user.entity.User;

import jakarta.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 300)
    private String title;

    @Lob
    private String content;

    @Column(nullable = false)
    private String category;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDING;

    private String postProfileImage;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostImage> images = new ArrayList<PostImage>();

    @Builder
    private Post(String title, String content, String category, User user, String postProfileImage) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.user = user;
        this.postProfileImage = postProfileImage;
    }

    public void updatePost(PostDto postDto){
        this.title = postDto.getTitle();
        this.content = postDto.getContent();
        this.category = postDto.getCategory();
    }

}
