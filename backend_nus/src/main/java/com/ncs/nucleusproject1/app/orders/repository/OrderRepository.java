package com.ncs.nucleusproject1.app.orders.repository;

/*@author: Shannon Heng, 18 October 2023*/

import com.ncs.nucleusproject1.app.orders.model.Order;
import com.ncs.nucleusproject1.app.products.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findAll();
    List<Order> findAllByCustomerid(String customerId);
    Optional<Order> findFirstByOrderid(String orderid);

}
