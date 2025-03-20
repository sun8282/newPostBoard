package com.study.Board.user.entity;

import com.study.Board.post.entity.Post;
import com.study.Board.user.dto.UpdateDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "app_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false, unique = true, length = 100)
    private String userEmail;

    @Column(nullable = false, length = 50)
    private String userName;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 50, unique = true)
    private String userId;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<Post>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role = Role.USER;

    private String profileImage;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(role);
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Builder
    private User(String userEmail, String userName, String password, String userId, String profileImage) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.password = password;
        this.userId = userId;
        this.profileImage = profileImage;
    }

    public void updateInfo(UpdateDto userDto, String profileImagePath) {
        this.userName = userDto.getUserName();
        this.userEmail = userDto.getUserEmail();
        this.profileImage = profileImagePath;
    }
}
