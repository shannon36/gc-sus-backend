package com.ncs.nucleusproject1.app.orders.service;

/*@author: Shannon Heng, 19 October 2023*/

import com.ncs.nucleusproject1.app.orders.model.OrderItems;
import com.ncs.nucleusproject1.app.orders.repository.OrderItemRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class OrderItemService {

    @Autowired
    OrderItemRepository orderItemRepo;

  //use UUID instead - Shannon, 17 September 2024
  private String getOrderItemId() {
    UUID uuid = UUID.randomUUID();
    String uuidAsString = uuid.toString();

    System.out.println("Your order item UUID is: " + uuidAsString);
    return uuidAsString;
  }

  public OrderItems findByOrderItemId(String orderItemId) {
    log.info("findByOrderid");

    return orderItemRepo.findByOrderitemid(orderItemId).orElse(null);
  }

  public List<OrderItems> findAllOrderItemsByOrderid(String orderId){
    try {
      log.info("findAllByOrderid");
      return orderItemRepo.findAllByOrderid(orderId);
    }catch (Exception e){
      e.printStackTrace();
      log.error("findAllByOrderid");
    }
    return Collections.emptyList();
  }

  public List<OrderItems> findAllOrderItemsByProductid(String pdtId){
    try {
      log.info("findAllByProductid");
      return orderItemRepo.findAllByProductid(pdtId);
    }catch (Exception e){
      e.printStackTrace();
      log.error("findAllByProductid");
    }
    return Collections.emptyList();
  }

  public List<OrderItems> getAllOrderItems(){
    try {
      log.info("getAllOrderItems");
      return orderItemRepo.findAll();
    }catch (Exception e){
      e.printStackTrace();
      log.error("getAllOrderItems");
    }
    return Collections.emptyList();
  }
  @Transactional
  public void saveOrderItem(OrderItems newOrderItem)
  {
    log.info("OrderItem To Be Saved");
    log.info(newOrderItem);
    newOrderItem.setOrderitemid(getOrderItemId());
    orderItemRepo.save(newOrderItem);
  }

  public BigDecimal calculateTotalPrice(List<OrderItems> itemList)
  {
    BigDecimal orderTotal = new BigDecimal(0.00);
    if(itemList!=null && !itemList.isEmpty()){
      for (OrderItems orderItem : itemList){

        orderTotal = orderTotal.add(orderItem.getPrice());
      }
    }

  

    return orderTotal;


  }

}
