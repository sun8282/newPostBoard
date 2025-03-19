package com.study.Board.user.controller;

import com.study.Board.user.dto.RegisterDto;
import com.study.Board.user.service.ProfileService;
import com.study.Board.user.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;
    private final ProfileService profileService;

    @GetMapping("")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", new RegisterDto());
        return "register";
    }

    @PostMapping("")
    public String registerUser(@ModelAttribute("userDto") @Valid RegisterDto userDto,
                               BindingResult bindingResult,
                               @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) throws IOException {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        String profileImagePath = profileService.saveProfileImage(profileImage);
        userService.registerUser(userDto, profileImagePath);

        return "redirect:/login";
    }
}
