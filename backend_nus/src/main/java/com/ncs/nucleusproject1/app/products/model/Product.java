package com.ncs.nucleusproject1.app.products.model;

/*@author: Shannon Heng, 10 October 2023*/
/*modified on 14 Nov by Shannon to include seller id*/

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Builder(toBuilder=true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "products")  //Hibernate Entity - match the java class to the db column name
public class Product {

    //use UUID instead - Shannon, 17 September 2024

    @Id
    //@GenericGenerator(name = "pdtid", strategy = "com.ncs.nucleusproject1.app.products.idgenerators.ProductIdGenerator")
    //@GeneratedValue(generator = "pdtid")
    @Column(name = "pdtid")
    private String pdtid;

    @Column(name = "sellerid")
    private String sellerid;

    @Column(name = "catid")
    private String catid;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "units_in_stock")
    private int unitsInStock;

    @Column(name = "date_created")
    @CreationTimestamp
    private Date dateCreated;

    @Column(name = "last_updated")
    @UpdateTimestamp
    private Date lastUpdated;
}
