package com.ncs.nucleusproject1.app.countrycodes.repository;

/*@author: Shannon Heng, 12 October 2023*/

import com.ncs.nucleusproject1.app.countrycodes.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, String> {

    List<Country> findAll();
    Optional<Country> findFirstByCode(String code);
    Optional<Country> findFirstByName(String name);

}
