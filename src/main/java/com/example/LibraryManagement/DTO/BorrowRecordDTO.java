package com.example.LibraryManagement.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRecordDTO {
    private Long      id;
    private Long      memberId;
    private String    memberName;
    private Long      bookId;
    private String    bookName;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private boolean   returned;
    private double    fineAmount;
}
