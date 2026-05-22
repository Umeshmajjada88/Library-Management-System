package com.example.LibraryManagement.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

// Mirrors NormalUser.java — gets its own "members" table.
// JPA Relations:
//   ONE Member → MANY BorrowRecords  (@OneToMany)
//   ONE Member → MANY Orders         (@OneToMany)
@Entity
@Table(name = "members")
@Getter
@Setter
@NoArgsConstructor
public class Member extends User {

    // ONE member can have MANY borrow records.
    // mappedBy = "member" means the foreign key (member_id)
    // lives in the borrow_records table, not here.
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BorrowRecord> borrowRecords = new ArrayList<>();

    // ONE member can place MANY orders.
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    public Member(String name, String email, String phoneNumber) {
        super(name, email, phoneNumber);
    }
}
