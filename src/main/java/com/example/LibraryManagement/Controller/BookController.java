package com.example.LibraryManagement.Controller;


import com.example.LibraryManagement.DTO.BookDTO;
import com.example.LibraryManagement.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// POST   /api/books              → AddBook.java
// GET    /api/books              → ViewBooks.java
// DELETE /api/books/{id}         → DeleteBook.java
// DELETE /api/books              → DeleteAllData.java
// GET    /api/books/search/name  → Search.java (by name)
// GET    /api/books/search/author→ Search.java (by author)
@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO dto) {
        return new ResponseEntity<>(bookService.addBook(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book deleted successfully.");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllBooks() {
        bookService.deleteAllBooks();
        return ResponseEntity.ok("All books deleted.");
    }

    @GetMapping("/search/name")
    public ResponseEntity<BookDTO> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(bookService.searchByName(name));
    }

    @GetMapping("/search/author")
    public ResponseEntity<List<BookDTO>> searchByAuthor(@RequestParam String author) {
        return ResponseEntity.ok(bookService.searchByAuthor(author));
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookDTO>> searchByKeyword(@RequestParam String keyword) {
        return ResponseEntity.ok(bookService.searchByNameContaining(keyword));
    }
}
