package com.ncs.nucleusproject1.app.user.controller;

import org.springframework.security.core.Authentication;

/*@author: Shannon Heng (Cheow Fu's baby girl), 9 October 2023*/
/*modified on 14 Nov by Shannon to include user role*/

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

import com.ncs.nucleusproject1.app.user.model.User;
import com.ncs.nucleusproject1.app.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import lombok.extern.log4j.Log4j2;

@RequestMapping("/Users")
@RestController
@Log4j2
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess() {
        return "Login successful!";
    }

    @GetMapping("/userList")
    public ResponseEntity<List<User>> getUserList()
    {
        return ResponseEntity.ok(userService.getListOfExistingUsers());
    }

    @GetMapping("/usersListByRole")
    public ResponseEntity<List<User>> usersListByRole(String roleChar)
    {
        return ResponseEntity.ok(userService.getListOfExistingUsersByRole(roleChar));
    }

    @GetMapping("/userEmail")
    public ResponseEntity<?>  getUserByEmail(Authentication authentication, String email){
        // Ensure the authentication object is valid
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("Unauthorized: No valid authentication found.");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String authEmail = userDetails.getUsername();  // Email is stored as username in the UserDetails
        log.info("requestor email: {}. param email: {}", authEmail, email);
        if (!authEmail.equals(email)) {
            return ResponseEntity.status(403).body("Unauthorized: identity email and requested email is different.");
        }

        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    //@GetMapping("/userGivenName")
    //public ResponseEntity<User>findUserByFirstName(String firstName){
    //    return ResponseEntity.ok(userService.getUserByFirstName(firstName));
   // }


    @GetMapping("/userId")
    public ResponseEntity<?> findUserById(Authentication authentication, String userId){
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("Unauthorized: No valid authentication found.");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();  // Email is stored as username in the UserDetails
        User user = userService.getUserByEmail(email);
        if (user == null || user != null && !user.getId().equals(userId)) {
            return ResponseEntity.status(403).body("Unauthorized: identity id and requested id is different.");
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/saveUser")
    public ResponseEntity<Object> addUser(Authentication authentication, @RequestBody User userReqBody) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("Unauthorized: No valid authentication found.");
        }

        userService.saveUser(userReqBody);
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }

    @PostMapping("/deleteUserById")
    public ResponseEntity<Object> deleteUser(Authentication authentication, String custId) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("Unauthorized: No valid authentication found.");
        }
        userService.deleteUserById(custId);
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }

    // Shannon - added on 11 Sept 2024 to update user's email or name or both
    @PutMapping("/updateUser")
    public ResponseEntity<Object> updateUser(Authentication authentication, String userId, @RequestBody User userReqBody) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("Unauthorized: No valid authentication found.");
        }
        userService.updateUserById(userId,userReqBody);
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }


    }

