package com.example.LibraryManagement.Repository;


import com.example.LibraryManagement.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

// Replaces: database.login(phonenumber, email) for Admin users
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByEmailAndPhoneNumber(String email, String phoneNumber);

    boolean existsByEmail(String email);
}
