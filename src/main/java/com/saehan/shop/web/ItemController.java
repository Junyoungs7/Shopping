package com.saehan.shop.web;

import com.saehan.shop.service.items.ItemService;
import com.saehan.shop.web.dto.ItemFormDto2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/item/{itemId}")
    public String itemDtl(@PathVariable("itemId") Long itemId, Model model){
        ItemFormDto2 itemFormDto = itemService.getItemDtl(itemId);
        model.addAttribute("item", itemFormDto);
        return "admin/itemDtl";
    }
}
