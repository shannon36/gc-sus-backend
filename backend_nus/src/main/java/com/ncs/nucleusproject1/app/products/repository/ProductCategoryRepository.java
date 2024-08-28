package com.ncs.nucleusproject1.app.products.repository;

/*@author: Shannon Heng, 10 October 2023*/

import com.ncs.nucleusproject1.app.products.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, String> {

    List<ProductCategory> findAll();
    Optional<ProductCategory> findByCatid(String catid);
    void deleteByCatid(String catid);

}
