package com.saehan.shop.web;

import com.saehan.shop.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class NoticeController {

    private final PostsService postsService;

    @GetMapping("/posts")
    public String postsRead(Model model){
        model.addAttribute("postsList", postsService.findAllDesc());
        return "notice/postsList";
    }

    @GetMapping("/posts/postsSave")
    public String postsSave(){
        return "notice/postsSave";
    }

}
