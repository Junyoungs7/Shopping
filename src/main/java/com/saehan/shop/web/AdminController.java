package com.saehan.shop.web;

import com.saehan.shop.domain.item.Item;
import com.saehan.shop.service.items.ItemService;
import com.saehan.shop.web.dto.ItemFormDto;
import com.saehan.shop.web.dto.ItemSearchRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final ItemService itemService;

    @GetMapping("/admin")
    public String admin(){
        return "adminHome";
    }

    @GetMapping("/admin/itemSave")
    public String itemSave(Model model){
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "admin/ItemReg";
    }

    @GetMapping(value = {"/admin/itemList", "/admin/itemList/{page}"})
    public String itemList(ItemSearchRequestDto itemSearchRequestDto,
                           @PathVariable("page")Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 9);
        Page<Item> items = itemService.getAdminItemPage(itemSearchRequestDto, pageable);
        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchRequestDto);
        model.addAttribute("maxPage", 10);

        return "admin/ItemList";
    }
}
