package com.ncs.nucleusproject1.app.auth.controller;

/*@author: Shannon Heng (Cheow Fu's baby girl), 14 October 2024*/
/*modified on 14 October by Shannon to include user role*/

import com.ncs.nucleusproject1.app.user.service.UserService;
import com.nimbusds.jwt.JWTClaimsSet;
import com.ncs.nucleusproject1.app.auth.util.JwtUtil;
import com.ncs.nucleusproject1.app.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;


import io.jsonwebtoken.JwtException;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;



@RestController
public class AuthController {

    private final UserService userService;
    // TODO: Figure out how to not hardcode this
    private static final String CLIENT_ID = "1090601764279-2njt06m8470ls2fo7h7aie8rltdjcgns.apps.googleusercontent.com";


    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/auth/token")
    public ResponseEntity<?> getToken(@RequestHeader("id_token") String idTokenString) {
        try {
            // 1. Validate the ID token
            GoogleIdToken idToken = validateIdToken(idTokenString);

            Payload payload = idToken.getPayload();

            // 2. Extract email from ID token claims
            String email = payload.getSubject();

            // 3. Look up the user by email in the database
            User user = userService.getUserByEmail(email);

            if (user == null) {
                return ResponseEntity.status(401).body("Invalid credentials");
            }

            // 4. Generate a custom JWT token for the user
            String jwtToken = JwtUtil.generateToken(user);

            // 5. Return the generated JWT token in the response
            Map<String, Object> response = new HashMap<>();
            response.put("jwt_token", jwtToken);

            return ResponseEntity.ok(response);

        } catch (JwtException e) {
            return ResponseEntity.status(401).body("Invalid ID token");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while processing the request");
        }
    }

    private GoogleIdToken validateIdToken(String idTokenString) throws Exception {
        NetHttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(CLIENT_ID))  // Set your client ID
                .build();

        // (Receive idTokenString by HTTPS POST)

        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken == null) {
            throw new InvalidParameterException("Invalid google id token");
        }
        return idToken;
    }

    // @GetMapping("/auth/token")
    // public ResponseEntity<?> getToken(Authentication authentication) {
    //     if (authentication == null) {
    //         return ResponseEntity.badRequest().body("Invalid oauth2 token");
    //     }
    //     JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) authentication;
    //     Map<String, Object> claims = jwtAuth.getTokenAttributes();

    //     String email = (String) claims.get("email");

    //     User user = userService.getUserByEmail(email);

    //     String jwtToken = JwtUtil.generateToken(user);

    //     Map<String, Object> response = new HashMap<>();
    //     response.put("jwt_token", jwtToken);

    //     return ResponseEntity.ok(response);
    // }


    @GetMapping("/auth/user")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal OAuth2User principal) {
        String email = principal.getAttribute("email");
        User user = userService.getUserByEmail(email);

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("name", user.getName());
        userInfo.put("email", user.getEmail());
        userInfo.put("role", user.getRoleind());
        userInfo.put("id", user.getId());

        return ResponseEntity.ok(userInfo);
    }

    // TODO: Create a auth/register endpoint
}
