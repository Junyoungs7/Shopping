package com.saehan.shop.domain.order;

import com.saehan.shop.domain.BaseTimeEntity;
import com.saehan.shop.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Builder
    public Order(User user, LocalDateTime orderDate, OrderStatus orderStatus){
        this.user = user;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;

    }

    public void addOrderItem(OrderItem orderItem){
        OrderItem orderItemReal = orderItem.toBuilder().order(this).build();
        orderItems.add(orderItemReal);
    }

    public static Order createOrder(User user, List<OrderItem> orderItemList){
        Order order = Order.builder().user(user).orderDate(LocalDateTime.now()).orderStatus(OrderStatus.ORDER).build();
        for(OrderItem orderItem : orderItemList)
            order.addOrderItem(orderItem);

        return order;
    }

    public int getTotalPrice(){
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    public void cancelOrder(){
        this.orderStatus = OrderStatus.CANCEL;

        for(OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }

}
