package com.ncs.nucleusproject1.app.orders.model;//package com.ncs.nucleusproject1.app.orders.model;

/*@author: Shannon Heng, 14 October 2023*/

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    //use UUID instead - Shannon, 17 September 2024

    @Id
    //@GenericGenerator(name = "orderid", strategy = "com.ncs.nucleusproject1.app.orders.idgenerators.OrderIdGenerator")
    //@GeneratedValue(generator = "orderid")
    //@GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="orderid")
    private String orderid;

    @Column(name="customerid")
    private String customerid;

    @Column(name="totalprice")
    private String totalPrice;

    @Column(name="customeraddress")
    private String customerAddress;

    @Column(name="paymentrefid")
    private String paymentRefId;

    @Column(name = "datecreated")
    @CreationTimestamp
    private Date dateCreated;

    @Column(name = "lastupdated")
    @UpdateTimestamp
    private Date lastUpdated;
}

