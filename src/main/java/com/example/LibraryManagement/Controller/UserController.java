package com.example.LibraryManagement.Controller;

import com.example.LibraryManagement.DTO.AuthResponse;
import com.example.LibraryManagement.DTO.LoginRequest;
import com.example.LibraryManagement.DTO.UserDTO;
import com.example.LibraryManagement.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// POST   /api/users/register → Main.java → newuser()
// POST   /api/users/login    → Main.java → login()
// GET    /api/users          → all users (admin)
// GET    /api/users/members  → all members (admin)
// DELETE /api/users/all      → DeleteAllData.java




@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // PUBLIC — no token needed
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO dto) {
        return new ResponseEntity<>(userService.register(dto), HttpStatus.CREATED);
    }

    // PUBLIC — returns JWT token on success
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    // ADMIN only (enforced by SecurityConfig)
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // ADMIN only
    @GetMapping("/members")
    public ResponseEntity<List<UserDTO>> getAllMembers() {
        return ResponseEntity.ok(userService.getAllMembers());
    }

    // ADMIN only
    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAllUsers() {
        userService.deleteAllData();
        return ResponseEntity.ok("All users deleted.");
    }
}