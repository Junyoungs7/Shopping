package com.saehan.shop.web.dto;

import com.saehan.shop.domain.item.Item;
import com.saehan.shop.domain.item.ItemCategory;
import com.saehan.shop.domain.item.ItemSellStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class ItemFormDto {

    private Long id;

    @NotBlank(message = "상품명은 필수 입력값입니다.")
    private String itemNm;

    @NotNull(message = "가격은 필수 입력값입니다.")
    private Integer price;

    @NotNull(message = "상품 정보는 필수 입력값입니다.")
    private String itemDetail;

    @NotNull(message = "재고는 필수 입력값입니다.")
    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;
    private ItemCategory itemCategory;

    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();

    private List<Long> itemImgIds = new ArrayList<>();

    @Builder
    public ItemFormDto(String itemNm, Integer price, String itemDetail,
                       Integer stockNumber, ItemSellStatus itemSellStatus, ItemCategory itemCategory){

        this.itemCategory = itemCategory;
        this.itemDetail  = itemDetail;
        this.itemNm = itemNm;
        this.price = price;
        this.stockNumber = stockNumber;
        this.itemSellStatus = itemSellStatus;
    }

    public Item toEntity(){
        return Item.builder().itemNm(itemNm).itemDetail(itemDetail).itemCategory(itemCategory)
                .stockNumber(stockNumber).price(price).itemSellStatus(itemSellStatus).build();
    }

    private static ModelMapper modelMapper = new ModelMapper();

    public Item createItem(){
        modelMapper.getConfiguration().setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);

        return modelMapper.map(this, Item.class);
    }

    public static ItemFormDto of(Item item){
        modelMapper.getConfiguration().setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);
        return modelMapper.map(item, ItemFormDto.class);
    }
}
