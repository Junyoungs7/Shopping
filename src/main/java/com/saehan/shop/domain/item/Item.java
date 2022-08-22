package com.saehan.shop.domain.item;

import com.saehan.shop.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
public class Item extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public void update(ItemSellStatus itemSellStatus, int stockNumber, int price){
        this.itemSellStatus = itemSellStatus;
        this.stockNumber = stockNumber;
        this.price = price;
    }
}
