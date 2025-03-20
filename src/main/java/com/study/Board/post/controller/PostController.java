package com.study.Board.post.controller;

import com.study.Board.post.dto.PostDto;
import com.study.Board.post.service.PostService;
import com.study.Board.user.entity.User;
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

import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @GetMapping("/new")
    public String showPostForm(Model model) {
        model.addAttribute("postDto", new PostDto());
        return "createPost";
    }

    @PostMapping
    public String createPost(@ModelAttribute PostDto postDto,
                             @RequestParam(value = "postProfileImage", required = false) MultipartFile postProfileImage) throws IOException {
        String profileImagePath = postService.uploadImage(postProfileImage);
        postService.createPost(postDto, userService.findCurrentUser(), profileImagePath);
        return "redirect:/";
    }

    @GetMapping("/{postId}")
    public String postDetails(Model model, @PathVariable("postId") Long postId) {
        PostDto postDto = postService.findById(postId);
        model.addAttribute("postDto", postDto);
        model.addAttribute("postId", postId);
        return "postDetails";
    }

    @PatchMapping("/{postId}")
    public String editResponse(Model model,
                               @ModelAttribute("postDto") @Valid PostDto postDto,
                               BindingResult bindingResult,
                               @PathVariable("postId") Long postId) {
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                log.error("Validation Error: {}", error.getDefaultMessage());
            }
            return "/{postId}/edit";
        }

        postService.updatePost(postId, postDto);

        log.info("Post {} updated successfully", postId);

        return "postDetails";
    }

    @DeleteMapping("/{postId}")
    public String deleteResponse(Model model, @PathVariable("postId") Long postId) {
        User user = userService.findCurrentUser();
        if (postService.isNotWirteUser(postId, user.getId())) {
            return "redirect:/posts/{postId}?authentication=no";
        }

        postService.deletePost(postId);

        return "redirect:/";
    }

    @GetMapping("/{postId}/edit")
    public String postEdit(Model model, @PathVariable("postId") Long postId) {
        User user = userService.findCurrentUser();
        if (postService.isNotWirteUser(postId, user.getId())) {
            return "redirect:/posts/{postId}?authentication=no";
        }

        PostDto postDto = postService.findById(postId);
        model.addAttribute("postId", postId);
        model.addAttribute("postDto", postDto);

        return "postEdit";
    }

}
