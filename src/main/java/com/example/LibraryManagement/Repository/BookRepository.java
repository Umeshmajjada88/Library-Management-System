package com.example.LibraryManagement.Repository;



import com.example.LibraryManagement.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

// Replaces all book methods in Database.java:
//   database.AddBook(book)     → save(book)
//   database.getAllBooks()     → findAll()
//   database.getBook(name)     → findByName(name)
//   database.deleteBook(i)     → deleteById(id)
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByName(String name);

    List<Book> findByAuthorContainingIgnoreCase(String author);

    List<Book> findByNameContainingIgnoreCase(String name);

    boolean existsByName(String name);
}
