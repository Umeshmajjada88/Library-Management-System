package com.example.LibraryManagement.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// @MappedSuperclass = not a table itself.
// Its fields (id, name, email, phoneNumber) are inherited
// into the Admin table and Member table directly.
// Mirrors your original abstract User.java
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    public User(String name, String email, String phoneNumber) {
        this.name        = name;
        this.email       = email;
        this.phoneNumber = phoneNumber;
    }
}
