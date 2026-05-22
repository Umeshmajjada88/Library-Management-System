package com.example.LibraryManagement.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// role field: "ADMIN" or "MEMBER"
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long   id;
    private String name;
    private String email;
    private String phoneNumber;
    private String role;
}
