package com.example.LibraryManagement.Service;


import com.example.LibraryManagement.DTO.BorrowRecordDTO;
import com.example.LibraryManagement.Entity.Book;
import com.example.LibraryManagement.Entity.BorrowRecord;
import com.example.LibraryManagement.Entity.Member;
import com.example.LibraryManagement.Exception.ResourceNotFoundException;
import com.example.LibraryManagement.Repository.BookRepository;
import com.example.LibraryManagement.Repository.BorrowRecordRepository;
import com.example.LibraryManagement.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

// Full implementation of three EMPTY classes from your original project:
//   BorrowBook.java    → borrowBook()
//   ReturnBook.java    → returnBook()
//   CalculateFine.java → calculateFine()
//
// Fine rule: ₹5 per day after dueDate
// Borrow period: 14 days
@Service
public class BorrowService {

    private static final double FINE_PER_DAY = 5.0;
    private static final int    BORROW_DAYS  = 14;

    @Autowired private BorrowRecordRepository borrowRecordRepository;
    @Autowired private BookRepository         bookRepository;
    @Autowired private MemberRepository       memberRepository;

    // BorrowBook.java
    @Transactional
    public BorrowRecordDTO borrowBook(Long memberId, Long bookId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found: " + memberId));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + bookId));

        // Check if member already has this book
        borrowRecordRepository.findByMemberAndBookAndReturnedFalse(member, book)
                .ifPresent(r -> { throw new IllegalStateException(
                        "Member already has '" + book.getName() + "' borrowed."); });

        // Check availability
        if (book.getBorrowingCopies() <= 0)
            throw new IllegalStateException("No copies available for: " + book.getName()
                    + ". Please place an order instead.");

        // Decrease available copies
        book.setBorrowingCopies(book.getBorrowingCopies() - 1);
        bookRepository.save(book);

        // Create borrow record
        LocalDate today = LocalDate.now();
        BorrowRecord record = new BorrowRecord(member, book, today, today.plusDays(BORROW_DAYS));
        return toDTO(borrowRecordRepository.save(record));
    }

    // ReturnBook.java
    @Transactional
    public BorrowRecordDTO returnBook(Long borrowRecordId) {
        BorrowRecord record = borrowRecordRepository.findById(borrowRecordId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrow record not found: " + borrowRecordId));

        if (record.isReturned())
            throw new IllegalStateException("This book has already been returned.");

        // Calculate fine and mark returned
        record.setFineAmount(calculateFineAmount(record.getDueDate()));
        record.setReturned(true);
        record.setReturnDate(LocalDate.now());
        borrowRecordRepository.save(record);

        // Restore copy count
        Book book = record.getBook();
        book.setBorrowingCopies(book.getBorrowingCopies() + 1);
        bookRepository.save(book);

        return toDTO(record);
    }

    // CalculateFine.java — preview fine without returning
    public double calculateFine(Long borrowRecordId) {
        BorrowRecord record = borrowRecordRepository.findById(borrowRecordId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrow record not found: " + borrowRecordId));
        return record.isReturned() ? record.getFineAmount() : calculateFineAmount(record.getDueDate());
    }

    public List<BorrowRecordDTO> getActiveBorrows(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found: " + memberId));
        return borrowRecordRepository.findByMemberAndReturnedFalse(member)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<BorrowRecordDTO> getAllBorrows(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found: " + memberId));
        return borrowRecordRepository.findByMember(member)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    // Admin: all borrows across all members
    public List<BorrowRecordDTO> getAllBorrows() {
        return borrowRecordRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    private double calculateFineAmount(LocalDate dueDate) {
        long daysLate = ChronoUnit.DAYS.between(dueDate, LocalDate.now());
        return daysLate > 0 ? daysLate * FINE_PER_DAY : 0.0;
    }

    private BorrowRecordDTO toDTO(BorrowRecord r) {
        return new BorrowRecordDTO(
                r.getId(),
                r.getMember().getId(), r.getMember().getName(),
                r.getBook().getId(),   r.getBook().getName(),
                r.getBorrowDate(), r.getDueDate(), r.getReturnDate(),
                r.isReturned(), r.getFineAmount()
        );
    }
}
