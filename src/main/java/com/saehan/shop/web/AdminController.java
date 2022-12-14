package com.saehan.shop.web;

import com.saehan.shop.domain.item.Item;
import com.saehan.shop.domain.item.ItemCategory;
import com.saehan.shop.domain.item.ItemSellStatus;
import com.saehan.shop.service.items.ItemService;
import com.saehan.shop.service.order.OrderService;
import com.saehan.shop.web.dto.ItemFormDto2;
import com.saehan.shop.web.dto.ItemSearchRequestDto;
import com.saehan.shop.web.dto.OrderListUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final ItemService itemService;
    private final OrderService orderService;

    @GetMapping("/admin")
    public String admin(){
        return "adminHome";
    }

    @GetMapping("/admin/itemSave")
    public String itemSave(Model model){
//        ItemFormDto test = ItemFormDto.builder().itemNm("test").itemDetail("test1").price(0).build();
        ItemFormDto2 test = new ItemFormDto2();
        model.addAttribute("itemFormDto", test);
        return "admin/ItemReg";
    }

    @PostMapping("/admin/itemSave")
    public String itemSave(@Valid ItemFormDto2 itemFormDto, BindingResult bindingResult,
                           Model model, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList){

        System.out.println(itemFormDto.toString());
        System.out.println(itemImgFileList.get(0).getOriginalFilename());

        if(bindingResult.hasErrors()){
            return "admin/ItemReg";
        }

        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null) {
            model.addAttribute("errorMessage", "????????? ?????? ???????????? ?????? ?????? ????????????.");
            return "admin/ItemReg";
        }

        try{
            itemService.saveItem(itemFormDto, itemImgFileList);
        }catch (Exception e){
            model.addAttribute("errorMessage", "?????? ?????? ??? ????????? ?????????????????????.");
            return "admin/ItemReg";
        }
        return "redirect:/admin/itemSave";
    }

    @GetMapping("/admin/item/{itemId}")
    public String itemDtl(@PathVariable("itemId") Long itemId, Model model){

        try{
            ItemFormDto2 itemFormDto2 = itemService.getItemDtl(itemId);
            model.addAttribute("itemFormDto", itemFormDto2);
        }catch (EntityNotFoundException e){
            model.addAttribute("errorMessage", "???????????? ?????? ???????????????.");
            model.addAttribute("itemFormDto", new ItemFormDto2());
            return "admin/ItemReg";
        }
        return "admin/ItemReg";
    }
    @PostMapping("/admin/item/{itemId}")
    public String itemUpdate(@Valid ItemFormDto2 itemFormDto2, BindingResult bindingResult,
                             @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList, Model model){
        if(bindingResult.hasErrors()){
            return "admin/ItemReg";
        }
        if(itemImgFileList.get(0).isEmpty() && itemFormDto2.getId() == null){
            model.addAttribute("errorMessage", "????????? ?????? ???????????? ?????? ?????? ????????????.");
            return "admin/ItemReg";
        }

        try{
            itemService.updateItem(itemFormDto2, itemImgFileList);
        }catch (Exception e){
            model.addAttribute("errorMessage", "?????? ?????? ??? ????????? ?????????????????????.");
            return "admin/ItemReg";
        }
        return "adminHome";
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

    @GetMapping(value = {"/admin/orderList","/admin/orderList/{page}"})
    public String orderList(@PathVariable("page")Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<OrderListUserDto> orderListUserDtos = orderService.userOrderList(pageable);
        model.addAttribute("orders", orderListUserDtos);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 10);
        return "admin/orderList";
    }
}
