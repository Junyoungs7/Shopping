package com.saehan.shop.domain.order;

import com.saehan.shop.domain.BaseTimeEntity;
import com.saehan.shop.domain.item.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class OrderItem extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;
    private int count;

    @Builder(toBuilder = true)
    public OrderItem(Item item, Order order, int orderPrice, int count){
        this.item = item;
        this.order = order;
        this.orderPrice = orderPrice;
        this.count = count;
    }

    public static OrderItem createOrderItem(Item item, int count){
        OrderItem orderItem = OrderItem.builder().item(item).count(count).orderPrice(item.getPrice()).build();
        item.removeStock(count);
        return orderItem;
    }

    public int getTotalPrice(){
        return orderPrice*count;
    }


}
