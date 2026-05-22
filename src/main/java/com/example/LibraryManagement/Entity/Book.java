package com.example.LibraryManagement.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

// Mirrors Book.java exactly (name, author, publisher, address, qty, price, brwcopies).
// JPA Relations:
//   ONE Book → MANY BorrowRecords  (@OneToMany)
//   ONE Book → MANY Orders         (@OneToMany)
@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String publisher;

    // "address" in original = where to find it on the shelf
    @Column(name = "collection_address", nullable = false)
    private String collectionAddress;

    // Total copies in library
    @Column(nullable = false)
    private int qty;

    @Column(nullable = false)
    private double price;

    // Copies available to borrow right now (original: brwcopies)
    // Decreases on borrow, increases on return
    @Column(name = "borrowing_copies", nullable = false)
    private int borrowingCopies;

    // ONE book can appear in MANY borrow records
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BorrowRecord> borrowRecords = new ArrayList<>();

    // ONE book can be ordered by MANY members
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    public Book(String name, String author, String publisher,
                String collectionAddress, int qty, double price, int borrowingCopies) {
        this.name              = name;
        this.author            = author;
        this.publisher         = publisher;
        this.collectionAddress = collectionAddress;
        this.qty               = qty;
        this.price             = price;
        this.borrowingCopies   = borrowingCopies;
    }
}
