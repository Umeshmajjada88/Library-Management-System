package com.example.LibraryManagement.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long      id;
    private Long      memberId;
    private String    memberName;
    private Long      bookId;
    private String    bookName;
    private LocalDate orderDate;
    private String    status;
}