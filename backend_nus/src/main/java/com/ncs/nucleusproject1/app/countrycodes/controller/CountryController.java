package com.ncs.nucleusproject1.app.countrycodes.controller;

/*@author: Shannon Heng, 10 October 2023*/

import com.ncs.nucleusproject1.app.countrycodes.model.Country;
import com.ncs.nucleusproject1.app.countrycodes.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

    @RequestMapping("/Country")
    @RestController
    @CrossOrigin(origins = {"http://localhost:4200", "http://13.229.63.255:8080", "https://smartcart.nus.yaphanyee.com"})
    public class CountryController {

        @Autowired
        CountryService ctryCodeService;

        //product categories
        @GetMapping("/getCountryByCode")
        public ResponseEntity<Country> getCountryNameByCode(String ctryCode)
        {
            return ResponseEntity.ok(ctryCodeService.getCountryName(ctryCode));
        }

        @GetMapping("/getCountryCodeByName")
        public ResponseEntity<Country> getCountryCodeByName(String ctryName){
            return ResponseEntity.ok(ctryCodeService.getCountryCode(ctryName));
        }


        @GetMapping("/getAllCountries")
        public ResponseEntity<List<Country>> getAllCountries() {
            return ResponseEntity.ok(ctryCodeService.getListOfCountries());
        }


    }

