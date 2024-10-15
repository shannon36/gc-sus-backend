package com.ncs.nucleusproject1.app.user.controller;

/*@author: Shannon Heng (Cheow Fu's baby girl), 9 October 2023*/
/*modified on 14 Nov by Shannon to include user role*/

import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

@RequestMapping("/Users")
@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://13.229.63.255:8080", "https://smartcart.nus.yaphanyee.com"})
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
    public ResponseEntity<User>   getUserByEmail(String email){
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    //@GetMapping("/userGivenName")
    //public ResponseEntity<User>findUserByFirstName(String firstName){
    //    return ResponseEntity.ok(userService.getUserByFirstName(firstName));
   // }


    @GetMapping("/userId")
    public ResponseEntity<User>findUserById(String userId){
        return ResponseEntity.ok(userService.getUserByUserId(userId));
    }

    @PostMapping("/saveUser")
    public ResponseEntity<Object> addUser(@RequestBody User userReqBody) {
        userService.saveUser(userReqBody);
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }

    @PostMapping("/deleteUserById")
    public ResponseEntity<Object> deleteUser(String custId) {
        userService.deleteUserById(custId);
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }
// Shannon - added on 11 Sept 2024 to update user's email or name or both
    @PutMapping("/updateUser")
    public ResponseEntity<Object> updateUser(String userId, @RequestBody User userReqBody) {
        userService.updateUserById(userId,userReqBody);
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }


    }

