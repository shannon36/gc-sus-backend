package com.ncs.nucleusproject1.app.auth.filter;

import com.ncs.nucleusproject1.app.auth.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Extract JWT from Authorization header
        String jwt = getJwtFromRequest(request);

        if (StringUtils.hasText(jwt) && validateToken(jwt)) {
            // Get user details (like email) from JWT
            Claims claims = getClaimsFromJwt(jwt);
            String email = claims.getSubject();  // Email is stored as subject in the token
            String role = (String) claims.get("roles");

            // You can create a UserDetails object here if needed
            UserDetails userDetails = User
                .withUsername(email)
                .roles(role)
                .password("")
                .build();

            // Set the authentication in the security context
            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }

    // Helper method to extract JWT from the request header
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);  // Remove "Bearer " prefix
        }
        return null;
    }

    // Validate JWT token using JwtUtil
    private boolean validateToken(String token) {
        try {
            JwtUtil.getSecretKey();  // Ensure the secret key is present
            Jwts.parserBuilder()
                    .setSigningKey(JwtUtil.getSecretKey())  // Validate with your secret key
                    .build()
                    .parseClaimsJws(token);  // If no exception, token is valid
            return true;
        } catch (Exception e) {
            // Log or handle invalid token
            return false;
        }
    }

    // Extract claims from JWT
    private Claims getClaimsFromJwt(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(JwtUtil.getSecretKey())  // Secret key
                .build()
                .parseClaimsJws(token)
                .getBody();  // Return claims
    }
}
