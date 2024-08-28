package com.ncs.nucleusproject1.app.orders.service;

/*@author: Shannon Heng, 19 October 2023*/

import com.ncs.nucleusproject1.app.orders.model.Order;
import com.ncs.nucleusproject1.app.orders.model.OrderItems;
import com.ncs.nucleusproject1.app.orders.repository.OrderItemRepository;
import com.ncs.nucleusproject1.app.orders.repository.OrderRepository;
import com.ncs.nucleusproject1.app.products.model.Product;
import com.ncs.nucleusproject1.app.products.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Service
@Log4j2
public class OrderService {

    @Autowired
    OrderRepository orderRepo;
    @Autowired
    OrderItemRepository orderItemRepo;

    public Order findByOrderid(String orderId) {
        log.info("findByOrderid");

        return orderRepo.findFirstByOrderid(orderId).orElse(null);
    }

    public List<Order> findAllOrdersbyCustomerId(String customerId){
        try {
            log.info("findAllOrdersbyCustomerId");
            return orderRepo.findAllByCustomerid(customerId);
        }catch (Exception e){
            e.printStackTrace();
            log.error("findAllOrdersbyCustomerId");
        }
        return Collections.emptyList();
    }

    public List<Order> getAllOrders(){
        try {
            log.info("getAllOrders");
            return orderRepo.findAll();
        }catch (Exception e){
            e.printStackTrace();
            log.error("getAllOrders");
        }
        return Collections.emptyList();
    }
    @Transactional
    public void saveOrder(Order newOrder)
    {
        log.info("Order To Be Saved");
        log.info(newOrder);
        orderRepo.save(newOrder);
        log.info(newOrder.getOrderid());
    }

}
