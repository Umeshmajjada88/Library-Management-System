package com.example.LibraryManagement.Security;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtUtilsTest {

    @Autowired
    private JwtUtils jwtUtils;

    @Test
    void testGenerateToken() {

        String token =
                jwtUtils.generateToken(
                        "admin@gmail.com",
                        "ADMIN"
                );

        assertNotNull(token);
    }

    @Test
    void testValidateToken() {

        String token =
                jwtUtils.generateToken(
                        "admin@gmail.com",
                        "ADMIN"
                );

        boolean valid =
                jwtUtils.validateToken(token);

        assertTrue(valid);
    }
}