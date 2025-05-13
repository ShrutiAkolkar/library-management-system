package com.library.controller;

import com.library.model.Book;
import com.library.repository.BookRepository;
import com.library.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private BookRepository bookRepository;

    @PostMapping("/borrow")
    public ResponseEntity<String> borrowBook(@RequestParam Long userId, @RequestParam Long bookId) {
        return ResponseEntity.ok(libraryService.borrowBook(userId, bookId));
    }

    // CRUD operations for Book

    @PostMapping("/books")
    public Book createBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        return bookRepository.findById(id).map(book -> {
            book.setTitle(bookDetails.getTitle());
            book.setAuthor(bookDetails.getAuthor());
            book.setStatus(bookDetails.getStatus());
            return ResponseEntity.ok(bookRepository.save(book));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        return bookRepository.findById(id).map(book -> {
            bookRepository.delete(book);
            return ResponseEntity.noContent().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
