package com.ncs.nucleusproject1.app.products.repository;

/*@author: Shannon Heng, 9 October 2023*/
/*modified on 14 Nov by Shannon to include seller id*/

import com.ncs.nucleusproject1.app.products.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {

    List<Product> findAll();
    List<Product> findByCatid(String catId);
    List<Product> findBySellerid(String sellerId);

    Optional<Product> findDistinctFirstByPdtid(String PdtId);
    void deleteByPdtid(String pdtId);

}
