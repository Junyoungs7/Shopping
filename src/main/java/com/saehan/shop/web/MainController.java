package com.saehan.shop.web;

import com.saehan.shop.config.auth.LoginUser;
import com.saehan.shop.config.auth.dto.SessionUser;
import com.saehan.shop.service.items.ItemService;
import com.saehan.shop.web.dto.ItemSearchRequestDto;
import com.saehan.shop.web.dto.MainItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ItemService itemService;

    @GetMapping("/")
    public String mainHome(ItemSearchRequestDto itemSearchRequestDto, Optional<Integer> page, Model model, @LoginUser SessionUser user){

        if(user != null){
            model.addAttribute("userName", user.getName());
        }

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchRequestDto, pageable);
        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchRequestDto);
        model.addAttribute("maxPage", 5);
        return "mainHome";
    }






}
