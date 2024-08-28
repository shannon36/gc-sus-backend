package com.ncs.nucleusproject1.app.orders.model;

import com.ncs.nucleusproject1.app.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Checkout{
    private User customer;

    private Order order;

    private List<OrderItems> orderItems;
}
