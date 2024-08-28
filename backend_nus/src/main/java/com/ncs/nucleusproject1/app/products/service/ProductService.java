package com.ncs.nucleusproject1.app.products.service;

/*@author: Shannon Heng, 10 October 2023*/
/*modified on 14 Nov by Shannon to include seller id*/

import com.ncs.nucleusproject1.app.products.model.Product;
import com.ncs.nucleusproject1.app.products.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;
@Service
@Log4j2
public class ProductService {

    @Autowired
    ProductRepository pdtRepo;
    public Product getProductById(String pdtId) {
        log.info("getProductById");

        return pdtRepo.findDistinctFirstByPdtid(pdtId).orElse(null);
    }

    public List<Product> getListOfProductsByCategory(String catId){
        try {
            log.info("getProductsByCategoryId");
            return pdtRepo.findByCatid(catId);
        }catch (Exception e){
            e.printStackTrace();
            log.error("getListOfProductsByCategoryId");
        }
        return Collections.emptyList();
    }

    public List<Product> getListOfProductsBySeller(String sellerId){
        try {
            log.info("getListOfProductsBySeller");
            return pdtRepo.findBySellerid(sellerId);
        }catch (Exception e){
            e.printStackTrace();
            log.error("getListOfProductsBySeller");
        }
        return Collections.emptyList();
    }

    public List<Product> getAllProducts(){
        try {
            log.info("getAllProducts");
            return pdtRepo.findAll();
        }catch (Exception e){
            e.printStackTrace();
            log.error("getListOfCustomers");
        }
        return Collections.emptyList();
    }
    @Transactional
    public void saveProduct(Product newProduct)
    {
        log.info("Product To Be Saved");
        log.info(newProduct);
        pdtRepo.save(newProduct);
    }

    @Transactional
    public void deleteProductById(String pdtId) {
        log.info("Product To Be Deleted");
        log.info(pdtRepo.findDistinctFirstByPdtid(pdtId));
        pdtRepo.deleteByPdtid(pdtId);
    }
}
