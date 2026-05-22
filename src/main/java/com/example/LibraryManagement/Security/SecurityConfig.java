package com.example.LibraryManagement.Security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF — not needed for stateless REST APIs
                .csrf(csrf -> csrf.disable())

                // No sessions — every request must carry its own JWT
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth

                        // ── PUBLIC — no token needed ──────────────────────────────
                        .requestMatchers(
                                "/api/users/register",
                                "/api/users/login"
                        ).permitAll()

                        // ── ADMIN only ────────────────────────────────────────────
                        .requestMatchers(
                                "/api/users",
                                "/api/users/members",
                                "/api/users/all",
                                "/api/borrow/all",
                                "/api/orders/pending"
                        ).hasRole("ADMIN")

                        // POST /api/books — admin only (add book)
                        .requestMatchers(HttpMethod.POST, "/api/books").hasRole("ADMIN")

                        // DELETE /api/books/** — admin only
                        .requestMatchers(HttpMethod.DELETE, "/api/books/**").hasRole("ADMIN")

                        // PUT /api/orders/** — admin only (fulfil/cancel)
                        .requestMatchers(HttpMethod.PUT, "/api/orders/**").hasRole("ADMIN")

                        // GET /api/orders — admin only (all orders)
                        .requestMatchers(HttpMethod.GET, "/api/orders").hasRole("ADMIN")

                        // ── Any authenticated user (ADMIN or MEMBER) ──────────────
                        .anyRequest().authenticated()
                )

                // Add JWT filter before Spring's default auth filter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
