package com.ncs.nucleusproject1.app.products.model;

/*@author: Shannon Heng, 18 September 2024*/

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Builder(toBuilder=true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "allowed_images")  //Hibernate Entity - match the java class to the db column name
public class Image {

    //use UUID instead - Shannon, 17 September 2024

    @Id
    @Column(name = "imageid")
    private String imageid;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "description")
    private String description;
}
