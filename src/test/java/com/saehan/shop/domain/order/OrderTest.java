package com.saehan.shop.domain.order;

import com.saehan.shop.domain.item.Item;
import com.saehan.shop.domain.item.ItemCategory;
import com.saehan.shop.domain.item.ItemRepository;
import com.saehan.shop.domain.item.ItemSellStatus;
import com.saehan.shop.domain.user.Role;
import com.saehan.shop.domain.user.User;
import com.saehan.shop.domain.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
class OrderTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext
    EntityManager em;

    public Item createItem(){
        return Item.builder()
                .itemNm("테스트 상품")
                .itemCategory(ItemCategory.electrical)
                .itemDetail("테스트 상품 상세 설명")
                .itemSellStatus(ItemSellStatus.SELL)
                .price(10000)
                .stockNumber(100)
                .build();
    }

    @Test
    @DisplayName("영속성 전이 테스트")
    public void cascadeTest(){
        User user = new User("1", "1","1", Role.USER);
        userRepository.save(user);
        Order orders = new Order(user, LocalDateTime.now(), OrderStatus.ORDER);

        for(int i = 0; i < 3; i++) {
            Item item = this.createItem();
            itemRepository.save(item);
            OrderItem orderItem = new OrderItem(item, orders, 10000, 10);

            orders.getOrderItems().add(orderItem);
        }

        orderRepository.saveAndFlush(orders);
        em.clear();

        Order savedOrder = orderRepository.findById(orders.getId()).orElseThrow(EntityNotFoundException::new);
        assertEquals(3, savedOrder.getOrderItems().size());
    }

    public Order createOrder(){
        User user = new User("1", "1","1", Role.USER);
        userRepository.save(user);

        Order order = new Order(user, LocalDateTime.now(), OrderStatus.ORDER);

        for(int i = 0; i < 3; i++){
            Item item = Item.builder()
                    .itemNm("테스트 상품")
                    .itemCategory(ItemCategory.electrical)
                    .itemDetail("테스트 상품 상세 설명")
                    .itemSellStatus(ItemSellStatus.SELL)
                    .price(10000)
                    .stockNumber(100)
                    .build();

            itemRepository.save(item);
            OrderItem orderItem = new OrderItem(item, order, 10000, 10);

            order.getOrderItems().add(orderItem);
        }
        orderRepository.save(order);
        return order;
    }

    @Test
    @DisplayName("고아객체 제거")
    public void orphanRemovalTest(){
        Order order =this.createOrder();
        order.getOrderItems().remove(0);
        em.flush();
    }



}