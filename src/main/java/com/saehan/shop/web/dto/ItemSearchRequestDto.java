package com.saehan.shop.web.dto;

import com.saehan.shop.domain.item.ItemCategory;
import com.saehan.shop.domain.item.ItemSellStatus;
import lombok.Getter;

@Getter
public class ItemSearchRequestDto {

    private String searchDateType;
    private ItemCategory itemCategory;
    private ItemSellStatus itemSellStatus;
    private String searchBy;
    private String searchQuery = "";
}
