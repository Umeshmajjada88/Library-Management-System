package com.example.LibraryManagement.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long   id;
    private String name;
    private String author;
    private String publisher;
    private String collectionAddress;
    private int    qty;
    private double price;
    private int    borrowingCopies;
}
