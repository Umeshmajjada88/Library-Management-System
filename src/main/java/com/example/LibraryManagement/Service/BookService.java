package com.example.LibraryManagement.Service;



import com.example.LibraryManagement.DTO.BookDTO;
import com.example.LibraryManagement.Entity.Book;
import com.example.LibraryManagement.Exception.ResourceNotFoundException;
import com.example.LibraryManagement.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

// Implements:
//   AddBook.java      → addBook()
//   ViewBooks.java    → getAllBooks()
//   DeleteBook.java   → deleteBook()
//   Search.java       → searchByName(), searchByAuthor()
//   DeleteAllData.java → deleteAllBooks()
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // AddBook.java
    public BookDTO addBook(BookDTO dto) {
        if (bookRepository.existsByName(dto.getName())) {
            throw new IllegalStateException("Book already exists: " + dto.getName());
        }
        Book book = new Book(
                dto.getName(), dto.getAuthor(), dto.getPublisher(),
                dto.getCollectionAddress(), dto.getQty(),
                dto.getPrice(), dto.getBorrowingCopies()
        );
        return toDTO(bookRepository.save(book));
    }

    // ViewBooks.java
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    // DeleteBook.java — by ID
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    // DeleteBook.java — by name
    public void deleteBookByName(String name) {
        Book book = bookRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + name));
        bookRepository.delete(book);
    }

    // Search.java — exact name
    public BookDTO searchByName(String name) {
        return toDTO(bookRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + name)));
    }

    // Search.java — by author
    public List<BookDTO> searchByAuthor(String author) {
        List<Book> books = bookRepository.findByAuthorContainingIgnoreCase(author);
        if (books.isEmpty()) throw new ResourceNotFoundException("No books found by author: " + author);
        return books.stream().map(this::toDTO).collect(Collectors.toList());
    }

    // Search by partial name keyword
    public List<BookDTO> searchByNameContaining(String keyword) {
        return bookRepository.findByNameContainingIgnoreCase(keyword)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    // DeleteAllData.java
    public void deleteAllBooks() {
        bookRepository.deleteAll();
    }

    // Used internally by BorrowService and OrderService
    public Book getBookEntityById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    public BookDTO toDTO(Book book) {
        return new BookDTO(
                book.getId(), book.getName(), book.getAuthor(),
                book.getPublisher(), book.getCollectionAddress(),
                book.getQty(), book.getPrice(), book.getBorrowingCopies()
        );
    }
}
