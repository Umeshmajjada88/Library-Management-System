package com.example.LibraryManagement.Security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

// Runs once on EVERY incoming request.
// Reads the Authorization header, validates the JWT,
// and tells Spring Security who is making the request.
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Step 1: Read Authorization header
        String authHeader = request.getHeader("Authorization");

        // Step 2: Check it starts with "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            // Step 3: Extract token (strip "Bearer " prefix)
            String token = authHeader.substring(7);

            // Step 4: Validate token
            if (jwtUtils.validateToken(token)) {

                // Step 5: Extract email and role
                String email = jwtUtils.getEmailFromToken(token);
                String role  = jwtUtils.getRoleFromToken(token);

                // Step 6: Build Spring Security auth object
                // "ROLE_" prefix is required by Spring Security
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_" + role))
                        );
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // Step 7: Register auth in security context
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // Step 8: Continue to next filter or controller
        filterChain.doFilter(request, response);
    }
}
