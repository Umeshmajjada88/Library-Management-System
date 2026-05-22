package com.example.LibraryManagement.Controller;


import com.example.LibraryManagement.DTO.BorrowRecordDTO;
import com.example.LibraryManagement.Service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

// POST  /api/borrow                        → BorrowBook.java
// PUT   /api/borrow/{id}/return            → ReturnBook.java
// GET   /api/borrow/{id}/fine              → CalculateFine.java
// GET   /api/borrow/active/{memberId}      → active borrows for member
// GET   /api/borrow/member/{memberId}      → full history for member
// GET   /api/borrow/all                    → admin: all borrows
@RestController
@RequestMapping("/api/borrow")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    @PostMapping
    public ResponseEntity<BorrowRecordDTO> borrowBook(
            @RequestParam Long memberId,
            @RequestParam Long bookId) {
        return new ResponseEntity<>(borrowService.borrowBook(memberId, bookId), HttpStatus.CREATED);
    }

    @PutMapping("/{borrowRecordId}/return")
    public ResponseEntity<BorrowRecordDTO> returnBook(@PathVariable Long borrowRecordId) {
        return ResponseEntity.ok(borrowService.returnBook(borrowRecordId));
    }

    @GetMapping("/{borrowRecordId}/fine")
    public ResponseEntity<Map<String, Object>> calculateFine(@PathVariable Long borrowRecordId) {
        double fine = borrowService.calculateFine(borrowRecordId);
        return ResponseEntity.ok(Map.of(
                "borrowRecordId", borrowRecordId,
                "fineAmount",     fine,
                "currency",       "INR"
        ));
    }

    @GetMapping("/active/{memberId}")
    public ResponseEntity<List<BorrowRecordDTO>> getActiveBorrows(@PathVariable Long memberId) {
        return ResponseEntity.ok(borrowService.getActiveBorrows(memberId));
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<BorrowRecordDTO>> getAllBorrowsByMember(@PathVariable Long memberId) {
        return ResponseEntity.ok(borrowService.getAllBorrows(memberId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<BorrowRecordDTO>> getAllBorrows() {
        return ResponseEntity.ok(borrowService.getAllBorrows());
    }
}
