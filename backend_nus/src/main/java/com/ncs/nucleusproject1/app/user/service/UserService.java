package com.ncs.nucleusproject1.app.user.service;

/*@author: Shannon Heng, 9 October 2023*/
/*modified on 14 Nov by Shannon to include user role*/

import com.ncs.nucleusproject1.app.user.model.User;
import com.ncs.nucleusproject1.app.user.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@Service
@Log4j2
public class UserService {

        @Autowired
        UserRepo userRepo;
        public User getUserByEmail(String custEmail) {
                log.info("getCustomerByEmail");
                return userRepo.findFirstByEmail(custEmail).orElse(null);
        }

/*        public User getUserByFirstName(String firstname){
                log.info("getCustomerByGivenName");
                return userRepo.findFirstByName(firstname).orElse(null);
        }*/

//        public User getUserByLastName(String lastname){
//                log.info("getCustomerByLastName");
//                return userRepo.findFirstByLastname(lastname).orElse(null);
//        }

        public User getUserByUserId(String userId) {
                log.info("getCustomerById");
                return userRepo.findFirstById(userId).orElse(null);
        }
        public List<User> getListOfExistingUsersByRole(String roleInd){
                try {
                        log.info("getListOfExistingUsersByRole");
                        return userRepo.findByRoleind(roleInd);
                }catch (Exception e){
                        e.printStackTrace();
                        log.error("getListOfExistingUsersByRole");
                }
                return Collections.emptyList();
        }

        public List<User> getListOfExistingUsers(){
                try {
                        log.info("allCustomers");
                        return userRepo.findAll();
                }catch (Exception e){
                        e.printStackTrace();
                        log.error("getListOfCustomers");
                }
                return Collections.emptyList();
        }
        @Transactional
        public void saveUser(User user) {
                userRepo.save(user);
                log.info("New user created: "+user);;
        }
        @Transactional
        public void deleteUserById(String userId) {
                userRepo.deleteById(userId);
        }

        // Shannon - added on 11 Sept 2024 to update user's email or name or both
        @Transactional
        public void updateUserById(User existingUser) {
                userRepo.save(existingUser);
                log.info("Email and/or name changed."+existingUser);
        }


}




