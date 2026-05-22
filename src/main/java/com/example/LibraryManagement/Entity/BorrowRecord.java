package com.example.LibraryManagement.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

// Implements BorrowBook.java + ReturnBook.java + CalculateFine.java
// (all three were empty in your original project)
//
// JPA Relations:
//   MANY BorrowRecords → ONE Member  (@ManyToOne) → FK: member_id
//   MANY BorrowRecords → ONE Book    (@ManyToOne) → FK: book_id
@Entity
@Table(name = "borrow_records")
@Getter
@Setter
@NoArgsConstructor
public class BorrowRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // MANY borrow records belong to ONE member.
    // @JoinColumn creates the "member_id" foreign key column in this table.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // MANY borrow records reference ONE book.
    // @JoinColumn creates the "book_id" foreign key column in this table.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "borrow_date", nullable = false)
    private LocalDate borrowDate;

    // dueDate = borrowDate + 14 days (set in BorrowService)
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    // null until the book is actually returned
    @Column(name = "return_date")
    private LocalDate returnDate;

    // false = currently borrowed, true = returned
    @Column(nullable = false)
    private boolean returned = false;

    // Fine in rupees: ₹5 per day past dueDate (set on return)
    @Column(name = "fine_amount")
    private double fineAmount = 0.0;

    public BorrowRecord(Member member, Book book, LocalDate borrowDate, LocalDate dueDate) {
        this.member     = member;
        this.book       = book;
        this.borrowDate = borrowDate;
        this.dueDate    = dueDate;
    }
}
