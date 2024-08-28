package com.ncs.nucleusproject1.app.products.model;

/*@author: Shannon Heng, 10 October 2023*/

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "product_category")  //Hibernate Entity - match the java class to the db column name
public class ProductCategory {

    @Id
    @Column(name="catid")
    private String catid;

    @Column(name="categoryname")
    private String categoryname;



}
