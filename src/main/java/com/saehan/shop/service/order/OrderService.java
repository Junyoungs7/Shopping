package com.saehan.shop.service.order;

import com.saehan.shop.domain.item.Item;
import com.saehan.shop.domain.item.ItemRepository;
import com.saehan.shop.domain.order.Order;
import com.saehan.shop.domain.order.OrderItem;
import com.saehan.shop.domain.order.OrderRepository;
import com.saehan.shop.domain.user.User;
import com.saehan.shop.domain.user.UserRepository;
import com.saehan.shop.web.dto.ItemFormDto2;
import com.saehan.shop.web.dto.OrderDto;
import com.saehan.shop.web.dto.UserFormDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Long order(OrderDto orderDto, String email) {

        System.out.println("orderService : orderDto - itemId = " + orderDto.getItemId());
        System.out.println("orderService : email = " + email);

//

        Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException::new);
        if(item != null)
            System.out.println(item.getItemNm());

        User user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        if(user != null)
            System.out.println(user.getEmail());
//
        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        orderItemList.add(orderItem);

        System.out.println(orderItemList.get(0));

        Order order = Order.createOrder(user, orderItemList);
        orderRepository.save(order);

        System.out.println(order.getId());

        return order.getId();
    }
}
