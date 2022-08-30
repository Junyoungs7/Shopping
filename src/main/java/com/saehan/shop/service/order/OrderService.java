package com.saehan.shop.service.order;

import com.saehan.shop.domain.item.Item;
import com.saehan.shop.domain.item.ItemImg;
import com.saehan.shop.domain.item.ItemImgRepository;
import com.saehan.shop.domain.item.ItemRepository;
import com.saehan.shop.domain.order.Order;
import com.saehan.shop.domain.order.OrderItem;
import com.saehan.shop.domain.order.OrderRepository;
import com.saehan.shop.domain.user.User;
import com.saehan.shop.domain.user.UserRepository;
import com.saehan.shop.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    private final ItemImgRepository itemImgRepository;

    public Long order(OrderDto orderDto, String email) {

        System.out.println("orderService : orderDto - itemId = " + orderDto.getItemId());
        System.out.println("orderService : email = " + email);

        Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException::new);
        if(item != null)
            System.out.println(item.getItemNm());

        User user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        if(user != null)
            System.out.println(user.getEmail());

        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        orderItemList.add(orderItem);

        System.out.println(orderItemList.get(0));

        Order order = Order.createOrder(user, orderItemList);
        orderRepository.save(order);

        System.out.println(order.getId());

        return order.getId();
    }

    @Transactional(readOnly = true)
    public Page<OrderHistDto> getOrderList(String email, Pageable pageable){
        List<Order> orders = orderRepository.findOrders(email, pageable);
        Long totalCount = orderRepository.countOrder(email);

        List<OrderHistDto> orderHistDtos = new ArrayList<>();

        for(Order order : orders){
            OrderHistDto orderHistDto = new OrderHistDto(order);
            List<OrderItem> orderItems = order.getOrderItems();
            for(OrderItem orderItem : orderItems){
                ItemImg itemImg = itemImgRepository.findByItemIdAndRepImgYn(orderItem.getItem().getId(), "Y");
                OrderItemDto orderItemDto = new OrderItemDto(orderItem, itemImg.getImgUrl());
                orderHistDto.addOrderItemDto(orderItemDto);
            }
            orderHistDtos.add(orderHistDto);
        }
        return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount);
    }

    @Transactional(readOnly = true)
    public boolean validateOrder(Long orderId, String email){
        User user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        User savedUser = order.getUser();

        if(!StringUtils.equals(user.getEmail(), savedUser.getEmail())){
            return false;
        }
        return true;

    }

    public void cancelOrder(Long orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        order.cancelOrder();
    }

    public Long orders(List<OrderDto> orderDtoList, String email){
        User user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        List<OrderItem> orderItemList = new ArrayList<>();

        for(OrderDto orderDto : orderDtoList){
            Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException::new);
            OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
            orderItemList.add(orderItem);
        }

        Order order = Order.createOrder(user, orderItemList);
        orderRepository.save(order);

        return order.getId();

    }

}
