package com.ncs.nucleusproject1.app.orders.repository;

/*@author: Shannon Heng, 18 October 2023*/

import com.ncs.nucleusproject1.app.orders.model.Order;
import com.ncs.nucleusproject1.app.orders.model.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItems, String> {

    List<OrderItems> findAll();
    List<OrderItems> findAllByOrderid(String orderId);

    List<OrderItems> findAllByProductid(String productId);
    Optional<OrderItems> findByOrderitemid(String orderItemId);

}
