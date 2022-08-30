package com.saehan.shop.service.cart;

import com.saehan.shop.domain.cart.CartItem;
import com.saehan.shop.domain.cart.CartItemRepository;
import com.saehan.shop.domain.item.Item;
import com.saehan.shop.domain.item.ItemCategory;
import com.saehan.shop.domain.item.ItemRepository;
import com.saehan.shop.domain.item.ItemSellStatus;
import com.saehan.shop.domain.user.Role;
import com.saehan.shop.domain.user.User;
import com.saehan.shop.domain.user.UserRepository;
import com.saehan.shop.web.dto.CartItemDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
class CartServiceTest {

    @Autowired
    CartService cartService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    public Item saveItem(){
        Item item = Item.builder()
                .itemSellStatus(ItemSellStatus.SELL)
                .itemCategory(ItemCategory.Hand)
                .itemDetail("item 상세 설명")
                .itemNm("item")
                .price(10000)
                .stockNumber(100)
                .build();
        return itemRepository.save(item);
    }

    public User saveUser(){
        User user = User.builder()
                .role(Role.ADMIN)
                .name("user")
                .email("test@test.com")
                .picture("test")
                .build();
        return userRepository.save(user);
    }

    @Test
    @DisplayName("장바구니 담기 테스트")
    public void addCart(){
        Item item = saveItem();
        User user = saveUser();

        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setItemId(item.getId());
        cartItemDto.setCount(10);

        Long cartItemId = cartService.addCart(cartItemDto, user.getEmail());

        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
        assertEquals(cartItem.getItem().getId(), item.getId());
        assertEquals(cartItemDto.getCount(), cartItem.getCount());
    }
}