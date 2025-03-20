package com.study.Board.user.controller;

import com.study.Board.user.dto.RegisterDto;
import com.study.Board.user.service.ProfileService;
import com.study.Board.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;
    private final ProfileService profileService;

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", new RegisterDto());
        return "register";
    }

    @PostMapping
    public String registerUser(@ModelAttribute("userDto") @Valid RegisterDto userDto,
                               BindingResult bindingResult,
                               @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) {
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                log.error("Register Error: {}", error.getDefaultMessage());
            }
            return "register";
        }

        String profileImagePath = profileService.saveProfileImage(profileImage);
        userService.registerUser(userDto, profileImagePath);

        return "redirect:/login";
    }
}
