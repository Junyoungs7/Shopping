package com.saehan.shop.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class testController {

    @GetMapping("/regitem")
    public String reg(){
        return "admin/ItemReg";
    }
}
