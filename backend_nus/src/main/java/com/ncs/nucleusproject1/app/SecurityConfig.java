package com.ncs.nucleusproject1.app;


import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests() // Use authorizeHttpRequests() instead of authorizeRequests()
                .requestMatchers("/", "/error").permitAll() // Replace antMatchers with requestMatchers
                .requestMatchers("/webjars/**").permitAll()
                .anyRequest().authenticated() // Require authentication for all other requests
                .and()
                .oauth2Login()
                .loginPage("/login") // Custom login page (optional)
                .defaultSuccessUrl("/loginSuccess", true) // Redirect after successful login
                .failureUrl("/loginFailure") // Redirect if login fails (optional)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")); // Redirect to login page if not authenticated

        return http.build();
    }
}
