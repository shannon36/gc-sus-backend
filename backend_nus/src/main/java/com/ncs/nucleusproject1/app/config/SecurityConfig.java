package com.ncs.nucleusproject1.app.config;

import java.util.Arrays;

import com.ncs.nucleusproject1.app.auth.filter.JwtAuthenticationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();

        http
            .csrf().disable()
            .cors()
            .and()
            .authorizeHttpRequests(a -> a
                .requestMatchers("/", "/error", "/webjars/**", "/auth/**").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class));  // Add JWT filter before Spring's built-in authentication
        return http.build();
    }

    // CORS filter configuration
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);  // Allow credentials such as cookies or Authorization headers
        config.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://13.229.63.255:8080", "https://smartcart.nus.yaphanyee.com"));  // Allow your frontend's origin
        config.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));  // Allow these headers
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));  // Allow these HTTP methods
        source.registerCorsConfiguration("/**", config);  // Apply CORS configuration to all endpoints
        return new CorsFilter(source);
    }


}
