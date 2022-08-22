package com.saehan.shop.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String admin(){
        return "adminHome";
    }

    @GetMapping("/admin/itemSave")
    public String itemSave(){
        return "admin/ItemReg";
    }

    @GetMapping("/admin/itemList")
    public String itemList(){
        return "admin/ItemList";
    }
}
