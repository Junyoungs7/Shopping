package com.saehan.shop.service.posts;

import com.saehan.shop.domain.item.Item;
import com.saehan.shop.domain.item.ItemCategory;
import com.saehan.shop.domain.item.ItemRepository;
import com.saehan.shop.domain.item.ItemSellStatus;
import com.saehan.shop.service.items.ItemService;
import com.saehan.shop.web.dto.ItemSaveRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityNotFoundException;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
class ItemServiceTest {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemService itemService;


    @Test
    @DisplayName("등록 테스트")
    public void save(){
        String itemNm = "등록 상품";
        String itemDetail = "등록 상품 상세 설명";
        ItemCategory itemCategory = ItemCategory.electrical;
        ItemSellStatus itemSellStatus = ItemSellStatus.SELL;
        int price = 10000;
        int stockNumber = 100;

        ItemSaveRequestDto itemSaveRequestDto = new ItemSaveRequestDto(itemNm, itemDetail, price, stockNumber, itemSellStatus, itemCategory);
        Long getId = itemService.save(itemSaveRequestDto);

        Item savedItem = itemRepository.findById(getId).orElseThrow(EntityNotFoundException::new);

        Assertions.assertThat(itemNm).isEqualTo(savedItem.getItemNm());
        Assertions.assertThat(price).isEqualTo(savedItem.getPrice());

    }

}