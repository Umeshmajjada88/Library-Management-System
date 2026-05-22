package com.example.LibraryManagement.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Mirrors Admin.java — gets its own "admins" table.
// Inherits id, name, email, phoneNumber from User.
@Entity
@Table(name = "admins")
@Getter
@Setter
@NoArgsConstructor
public class Admin extends User {

    public Admin(String name, String email, String phoneNumber) {
        super(name, email, phoneNumber);
    }


}
