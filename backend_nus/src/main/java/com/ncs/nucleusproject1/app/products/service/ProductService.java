package com.ncs.nucleusproject1.app.products.service;

/*@author: Shannon Heng, 10 October 2023*/
/*modified on 14 Nov by Shannon to include seller id*/

import com.ncs.nucleusproject1.app.products.model.Image;
import com.ncs.nucleusproject1.app.products.model.Product;
import com.ncs.nucleusproject1.app.products.repository.ImageRepository;
import com.ncs.nucleusproject1.app.products.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class ProductService {

    @Autowired
    ProductRepository pdtRepo;

    @Autowired
    ImageRepository imgRepo;

    //use UUID instead - Shannon, 17 September 2024
    private String getPdtId() {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();

        System.out.println("Your product UUID is: " + uuidAsString);
        return uuidAsString;
    }
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

    //added to return query results for product and its category name - Shannon, 18 Sept 2024
    public List<Object> getProductsAndCategoryNameForSeller(String sellerid){
        try {
            log.info("getProductsAndCategoryNameForSeller");
            return pdtRepo.getProductsAndCategoryNameForSeller(sellerid);
        }catch (Exception e){
            e.printStackTrace();
            log.error("getProductsAndCategoryNameForSeller");
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
        newProduct.setPdtid(getPdtId());
        pdtRepo.save(newProduct);
    }

    @Transactional
    public void deleteProductById(String pdtId) {
        log.info("Product To Be Deleted");
        log.info(pdtRepo.findDistinctFirstByPdtid(pdtId));
        pdtRepo.deleteByPdtid(pdtId);
    }

    // Shannon - added on 11 Sept 2024 to update pdt
    @Transactional
    public void updatePdtById(String pdtId,Product pdtReqBody)
    {   Product currPdt = getProductById(pdtId);
        if (currPdt!=null && !currPdt.getCatid().isEmpty()) {
            currPdt.setSellerid(pdtReqBody.getSellerid());
            currPdt.setCatid(pdtReqBody.getCatid());
            currPdt.setName(pdtReqBody.getName());
            currPdt.setDescription(pdtReqBody.getDescription());
            currPdt.setImageUrl(pdtReqBody.getImageUrl());
            currPdt.setUnitPrice(pdtReqBody.getUnitPrice());
            currPdt.setUnitsInStock(pdtReqBody.getUnitsInStock());
            currPdt.setLastUpdated(pdtReqBody.getLastUpdated());
            log.info("Current Product to be saved: " + currPdt);
            pdtRepo.save(currPdt);
        }
    }


    //added for images - Shannon, 18 September 2024

    public Image findByImageid(String imageid) {
        log.info("findByImageid");

        return imgRepo.findByImageid(imageid).orElse(null);
    }

    public List<Image> getAllImages(){
        try {
            log.info("getAllImages");
            return imgRepo.findAll();
        }catch (Exception e){
            e.printStackTrace();
            log.error("getAllImages");
        }
        return Collections.emptyList();
    }

    @Transactional
    public void saveImage(Image newImage)
    {
        log.info("Image To Be Saved");
        log.info(newImage);
        newImage.setImageid(getPdtId()); //use UUID mtd too for images
        imgRepo.save(newImage);
    }

    @Transactional
    public void deleteImageById(String imageId) {
        log.info("Image To Be Deleted");
        log.info(imgRepo.findByImageid(imageId));
        imgRepo.deleteByImageid(imageId);
    }

    @Transactional
    public void updateImageById(String imageId,Image imgReqBody)
    {   Image currImg = findByImageid(imageId);
        if (currImg!=null && !currImg.getImageid().isEmpty()) {
            currImg.setDescription(imgReqBody.getDescription());
            currImg.setImageUrl(imgReqBody.getImageUrl());
            log.info("Current Image to be saved: " + currImg);
            imgRepo.save(currImg);
        }
    }

}
