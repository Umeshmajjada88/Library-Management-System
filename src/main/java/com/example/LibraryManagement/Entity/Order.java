package com.example.LibraryManagement.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

// Implements PlaceOrder.java + ViewOrders.java
// (both were empty in your original project)
//
// JPA Relations:
//   MANY Orders → ONE Member  (@ManyToOne) → FK: member_id
//   MANY Orders → ONE Book    (@ManyToOne) → FK: book_id
@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // MANY orders belong to ONE member
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // MANY orders are for ONE book
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.PENDING;

    public enum OrderStatus {
        PENDING, FULFILLED, CANCELLED
    }

    public Order(Member member, Book book, LocalDate orderDate) {
        this.member    = member;
        this.book      = book;
        this.orderDate = orderDate;
    }
}
