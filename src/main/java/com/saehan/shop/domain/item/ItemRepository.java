package com.saehan.shop.domain.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item>, ItemRepositoryCustom {

    @Query("select i from Item i where i.itemNm like %:itemNm% order by i.price desc")
    List<Item> findByItemNm(@Param("itemNm")String itemNm);
    Item findByItemNmAndItemDetail(String itemNm, String itemDetail);
}
