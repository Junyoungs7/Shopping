package com.saehan.shop.domain.order;

import com.saehan.shop.domain.BaseTimeEntity;
import com.saehan.shop.domain.item.Item;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class OrderItem extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;
    private int count;
}
