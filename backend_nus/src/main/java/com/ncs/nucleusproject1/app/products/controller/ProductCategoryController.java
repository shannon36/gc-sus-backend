package com.ncs.nucleusproject1.app.products.controller;

/*@author: Shannon Heng, 10 October 2023*/

import com.ncs.nucleusproject1.app.products.model.ProductCategory;
import com.ncs.nucleusproject1.app.products.service.ProductCategoryService;
import com.ncs.nucleusproject1.app.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/ProductCategoryController")
@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://13.229.63.255:8080", "https://smartcart.nus.yaphanyee.com"})
public class ProductCategoryController {

    @Autowired
    ProductCategoryService pdtCatService;

    //product categories
    @GetMapping("/getProductCategoryList")
    public ResponseEntity<List<ProductCategory>> getPdtCatList()
    {
        return ResponseEntity.ok(pdtCatService.getListOfProductCategories());
    }

    @GetMapping("/getProductCategoryById")
    public ResponseEntity<ProductCategory>  getProductCategoryById(String pdtCatId){
        return ResponseEntity.ok(pdtCatService.getProductCategoryById(pdtCatId));
    }


    @PostMapping("/saveNewProductCategory")
    public ResponseEntity<Object> addPdtCat(@RequestBody ProductCategory pdtCatReqBody) {
        pdtCatService.saveProductCategory(pdtCatReqBody);
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }

    @PostMapping("/deleteProductCategoryById")
    public ResponseEntity<Object> deleteProductCat(String pdtCatId) {
        pdtCatService.deleteProductCatById(pdtCatId);
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }

    // Shannon - added on 11 Sept 2024 to update pdt cat name
    @PutMapping("/updateProductCatById")
    public ResponseEntity<Object> updateProductCatById(String pdtCatId, @RequestBody ProductCategory pdtCatReqBody) {
        ProductCategory currPdtCat = pdtCatService.getProductCategoryById(pdtCatId);
        if (currPdtCat!=null && !currPdtCat.getCatid().isEmpty()){
            currPdtCat.setCategoryname(pdtCatReqBody.getCategoryname());
            pdtCatService.updatePdtCatById(currPdtCat);}
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }

    }

