package com.library.service;

import com.library.model.*;
import com.library.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LibraryService {

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BorrowTransactionRepository transactionRepo;

    public String borrowBook(Long userId, Long bookId) {
        User user = userRepo.findById(userId).orElseThrow();
        Book book = bookRepo.findById(bookId).orElseThrow();

        if (book.getStatus() == BookStatus.BORROWED) {
            return "Book not available";
        }

        book.setStatus(BookStatus.BORROWED);
        user.getBorrowedBooks().add(book);
        bookRepo.save(book);
        userRepo.save(user);

        BorrowTransaction tx = new BorrowTransaction();
        tx.setUser(user);
        tx.setBook(book);
        tx.setBorrowDate(LocalDate.now());
        tx.setDueDate(LocalDate.now().plusDays(14));
        transactionRepo.save(tx);

        return "Book borrowed successfully!";
    }
}
