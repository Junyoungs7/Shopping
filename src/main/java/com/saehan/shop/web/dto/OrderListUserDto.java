package com.saehan.shop.web.dto;

import com.saehan.shop.domain.order.Order;
import com.saehan.shop.domain.order.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class OrderListUserDto {

    private Long orderId;
    private OrderStatus orderStatus;
    private String orderDate;
    private String orderUser;

    //private List<>

    public OrderListUserDto(Order order){
        this.orderId = order.getId();
        this.orderStatus = order.getOrderStatus();
        this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.orderUser = order.getUser().getName();
    }
}
