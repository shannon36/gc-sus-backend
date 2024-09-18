package com.ncs.nucleusproject1.app.products.repository;

/*@author: Shannon Heng, 18 September 2024*/

import com.ncs.nucleusproject1.app.products.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, String> {

    List<Image> findAll();
    Optional<Image> findByImageid(String imageid);
    void deleteByImageid(String imageid);
}
