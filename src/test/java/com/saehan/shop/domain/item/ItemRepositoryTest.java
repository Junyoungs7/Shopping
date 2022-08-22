package com.saehan.shop.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    public void createItem(){
        for(int i = 1; i <= 5; i++){
            Item item = new Item();
            Item savedItem = itemRepository.save(Item.builder()
                            .itemNm("테스트 상품"+i)
                            .itemCategory(ItemCategory.electrical)
                            .itemDetail("테스트 상품 상세 설명"+i)
                            .itemSellStatus(ItemSellStatus.SELL)
                            .price(10000+i)
                            .stockNumber(100+i)
                            .build());
            System.out.println(savedItem.toString());
        }

    }

    @Test
    @DisplayName("상품 저장 및 상품명으로 조회 테스트")
    public void saveAndRead(){
        this.createItem();

        List<Item> itemList = itemRepository.findByItemNm("테스트 상품3");
        for(Item item : itemList){
            System.out.println(item.toString());
            Assertions.assertThat(item.getPrice()).isEqualTo(10003);
        }
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    public void delete(){
        this.createItem();
        Item deleteItem = itemRepository.findByItemNmAndItemDetail("테스트 상품3", "테스트 상품 상세 설명3");

        itemRepository.delete(deleteItem);

        List<Item> itemList = itemRepository.findByItemNm("테스트 상품");
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("상품 업데이트 테스트")
    public void update(){
        this.createItem();

        ItemSellStatus itemSellStatus = ItemSellStatus.SOLD_OUT;
        int price = 20000;
        int stockNumber = 20;

        Item updateItem = itemRepository.findByItemNmAndItemDetail("테스트 상품3", "테스트 상품 상세 설명3");
        updateItem.update(itemSellStatus, stockNumber, price);
        itemRepository.save(updateItem);

        List<Item> itemList = itemRepository.findByItemNm("테스트 상품");
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }
}