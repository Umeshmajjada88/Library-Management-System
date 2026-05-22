package com.example.LibraryManagement.Repository;



import com.example.LibraryManagement.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

// Replaces: database.login() + database.AddUser() for NormalUser
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmailAndPhoneNumber(String email, String phoneNumber);

    boolean existsByEmail(String email);
}
