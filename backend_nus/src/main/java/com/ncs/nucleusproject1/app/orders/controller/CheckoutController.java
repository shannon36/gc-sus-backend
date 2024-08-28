package com.ncs.nucleusproject1.app.orders.controller;

/*@author: Shannon Heng, 19 October 2023*/

import com.ncs.nucleusproject1.app.orders.model.Checkout;
import com.ncs.nucleusproject1.app.orders.model.Order;
import com.ncs.nucleusproject1.app.orders.model.OrderItems;
import com.ncs.nucleusproject1.app.orders.service.CheckoutService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/CheckoutPurchase")
@RestController
@Log4j2
@CrossOrigin(origins = {"http://localhost:4200", "http://13.229.63.255:8080", "https://smartcart.nus.yaphanyee.com"})
public class CheckoutController {

    @Autowired
    CheckoutService chkOutService;


  @PostMapping("/checkoutOrderAndItsItems")
  public ResponseEntity<Object> addOrderAndItsItems(@RequestBody Checkout checkoutDTO) {
    chkOutService.saveOrderViaOrderService(checkoutDTO);
    log.info(checkoutDTO);
    return new ResponseEntity<>(HttpStatus.OK);
    //"Success. Order Id created is:"+checkoutDTO.getOrder().getOrderid(),
  }
}

