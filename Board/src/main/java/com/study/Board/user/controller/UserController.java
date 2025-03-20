package com.study.Board.user.controller;

import com.study.Board.user.dto.UpdateDto;
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
                               @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) {
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                log.error("Error: {}", error.getDefaultMessage());
                model.addAttribute("errors", bindingResult.getAllErrors());
            }
            return "userEdit";
        }

        String profileImagePath = profileService.saveProfileImage(profileImage);
        userService.updateUser(userDto, profileImagePath);

        log.info("User {} updated successfully", userDto.getUserName());

        return "redirect:/";
    }

}
