package com.ncs.nucleusproject1.app.auth.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

import com.ncs.nucleusproject1.app.user.model.User;

import java.util.Date;

public class JwtUtil {

    // TODO: If possible put key in vault or kubernetes secrets minimally
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String generateToken(User user) {
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
                .signWith(SECRET_KEY)
                .compact();
    }

    public static SecretKey getSecretKey() {
        return SECRET_KEY;
    }
}
