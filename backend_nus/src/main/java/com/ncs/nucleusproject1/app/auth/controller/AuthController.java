package com.ncs.nucleusproject1.app.auth.controller;

/*@author: Shannon Heng, 14 October 2024*/
/*modified on 14 October by Shannon to include user role*/

import com.ncs.nucleusproject1.app.user.service.UserService;
import com.ncs.nucleusproject1.app.auth.dto.RegistrationRequestDTO;
import com.ncs.nucleusproject1.app.auth.util.JwtUtil;
import com.ncs.nucleusproject1.app.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;


import io.jsonwebtoken.JwtException;
import lombok.extern.log4j.Log4j2;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;



@RestController
@Log4j2
public class AuthController {

    private final UserService userService;
    // TODO: Figure out how to not hardcode this
    private static final String CLIENT_ID = "1090601764279-2njt06m8470ls2fo7h7aie8rltdjcgns.apps.googleusercontent.com";


    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/auth/token")
    public ResponseEntity<?> getToken(@RequestParam("id_token") String idTokenString) {
        try {
            // 1. Validate the ID token
            GoogleIdToken idToken = validateIdToken(idTokenString);

            Payload payload = idToken.getPayload();

            // 2. Extract email from ID token claims
            String email = payload.getEmail();

            // 3. Look up the user by email in the database
            User user = userService.getUserByEmail(email);

            if (user == null) {
                return ResponseEntity.status(401).body("Invalid credentials");
            }

            // 4. Generate a custom JWT token for the user
            String jwtToken = JwtUtil.generateToken(user);

            // 5. Return the generated JWT token in the response
            Map<String, Object> response = new HashMap<>();
            response.put("jwt", jwtToken);

            return ResponseEntity.ok(response);

        } catch (JwtException e) {
            return ResponseEntity.status(401).body("Invalid ID token");
        } catch (Exception e) {
            log.error("[/auth/token] internal error: {}", e);
            return ResponseEntity.status(500).body("An error occurred while processing the request");
        }
    }

    @GetMapping("/auth/user")
    public ResponseEntity<?> getUserInfo(Authentication authentication) {
        // Ensure the authentication object is valid
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("Unauthorized: No valid authentication found.");
        }

        // Extract the user details from the authentication object
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();  // Email is stored as username in the UserDetails
        String role = userDetails.getAuthorities().stream()
                                .map(grantedAuthority -> grantedAuthority.getAuthority())
                                .findFirst().orElse("ROLE_C");  // Assuming a single role
        log.info("User email: {}. User role: {}", email, role);

        // Fetch the user from the database using the email
        User user = userService.getUserByEmail(email);

        if (user == null) {
            return ResponseEntity.status(404).body("User not found.");
        }

        // Prepare user info to return
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("name", user.getName());
        userInfo.put("email", user.getEmail());
        userInfo.put("role", user.getRoleind());
        userInfo.put("id", user.getId());

        return ResponseEntity.ok(userInfo);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequestDTO registrationRequest) {
        try {
            // 1. Validate the Google ID token
            String idTokenString = registrationRequest.getIdToken();
            GoogleIdToken idToken = validateIdToken(idTokenString);
            Payload payload = idToken.getPayload();

            // 2. Extract email from the ID token claims
            String email = payload.getEmail();
            String name = (String) payload.get("name");

            if (registrationRequest.getName() != null && !registrationRequest.getName().isEmpty()) {
                name = registrationRequest.getName();
            }

            // 3. Extract role from the RegistrationRequestDTO
            String role = registrationRequest.getRole();
            if (role == null || (!role.equals("S") && !role.equals("C"))) {
                return ResponseEntity.status(400).body("Invalid role. Must be 'S' for seller or 'C' for customer.");
            }

            // 4. Check if user already exists
            User user = userService.getUserByEmail(email);

            if (user == null) {
                // 5. If user does not exist, create a new user
                userService.findOrCreateUser(email, name, role);
            } else {
                // If the user already exists, return a conflict status
                return ResponseEntity.status(409).body("User already exists with the provided email");
            }

            user = userService.getUserByEmail(email);
            // 6. Generate a JWT token for the user
            String jwtToken = JwtUtil.generateToken(user);

            // 7. Return the generated JWT token in the response
            Map<String, Object> response = new HashMap<>();
            response.put("jwt", jwtToken);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("[/auth/register] internal error: {}", e);
            return ResponseEntity.status(500).body("An error occurred during registration");
        }
    }

    private GoogleIdToken validateIdToken(String idTokenString) throws Exception {
        NetHttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(CLIENT_ID))  // Set your client ID
                .build();

        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken == null) {
            throw new InvalidParameterException("Invalid google id token");
        }
        return idToken;
    }
}
