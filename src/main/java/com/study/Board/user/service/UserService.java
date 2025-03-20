package com.study.Board.user.service;

import com.study.Board.user.dto.RegisterDto;
import com.study.Board.user.dto.UpdateDto;
import com.study.Board.user.entity.User;
import com.study.Board.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(RegisterDto userDto, String profileImagePath) {

        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        User newUser = userDto.toEntity(encodedPassword, profileImagePath);

        userRepository.save(newUser);
    }

    public void updateUser(UpdateDto userDto, String profileImagePath) {
        User findUser = findCurrentUser();

        findUser.updateInfo(userDto, profileImagePath);

        CustomUserDetails updatedUserDetails = new CustomUserDetails(findUser);

        UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
                updatedUserDetails,
                null,
                updatedUserDetails.getAuthorities()
        );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(newAuth);
        SecurityContextHolder.setContext(context);
    }


    public User findCurrentUser() {
        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        User findUser = userRepository.findByUserId(currentUserId)
                .orElseThrow(() -> new UsernameNotFoundException("User with ID " + currentUserId + " not found."));
        return findUser;
    }

}

