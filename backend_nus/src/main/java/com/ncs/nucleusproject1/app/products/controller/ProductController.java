package com.ncs.nucleusproject1.app.products.controller;

/*@author: Shannon Heng, 10 October 2023*/
/*modified on 14 Nov by Shannon to include seller id*/

import com.ncs.nucleusproject1.app.products.model.Image;
import com.ncs.nucleusproject1.app.products.model.Product;
import com.ncs.nucleusproject1.app.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //added to return query results for product and its category name - Shannon, 18 Sept 2024
    @GetMapping("/getProductsAndCategoryNameForSeller")
    public ResponseEntity<List<Object>>  getProductsAndCategoryNameForSeller(String sellerid){
        return ResponseEntity.ok(pdtService.getProductsAndCategoryNameForSeller(sellerid));
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
        pdtService.updatePdtById(pdtId,pdtReqBody);
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }

    //images endpoints - Shannon, 18 Sept 2024
    @GetMapping("/findByImageid")
    public ResponseEntity<Image> findByImageid(String imageid){
        return ResponseEntity.ok(pdtService.findByImageid(imageid));
    }


    @GetMapping("/getAllImages")
    public ResponseEntity<List<Image>> getAllImages(){
        return ResponseEntity.ok(pdtService.getAllImages());
    }


    @PostMapping("/saveImage")
    public ResponseEntity<Object> saveImage(@RequestBody Image imgReqBody) {
        pdtService.saveImage(imgReqBody);
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }

    @PostMapping("/deleteImageById")
    public ResponseEntity<Object> deleteImageById(String imageId) {
        pdtService.deleteImageById(imageId);
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }

    @PutMapping("/updateImageById")
    public ResponseEntity<Object> updateImageById(String imageId, @RequestBody Image imgReqBody) {
        pdtService.updateImageById(imageId,imgReqBody);
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }


}

