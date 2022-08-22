package com.saehan.shop.web.dto;

import com.saehan.shop.domain.item.Item;
import com.saehan.shop.domain.item.ItemCategory;
import com.saehan.shop.domain.item.ItemSellStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemSaveRequestDto {

    private String itemNm;
    private String itemDetail;
    private int price;
    private int stockNumber;
    private ItemSellStatus itemSellStatus;
    private ItemCategory itemCategory;

    @Builder
    public ItemSaveRequestDto(String itemNm, String itemDetail, int price, int stockNumber,
                              ItemSellStatus itemSellStatus, ItemCategory itemCategory){
        this.itemNm = itemNm;
        this.itemDetail = itemDetail;
        this.price = price;
        this.stockNumber = stockNumber;
        this.itemSellStatus = itemSellStatus;
        this.itemCategory = itemCategory;
    }

    public Item toEntity(){
        return Item.builder()
                .itemSellStatus(itemSellStatus)
                .itemNm(itemNm)
                .price(price)
                .stockNumber(stockNumber)
                .itemDetail(itemDetail)
                .itemCategory(itemCategory)
                .build();
    }

}
