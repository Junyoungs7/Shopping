package com.saehan.shop.web;

import com.saehan.shop.config.auth.LoginUser;
import com.saehan.shop.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String mainHome(Model model, @LoginUser SessionUser user){

        if(user != null){
            model.addAttribute("userName", user.getName());
        }
        return "mainHome";
    }




}
