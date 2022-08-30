package com.saehan.shop.domain.cart;

import com.saehan.shop.domain.BaseTimeEntity;
import com.saehan.shop.domain.item.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "cart_item")
@NoArgsConstructor
public class CartItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int count;

    @Builder
    public CartItem(Cart cart, Item item, int count){
        this.cart = cart;
        this.item = item;
        this.count = count;
    }

    public void addCount(int count){
        this.count = count;
    }

    public void updateCount(int count){
        this.count = count;
    }
}
