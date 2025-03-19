package com.study.Board.user.controller;

import com.study.Board.user.dto.RegisterDto;
import com.study.Board.user.dto.UpdateDto;
import com.study.Board.user.entity.User;
import com.study.Board.user.service.CustomUserDetails;
import com.study.Board.user.service.ProfileService;
import com.study.Board.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ProfileService profileService;

    @GetMapping("/{userId}")
    public String userDetails(Model model) {
        model.addAttribute("currentUser", userService.findCurrentUser());
        return "userDetails";
    }

    @GetMapping("/{userId}/edit")
    public String userEdit(Model model) {
        model.addAttribute("userDto", new UpdateDto());
        model.addAttribute("currentUser", userService.findCurrentUser());
        return "userEdit";
    }

    @PatchMapping("/{userId}")
    public String editResponse(Model model,
                               @ModelAttribute("userDto") @Valid UpdateDto userDto,
                               BindingResult bindingResult,
                               @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
                               HttpServletRequest request
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.out.println("Error: " + error.getDefaultMessage());
            }
            return "userEdit";
        }

        String profileImagePath = profileService.saveProfileImage(profileImage);

        userService.updateUser(userDto, profileImagePath,request);
        return "redirect:/";
    }
}
