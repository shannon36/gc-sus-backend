package com.ncs.nucleusproject1.app.orders.controller;

/*@author: Shannon Heng, 19 October 2023*/

import com.ncs.nucleusproject1.app.orders.model.Order;
import com.ncs.nucleusproject1.app.orders.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/Orders")
@RestController
@Log4j2
@CrossOrigin(origins = {"http://localhost:4200", "http://13.229.63.255:8080", "https://smartcart.nus.yaphanyee.com"})
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/getOrderByOrderId")
    public ResponseEntity<Order> getOrderByOrderId(String orderId)
    {
        return ResponseEntity.ok(orderService.findByOrderid(orderId));
    }

    @GetMapping("/getOrdersByCustomerId")
    public ResponseEntity<List<Order>> getOrdersByCustomerId(String custId)
    {
    return ResponseEntity.ok(orderService.findAllOrdersbyCustomerId(custId));
    }

  @GetMapping("/getAllOrders")
  public ResponseEntity<List<Order>> getAllOrders()
  {
    return ResponseEntity.ok(orderService.getAllOrders());
  }

  @PostMapping("/addOrder")
  public ResponseEntity<Object> addOrder(@RequestBody Order orderReqBody) {
    orderService.saveOrder(orderReqBody);
    log.info(orderReqBody);
    return new ResponseEntity<>("Ok", HttpStatus.OK);
  }
}

