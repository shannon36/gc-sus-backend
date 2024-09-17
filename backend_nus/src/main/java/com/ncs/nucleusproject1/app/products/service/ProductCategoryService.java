package com.ncs.nucleusproject1.app.products.service;

/*@author: Shannon Heng, 10 October 2023*/

import com.ncs.nucleusproject1.app.products.model.ProductCategory;
import com.ncs.nucleusproject1.app.products.repository.ProductCategoryRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class ProductCategoryService {

        @Autowired
        ProductCategoryRepository pdtCatRepo;

        //use UUID instead - Shannon, 17 September 2024
        private String getPdtCatId() {
                UUID uuid = UUID.randomUUID();
                String uuidAsString = uuid.toString();

                System.out.println("Your product category UUID is: " + uuidAsString);
                return uuidAsString;
        }
        public ProductCategory getProductCategoryById(String catId) {
                log.info("getProductCategoryById");
                log.info(pdtCatRepo.findByCatid(catId).orElse(null));
                return pdtCatRepo.findByCatid(catId).orElse(null);
        }

        public List<ProductCategory> getListOfProductCategories(){
                try {
                        log.info("allProductCategories");
                        log.info(pdtCatRepo.findAll());
                        return pdtCatRepo.findAll();
                }catch (Exception e){
                        e.printStackTrace();
                        log.error("getListOfCustomers");
                }
                return Collections.emptyList();
        }
        @Transactional
        public void saveProductCategory(ProductCategory newProductCat)
        {       log.info("new Product to be saved");
                log.info(newProductCat);
                newProductCat.setCatid(getPdtCatId());
                pdtCatRepo.save(newProductCat);
        }

        @Transactional
        public void deleteProductCatById(String pdtCatId) {
                log.info("Product to be deleted");
                log.info(pdtCatRepo.findByCatid(pdtCatId));
                pdtCatRepo.deleteByCatid(pdtCatId);
        }

        // Shannon - added on 11 Sept 2024 to update pdt cat name
        @Transactional
        public void updatePdtCatById(String pdtCatId, ProductCategory pdtCatReqBody)
        {
                ProductCategory currPdtCat = getProductCategoryById(pdtCatId);

                if (currPdtCat!=null && !currPdtCat.getCatid().isEmpty()){
                        currPdtCat.setCategoryname(pdtCatReqBody.getCategoryname());
                        log.info("Current Product Category to be saved: "+currPdtCat);
                        pdtCatRepo.save(currPdtCat);
                }
        }


}




