package com.saehan.shop.domain.order;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o " + "where o.user.email = :email " + "order by o.orderDate desc ")
    List<Order> findOrders(@Param("email") String email, Pageable pageable);

    @Query("select count(o) from Order o " + "where o.user.email = :email")
    Long countOrder(@Param("email") String email);

    //주문 목록 전부 가져오기
    @Query("select o from Order o order by o.orderDate asc ")
    List<Order> findAllOrders(Pageable pageable);

    //주문 목록 중 확인되지 않은 상품 갯수 가져오기
    @Query("select count(o) from Order o where o.orderStatus = com.saehan.shop.domain.order.OrderStatus.ORDER order by o.orderDate asc ")
    Long countOrdersByOrderStatus();
}
