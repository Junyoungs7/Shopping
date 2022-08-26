package com.saehan.shop.domain.item;

import com.saehan.shop.domain.BaseTimeEntity;
import com.saehan.shop.web.dto.ItemFormDto2;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
@ToString
public class Item extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(length = 500, nullable = false)
    private String itemNm;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String itemDetail;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemSellStatus itemSellStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemCategory itemCategory;

    @Builder
    public Item (String itemNm, String itemDetail, int price, int stockNumber, ItemSellStatus itemSellStatus, ItemCategory itemCategory){
        this.itemCategory = itemCategory;
        this.itemDetail = itemDetail;
        this.itemNm = itemNm;
        this.itemSellStatus = itemSellStatus;
        this.price = price;
        this.stockNumber = stockNumber;
    }

    public void update(ItemFormDto2 itemFormDto2){
        this.itemSellStatus = itemFormDto2.getItemSellStatus();
        this.stockNumber = itemFormDto2.getStockNumber();
        this.price = itemFormDto2.getPrice();
        this.itemNm = itemFormDto2.getItemNm();
        this.itemDetail = itemFormDto2.getItemDetail();
        this.itemCategory = itemFormDto2.getItemCategory();
    }
}
