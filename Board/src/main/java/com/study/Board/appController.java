package com.study.Board;

import com.study.Board.post.entity.Post;
import com.study.Board.post.service.PostService;
import com.study.Board.user.entity.User;
import com.study.Board.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class appController {

    private final UserService userService;
    private final PostService postService;

    @GetMapping("/")
    public String showHome(@RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "6") int size,
                           @RequestParam(value = "categoryId", required = false) String category,
                           Model model) {
        Page<Post> posts = postService.isPossibleCategory(category, page, size);

        User findUser = userService.findCurrentUser();

        model.addAttribute("posts", posts);
        model.addAttribute("currentPage", posts.getNumber());
        model.addAttribute("totalPages", posts.getTotalPages());

        model.addAttribute("currentUser", findUser);
        return "index";
    }

}
