package com.ncs.nucleusproject1.app.orders.service;

/*@author: Shannon Heng, 19 October 2023*/

import com.ncs.nucleusproject1.app.orders.model.Checkout;
import com.ncs.nucleusproject1.app.orders.model.Order;
import com.ncs.nucleusproject1.app.orders.model.OrderItems;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Log4j2
public class CheckoutService {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

    public void saveOrderViaOrderService (Checkout checkoutDTO) {

      log.info("saveNewOrder");
      log.info(checkoutDTO);
      Order newOrder = checkoutDTO.getOrder();
      List<OrderItems> itemsForNewOrder = checkoutDTO.getOrderItems();

      processOrder(newOrder, itemsForNewOrder);
    }

    private void processOrder(Order newOrder, List<OrderItems> itemsForNewOrder) {
        newOrder.setTotalPrice(getOrderPrice(itemsForNewOrder));
        newOrder.setPaymentRefId("PT001");
        orderService.saveOrder(newOrder);
        String orderId = newOrder.getOrderid();
        processOrderItems(orderId, itemsForNewOrder);
    }

    private void processOrderItems(String orderId, List<OrderItems> itemsForNewOrder) {
        if(itemsForNewOrder!=null&& !itemsForNewOrder.isEmpty()){
            for (OrderItems orderItemInOneOrder : itemsForNewOrder) {
                log.info("saveOrderItemsForNewOrder");
                log.info(itemsForNewOrder);
                orderItemInOneOrder.setOrderid(orderId);
                orderItemService.saveOrderItem(orderItemInOneOrder);
            }   
        }
        
    }

    private String getOrderPrice(List<OrderItems> itemsForNewOrder) {
        BigDecimal totalPriceInDeciFormat = orderItemService.calculateTotalPrice(itemsForNewOrder);
        return totalPriceInDeciFormat.toString();
    }


}

