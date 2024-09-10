package com.ncs.nucleusproject1.app.products.controller;

/*@author: Shannon Heng, 10 October 2023*/
/*modified on 14 Nov by Shannon to include seller id*/

import com.ncs.nucleusproject1.app.products.model.Product;
import com.ncs.nucleusproject1.app.products.model.ProductCategory;
import com.ncs.nucleusproject1.app.products.service.ProductService;
import jakarta.persistence.Column;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RequestMapping("/Products")
@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://13.229.63.255:8080", "https://smartcart.nus.yaphanyee.com"})
public class ProductController {

    @Autowired
    ProductService pdtService;
    @GetMapping("/totalProductList")
    public ResponseEntity<List<Product>> getAllProducts()
    {
        return ResponseEntity.ok(pdtService.getAllProducts());
    }

    @GetMapping("/getProductsByProductId")
    public ResponseEntity<Product>  getProductById(String pdtId){
        return ResponseEntity.ok(pdtService.getProductById(pdtId));
    }

    @GetMapping("/getProductsByCategoryId")
    public ResponseEntity<List<Product>>  getProductsByCategoryId(String pdtCatId){
        return ResponseEntity.ok(pdtService.getListOfProductsByCategory(pdtCatId));
    }


    @GetMapping("/getProductsBySellerId")
    public ResponseEntity<List<Product>>  getProductsBySellerId(String sellerId){
        return ResponseEntity.ok(pdtService.getListOfProductsBySeller(sellerId));
    }


    @PostMapping("/saveNewProduct")
    public ResponseEntity<Object> addPdt(@RequestBody Product pdtReqBody) {
        pdtService.saveProduct(pdtReqBody);
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }

    @PostMapping("/deleteProductByProductId")
    public ResponseEntity<Object> deletePdt(String pdtId) {
        pdtService.deleteProductById(pdtId);
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }

    // Shannon - added on 11 Sept 2024 to update pdt
    @PutMapping("/updateProduct")
    public ResponseEntity<Object> updateProductById(String pdtId, @RequestBody Product pdtReqBody) {
        Product currPdt = pdtService.getProductById(pdtId);
        if (currPdt!=null && !currPdt.getCatid().isEmpty()){
            currPdt.setSellerid(pdtReqBody.getSellerid());
            currPdt.setCatid(pdtReqBody.getCatid());
            currPdt.setName(pdtReqBody.getName());
            currPdt.setDescription(pdtReqBody.getDescription());
            currPdt.setImageUrl(pdtReqBody.getImageUrl());
            currPdt.setUnitPrice(pdtReqBody.getUnitPrice());
            currPdt.setUnitsInStock(pdtReqBody.getUnitsInStock());
            currPdt.setLastUpdated(pdtReqBody.getLastUpdated());
            pdtService.updatePdtById(currPdt);}
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }

}

