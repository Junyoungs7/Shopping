package com.saehan.shop.web;

import com.saehan.shop.service.posts.ItemService;
import com.saehan.shop.web.dto.ItemSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminApiController {

    private final ItemService itemService;

    @GetMapping("/admin")
    public String admin(){
        return "adminHome";
    }

    @PostMapping("/admin/itemReg")
    public Long save(@RequestBody ItemSaveRequestDto itemSaveRequestDto){
        return itemService.save(itemSaveRequestDto);
    }



}
