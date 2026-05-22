package com.example.LibraryManagement.Repository;



import com.example.LibraryManagement.Entity.BorrowRecord;
import com.example.LibraryManagement.Entity.Member;
import com.example.LibraryManagement.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {

    // All active (not returned) borrows for a member
    List<BorrowRecord> findByMemberAndReturnedFalse(Member member);

    // Full borrow history for a member
    List<BorrowRecord> findByMember(Member member);

    // Check if member already has this specific book borrowed
    Optional<BorrowRecord> findByMemberAndBookAndReturnedFalse(Member member, Book book);

    // How many copies of a book are currently out
    List<BorrowRecord> findByBookAndReturnedFalse(Book book);
}
