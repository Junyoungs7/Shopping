package com.saehan.shop.service.order;

import com.saehan.shop.domain.item.Item;
import com.saehan.shop.domain.item.ItemCategory;
import com.saehan.shop.domain.item.ItemRepository;
import com.saehan.shop.domain.item.ItemSellStatus;
import com.saehan.shop.domain.order.Order;
import com.saehan.shop.domain.order.OrderItem;
import com.saehan.shop.domain.order.OrderRepository;
import com.saehan.shop.domain.order.OrderStatus;
import com.saehan.shop.domain.user.Role;
import com.saehan.shop.domain.user.User;
import com.saehan.shop.domain.user.UserRepository;
import com.saehan.shop.web.dto.OrderDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.Member;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UserRepository userRepository;

    public Item saveItem(){
        Item item = Item.builder()
                .itemNm("테스트 상품")
                .itemSellStatus(ItemSellStatus.SELL)
                .itemCategory(ItemCategory.Hand)
                .itemDetail("상품 설명")
                .price(10000)
                .stockNumber(100)
                .build();

        return itemRepository.save(item);
    }

    public User saveUser(){
        User users = User.builder()
                .email("test@test.com")
                .name("jun")
                .picture("sss")
                .role(Role.ADMIN)
                .build();

        return userRepository.save(users);
    }

    @Test
    @DisplayName("주문 테스트")
    public void order(){
        Item item = saveItem();
        User users = saveUser();

        OrderDto orderDto = new OrderDto();
        orderDto.setCount(10);
        orderDto.setItemId(item.getId());

        Long orderId = orderService.order(orderDto, users.getEmail());
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        List<OrderItem> orderItems = order.getOrderItems();

        int totalPrice = orderDto.getCount()*item.getPrice();
        assertEquals(totalPrice, order.getTotalPrice());
    }



}