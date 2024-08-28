package com.ncs.nucleusproject1.app.orders.model;//package com.ncs.nucleusproject1.app.orders.model;

/*@author: Shannon Heng, 14 October 2023*/

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import java.math.BigDecimal;


@Entity
@Table(name = "order_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItems {
    @Id
    @GenericGenerator(name = "orderitemid", strategy = "com.ncs.nucleusproject1.app.orders.idgenerators.OrderItemIdGenerator")
    @GeneratedValue(generator = "orderitemid")
    @Column(name="orderitemid")
    private String orderitemid;

    @Column(name="orderid")
    private String orderid;

    @Column(name="pdtid")
    private String productid;

    @Column(name="quantity")
    private int quantity;

    @Column(name="itemprice")
    private BigDecimal productPrice;

    public BigDecimal getPrice() {
        return productPrice.multiply(new BigDecimal(quantity));
    }
}
