package com.ncs.nucleusproject1.app.orders.controller;

/*@author: Shannon Heng, 19 October 2023*/

import com.ncs.nucleusproject1.app.orders.model.Order;
import com.ncs.nucleusproject1.app.orders.model.OrderItems;
import com.ncs.nucleusproject1.app.orders.service.OrderItemService;
import com.ncs.nucleusproject1.app.orders.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/OrderItems")
@RestController
@Log4j2
@CrossOrigin(origins = {"http://localhost:4200", "http://13.229.63.255:8080", "https://smartcart.nus.yaphanyee.com"})
public class OrderItemController {

    @Autowired
    OrderItemService orderItemService;

  @GetMapping("/findByOrderItemId")
  public ResponseEntity<OrderItems> findByOrderItemId(String orderItemId)
  {
    return ResponseEntity.ok(orderItemService.findByOrderItemId(orderItemId));
  }

  @GetMapping("/findAllOrderItemsByOrderid")
  public ResponseEntity<List<OrderItems>> findAllOrderItemsByOrderid(String orderId)
  {
    return ResponseEntity.ok(orderItemService.findAllOrderItemsByOrderid(orderId));
  }

  @GetMapping("/findAllOrderItemsByProductid")
  public ResponseEntity<List<OrderItems>> findAllOrderItemsByProductid(String pdtId)
  {
    return ResponseEntity.ok(orderItemService.findAllOrderItemsByProductid(pdtId));
  }

  @GetMapping("/getAllOrderItems")
  public ResponseEntity<List<OrderItems>> getAllOrderItems()
  {
    return ResponseEntity.ok(orderItemService.getAllOrderItems());
  }

  @PostMapping("/addOrderItems")
  public ResponseEntity<Object> addOrderItems(@RequestBody OrderItems orderItemReqBody) {
    orderItemService.saveOrderItem(orderItemReqBody);
    log.info(orderItemReqBody);
    return new ResponseEntity<>("Ok", HttpStatus.OK);
  }

}

