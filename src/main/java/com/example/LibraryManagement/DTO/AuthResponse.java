package com.example.LibraryManagement.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Returned after successful login — contains the JWT token
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;      // JWT token — use this in Authorization header
    private String tokenType;  // Always "Bearer"
    private Long   id;
    private String name;
    private String email;
    private String role;       // "ADMIN" or "MEMBER"
}
