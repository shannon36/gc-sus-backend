package com.ncs.nucleusproject1.app.countrycodes.service;

/*@author: Shannon Heng, 12 October 2023*/

import com.ncs.nucleusproject1.app.countrycodes.model.Country;
import com.ncs.nucleusproject1.app.countrycodes.repository.CountryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Log4j2
public class CountryService {

        @Autowired
        CountryRepository countryRepository;
        public Country getCountryName(String ctryCode) {
                log.info("getCountryName");
                return countryRepository.findFirstByCode(ctryCode).orElse(null);
        }

        public Country getCountryCode(String ctryName){
                log.info("getCountryCode");
                return countryRepository.findFirstByName(ctryName).orElse(null);
        }

        public List<Country> getListOfCountries(){
                try {
                        log.info("allCountries");
                        return countryRepository.findAll();
                }catch (Exception e){
                        e.printStackTrace();
                        log.error("getListOfCustomers");
                }
                return Collections.emptyList();
        }


}




