package com.library.repository;

import com.library.model.BorrowTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowTransactionRepository extends JpaRepository<BorrowTransaction, Long> {
}
