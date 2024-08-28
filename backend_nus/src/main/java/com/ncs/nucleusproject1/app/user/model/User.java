package com.ncs.nucleusproject1.app.user.model;

/*@author: Shannon Heng, 9 October 2023*/
/*modified on 14 Nov by Shannon to include user role*/
//role_ind: C is Customer, S is Seller
import java.io.Serializable;
import jakarta.persistence.*;
import lombok.*;

    @Entity
    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Table(name = "user")

    public class User implements Serializable {

        @Id
        //@GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        //private Long id;
        private String id;

        @Column(name = "email")
        private String email;

        @Column(name = "name")
        private String name;

        @Column(name = "roleind")
        private String roleind;

        //@Column(name = "mail_sent")
        //private Boolean mailSent;
    }


