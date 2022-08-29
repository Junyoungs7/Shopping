package com.saehan.shop.web.dto;

import com.saehan.shop.domain.item.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ItemSearchRequestDto {

    private String searchDateType;
    private ItemSellStatus itemSellStatus;
    private String searchBy;
    private String searchQuery = "";
}
