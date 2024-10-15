package com.ncs.nucleusproject1.app.auth.controller;

/*@author: Shannon Heng (Cheow Fu's baby girl), 14 October 2024*/
/*modified on 14 October by Shannon to include user role*/

import com.ncs.nucleusproject1.app.user.service.UserService;
import com.ncs.nucleusproject1.app.user.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    private final UserService userService;
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final Key jwtSecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Autowired
    public AuthController(UserService userService, OAuth2AuthorizedClientService authorizedClientService) {
        this.userService = userService;
        this.authorizedClientService = authorizedClientService;
    }

    @GetMapping("/auth/token")
    public ResponseEntity<?> getToken(@AuthenticationPrincipal OAuth2User principal) {
        OAuth2AuthorizedClient client = authorizedClientService
                .loadAuthorizedClient("google", principal.getName());
        // TODO: Check if redirect url works and we can get the profile, name and email from google

        if (client == null) {
            return ResponseEntity.badRequest().body("No authorized client found for the user.");
        }

        String email = principal.getAttribute("email");
        User user = userService.findOrCreateUser(email, principal.getAttribute("name"));

        String jwtToken = createJwtToken(user);
        String googleAccessToken = client.getAccessToken().getTokenValue(); // TODO: Check if this is safe to return to client or should be consumed by backend

        // TODO: Check if this is actually safe to return to client?
        Map<String, Object> response = new HashMap<>();
        response.put("jwt_token", jwtToken);
        response.put("google_access_token", googleAccessToken);
        response.put("token_type", "Bearer");
        response.put("expires_in", client.getAccessToken().getExpiresAt().getEpochSecond());

        return ResponseEntity.ok(response);
    }

    private String createJwtToken(User user) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date expiration = new Date(nowMillis + 3600000); // Token expires in 1 hour

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .claim("name", user.getName())
                .claim("roles", user.getRoleind())
                .claim("userId", user.getId())
                .signWith(jwtSecretKey)
                .compact();
    }

    @GetMapping("/auth/user")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal OAuth2User principal) {
        String email = principal.getAttribute("email");
        User user = userService.findOrCreateUser(email, principal.getAttribute("name"));

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("name", user.getName());
        userInfo.put("email", user.getEmail());
        userInfo.put("role", user.getRoleind());
        userInfo.put("id", user.getId());

        return ResponseEntity.ok(userInfo);
    }
}
